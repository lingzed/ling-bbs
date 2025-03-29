package com.ling.config.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonConfig {
    @Value("${spring.mail.username}")
    private String mailSender;

    public String getMailSender() {
        return mailSender;
    }
}
