package com.ling.entity.dto;

/**
 * 系统设置-邮件设置
 */
public class SysSetting4Mail {
    private String mailTitle;    // 邮箱验证码的标题
    private String mailContent; // 邮箱验证码的内容模板


    public SysSetting4Mail() {
    }

    public SysSetting4Mail(String mailTitle, String mailContent) {
        this.mailTitle = mailTitle;
        this.mailContent = mailContent;
    }


    /**
     * 获取
     * @return mailTitle
     */
    public String getMailTitle() {
        return mailTitle;
    }

    /**
     * 设置
     * @param mailTitle
     */
    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    /**
     * 获取
     * @return mailContent
     */
    public String getMailContent() {
        return mailContent;
    }

    /**
     * 设置
     * @param mailContent
     */
    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String toString() {
        return "SysSetting4Mail{mailTitle = " + mailTitle + ", mailContent = " + mailContent + "}";
    }
}
