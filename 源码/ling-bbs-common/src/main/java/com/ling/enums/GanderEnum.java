package com.ling.enums;

public enum GanderEnum {
    MAN(1, "男"),
    WOMAN(0, "女");
    Integer gander;
    String desc;

    GanderEnum(Integer gander, String desc) {
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
