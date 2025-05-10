package com.ling.controller.inner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.commons.CommonMsg;
import com.ling.config.WebConfig;
import com.ling.constant.Constant;
import com.ling.entity.vo.Result;
import com.ling.entity.vo.WSMessage;
import com.ling.enums.ResponseCodeEnum;
import com.ling.enums.WSMessageTypeEnum;
import com.ling.exception.BusinessException;
import com.ling.service.SysSettingService;
import com.ling.service.websocket.WebSocketServer;
import com.ling.utils.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 内部接口控制器
 */
@RestController
@RequestMapping("/inner-api")
public class InnerAPIController {
    private static final Logger log = LoggerFactory.getLogger(InnerAPIController.class);
    private static final JsonMapper JSON_MAPPER = new JsonMapper();
    @Resource
    private SysSettingService sysSettingService;
    @Resource
    private WebConfig webConfig;
    @Resource
    private WebSocketServer webSocketServer;

    /**
     * 刷新系统设置
     *
     * @param validData
     * @return
     */
    @PutMapping("/refresh-cache")
    @AccessControl
    public Result<Void> refreshSysSetting(@RequestBody Map<String, Object> validData) {
        Long timestamp = (Long) validData.get("timestamp");
        log.info("timestamp:{}", timestamp);
        if (timestamp == null)
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        // 校验时间戳，避免使用旧值
        checkTimestamp(timestamp);

        String sign = (String) validData.get("sign");
        log.info("sign:{}", sign);
        if (sign == null || sign.length() != 64)
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        // 校验签名
        checkSign(sign, String.valueOf(timestamp));

        sysSettingService.refreshCache();
        return Result.success();
    }

    /**
     * 发送消息
     *
     * @param userId
     * @param timestamp
     * @param sign
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/send-msg")
    public Result<Void> sendMessage(String userId, Long timestamp, String sign) throws JsonProcessingException {
        // 校验时间戳，避免使用旧值
        checkTimestamp(timestamp);

        // 校验签名
        checkSign(sign, userId + timestamp);

        WSMessage wsMessage = WSMessage.ofAdminServer(WSMessageTypeEnum.LOAD_UNREAD);

        String res = JSON_MAPPER.writeValueAsString(wsMessage);

        try {
            webSocketServer.sendMessageToUser(userId, res);
            return Result.success();
        } catch (IOException e) {
            throw new BusinessException(CommonMsg.WS_MESSAGE_SEND_FAIL, e);
        }
    }

    /**
     * 校验时间戳
     *
     * @param timestamp
     */
    private void checkTimestamp(Long timestamp) {
        if (Math.abs(System.currentTimeMillis() - timestamp) >= Constant.MILLIS_1 * 30) {
            log.error("时间戳失效");
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
    }

    /**
     * 校验签名
     *
     * @param sign
     */
    private void checkSign(String sign, String data) {
        String secretKey = webConfig.getSecretKey();
        String mySign = StrUtil.generateHmacSha256Hex(secretKey, data);
        if (!Objects.equals(mySign, sign)) {
            log.error("签名错误，校验失败");
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
    }
}
