package com.ling;

import com.ling.commons.CommonMsg;
import com.ling.exception.BusinessException;
import com.ling.service.SysSettingService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InitRun implements ApplicationRunner {
    @Resource
    private SysSettingService sysSettingService;

    /**
     * springboot启动后执行的钩子
     * 系统启动后，刷新缓存
     *
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) {
        try {
            sysSettingService.refreshCache();
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.REFRESH_CACHE_FAIL, e);
        }
    }
}
