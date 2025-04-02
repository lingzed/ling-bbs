package com.ling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//@Profile("admin")
public class AdminConfig {
    @Value("${project.folder}")
    private String projectFolder;
    @Value("${isDev}")
    private boolean isDev;
    @Value("${project.secret-key}")
    private String secretKey;
    @Value("${inner-url}")
    private String innerUrl;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getInnerUrl() {
        return innerUrl;
    }

    public void setInnerUrl(String innerUrl) {
        this.innerUrl = innerUrl;
    }

    public boolean isDev() {
        return isDev;
    }

    public String getProjectFolder() {
        return projectFolder;
    }
}
