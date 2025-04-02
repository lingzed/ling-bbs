package com.ling.entity.dto;

/**
 * 系统设置-邮件设置
 */
public class SysSetting4Mail {
    private String mailTitle;    // 邮箱验证码的标题
    private String mailContent; // 邮箱验证码的内容模板

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}
