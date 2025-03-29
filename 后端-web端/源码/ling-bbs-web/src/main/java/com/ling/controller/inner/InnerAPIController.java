package com.ling.controller.inner;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.config.web.WebpageConfig;
import com.ling.constant.Constant;
import com.ling.entity.vo.Result;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import com.ling.service.SysSettingService;
import com.ling.utils.StrUtil;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
@RequestMapping("/inner-api")
public class InnerAPIController {
    private Logger log = LoggerFactory.getLogger(InnerAPIController.class);
    @Resource
    private SysSettingService sysSettingService;
    @Resource
    private WebpageConfig webpageConfig;

    @PutMapping("/refresh-cache")
    @AccessControl
    public Result<Void> refreshSysCache(@Validation Long timestamp, @Validation String sign) {
        // 校验时间戳，避免使用旧值
        if (Math.abs(System.currentTimeMillis() - timestamp) >= Constant.MILLIS_1 * 30) {
            log.error("时间戳失效");
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        // 校验签名
        String secretKey = webpageConfig.getSecretKey();
        String mySign = StrUtil.generateHmacSha256Hex(secretKey, String.valueOf(timestamp));
        if (!Objects.equals(mySign, sign)) {
            log.error("签名错误，校验失败");
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        sysSettingService.refreshCache();
        return Result.success();
    }
}
