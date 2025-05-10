package com.ling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
//@Profile("web")
public class WebConfig extends CommonConfig {
    @Value("${project.admin-account.account}")
    private String adminAccount;
    @Value("${project.admin-account.password}")
    private String adminPassword;
    @Value("${project.admin-account.mails}")
    private String adminMails;
    @Value("${project.folder}")
    private String projectFolder;
    @Value("${project.folder-attachment}")
    private String projectFolderAttachment;
    @Value("${project.allow-img-suffix}")
    private String projectAllowImgSuffix;
    private List<String> allowImgSuffixList;
    @Value("${project.secret-key}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public String getProjectFolderAttachment() {
        return projectFolderAttachment;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public String getAdminMails() {
        return adminMails;
    }

    private String getProjectAllowImgSuffix() {
        return projectAllowImgSuffix;
    }

    public List<String> getAllowImgSuffixList() {
        if (Objects.isNull(allowImgSuffixList)) {
            allowImgSuffixList = Arrays.asList(getProjectAllowImgSuffix().split(","));
        }
        return allowImgSuffixList;
    }
}
