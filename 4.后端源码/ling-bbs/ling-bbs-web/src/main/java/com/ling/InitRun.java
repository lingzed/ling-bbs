package com.ling;

import com.ling.commons.CommonMsg;
import com.ling.exception.BusinessException;
import com.ling.service.SysSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InitRun implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InitRun.class);
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
        sysSettingService.initSysSetting();
    }
}
