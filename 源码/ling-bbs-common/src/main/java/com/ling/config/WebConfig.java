package com.ling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebConfig extends CommonConfig {
    @Value("${admin.account}")
    private String adminAccount;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.mails}")
    private String adminMails;
    @Value("${project.folder}")
    private String projectFolder;
    @Value("${project.folder-image}")
    private String projectFolderImage;
    @Value("${project.folder-attachment}")
    private String projectFolderAttachment;
    @Value("${project.folder-cover}")
    private String projectFolderCover;
    @Value("${project.folder-avatar}")
    private String projectFolderAvatar;

    public String getProjectFolderImage() {
        return projectFolderImage;
    }

    public String getProjectFolderAttachment() {
        return projectFolderAttachment;
    }

    public String getProjectFolderCover() {
        return projectFolderCover;
    }

    public String getProjectFolderAvatar() {
        return projectFolderAvatar;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getAdminMails() {
        return adminMails;
    }
}
