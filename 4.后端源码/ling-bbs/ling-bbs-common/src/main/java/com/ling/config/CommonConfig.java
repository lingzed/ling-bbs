package com.ling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonConfig {
    @Value("${spring.mail.username}")
    private String mailSender;
    @Value("${cors-address}")
    private String corsAddress;

    public String getCorsAddress() {
        return corsAddress;
    }

    public String getMailSender() {
        return mailSender;
    }
}
