package com.ling;

import com.ling.service.SysSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class InitRun implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InitRun.class);
    @Resource
    private SysSettingService sysSettingService;

    @Override
    public void run(ApplicationArguments args) {
        sysSettingService.initSysSetting();
    }
}
