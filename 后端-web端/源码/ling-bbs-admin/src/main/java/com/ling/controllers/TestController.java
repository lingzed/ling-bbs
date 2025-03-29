package com.ling.controllers;

import com.ling.config.admin.AdminConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {
    private Logger log = LoggerFactory.getLogger(TestController.class);
    @Resource
    private AdminConfig adminConfig;

    @GetMapping
    public String test() {
        log.info("{}", adminConfig.getProjectFolder());
        return "hello";
    }
}
