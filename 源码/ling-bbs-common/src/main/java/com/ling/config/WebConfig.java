package com.ling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebConfig extends CommonConfig {
    @Value("${admin.account}")
    private String adminAccount;

    public String getAdminAccount() {
        return adminAccount;
    }
}
