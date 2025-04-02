package com.ling.enums;

/**
 * 性别枚举
 */
public enum GenderEnum {
    MAN(1, "男"),
    WOMAN(0, "女"),
    SECRECY(2, "保密");
    Integer gender;
    String desc;

    GenderEnum(Integer gender, String desc) {
        this.gender = gender;
        this.desc = desc;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setDescription(String desc) {
        this.desc = this.desc;
    }
}
