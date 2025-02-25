package com.ling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
//@Profile("admin")
public class AdminConfig {
    @Value("${project.folder}")
    private String projectFolder;


    public String getProjectFolder() {
        return projectFolder;
    }
}
