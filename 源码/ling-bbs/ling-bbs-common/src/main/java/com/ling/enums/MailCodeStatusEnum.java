package com.ling.enums;

public enum MailCodeStatusEnum {
    NOT_USE(0, "未使用"),
    USE(1, "已使用");
    private Integer status;
    private String desc;

    MailCodeStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
