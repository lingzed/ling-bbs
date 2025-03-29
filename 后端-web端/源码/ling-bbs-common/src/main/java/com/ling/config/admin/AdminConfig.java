package com.ling.config.admin;

import org.springframework.beans.factory.annotation.Value;
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
