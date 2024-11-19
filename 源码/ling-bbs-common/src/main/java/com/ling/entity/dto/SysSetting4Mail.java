package com.ling.entity.dto;

/**
 * 系统设置-邮件设置
 */
public class SysSetting4Mail {
    private String mailTile;    // 邮箱验证码的标题
    private String mailContent; // 邮箱验证码的内容模板


    public SysSetting4Mail() {
    }

    public SysSetting4Mail(String mailTile, String mailContent) {
        this.mailTile = mailTile;
        this.mailContent = mailContent;
    }

    /**
     * 获取
     * @return mailTile
     */
    public String getMailTile() {
        return mailTile;
    }

    /**
     * 设置
     * @param mailTile
     */
    public void setMailTile(String mailTile) {
        this.mailTile = mailTile;
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
        return "SysSetting4Mail{mailTile = " + mailTile + ", mailContent = " + mailContent + "}";
    }
}
