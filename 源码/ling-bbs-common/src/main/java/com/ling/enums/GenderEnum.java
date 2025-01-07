package com.ling.enums;

/**
 * 性别枚举
 */
public enum GenderEnum {
    MAN(1, "男"),
    WOMAN(0, "女");
    Integer gander;
    String desc;

    GenderEnum(Integer gander, String desc) {
        this.gander = gander;
        this.desc = desc;
    }

    public void setGander(Integer gander) {
        this.gander = gander;
    }

    public void setDescription(String desc) {
        this.desc = this.desc;
    }
}
