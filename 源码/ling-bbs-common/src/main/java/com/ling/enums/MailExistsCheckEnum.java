package com.ling.enums;

/**
 * 枚举类，用于检查邮件是否存在
 */
public enum MailExistsCheckEnum {
    REGEISTER_CHECK(1, "注册场景检查"),
    RESET_PWD_CHECK(2, "重置密码场景检查");

    private Integer env;
    private String desc;

    MailExistsCheckEnum(Integer env, String desc) {
        this.env = env;
        this.desc = desc;
    }

    public Integer getEnv() {
        return env;
    }

    public String getDesc() {
        return desc;
    }
}
