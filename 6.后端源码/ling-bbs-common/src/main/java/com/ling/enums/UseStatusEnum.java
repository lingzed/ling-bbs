package com.ling.enums;

/**
 * 邮箱验证码状态枚举类
 */
public enum UseStatusEnum {
    NOT_USE(0, "未使用"),
    USE(1, "已使用");
    private Integer status;
    private String desc;

    UseStatusEnum(Integer status, String desc) {
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
