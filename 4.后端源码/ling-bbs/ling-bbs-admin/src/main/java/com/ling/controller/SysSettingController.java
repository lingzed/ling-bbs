package com.ling.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.config.AdminConfig;
import com.ling.entity.dto.SysSettingManager;
import com.ling.entity.po.SysSetting;
import com.ling.entity.vo.Result;
import com.ling.service.SysSettingService;
import com.ling.utils.OkHttpUtil;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys-setting")
public class SysSettingController {
    private static final Logger log = LoggerFactory.getLogger(SysSettingController.class);
    @Resource
    private SysSettingService sysSettingService;
    @Resource
    private AdminConfig adminConfig;

    @GetMapping("/refresh")
    @AccessControl
    public Result<String> refreshSysSetting() throws JsonProcessingException {
        sysSettingService.refreshCache();
        String innerUrl = adminConfig.getInnerUrl();
        String secretKey = adminConfig.getSecretKey();

        String url = innerUrl + "/refresh-cache";
        Long currentTime = System.currentTimeMillis();
        String sign = StrUtil.generateHmacSha256Hex(secretKey, String.valueOf(currentTime));

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("timestamp", currentTime);
        paramMap.put("sign", sign);
        ObjectMapper objectMapper = new ObjectMapper();
        String validData = objectMapper.writeValueAsString(paramMap);

        log.info(validData);

        String res = OkHttpUtil.putRequest(url, validData, null);
        return Result.success(res);
    }

    /**
     * 更新系统设置
     * @param sysSettingManager
     * @return
     * @throws JsonProcessingException
     */
    @PutMapping
    @AccessControl(loginRequired = true)
    public Result<Void> editSysSetting(@RequestBody @Validation SysSettingManager sysSettingManager) {
        sysSettingService.editSysSetting(sysSettingManager);
        return Result.success();
    }

    @GetMapping
    public Result<SysSettingManager> get() {
        SysSettingManager sysSettingManager = SysCacheUtil.getSysSettingManager();
        return Result.success(sysSettingManager);
    }
}
