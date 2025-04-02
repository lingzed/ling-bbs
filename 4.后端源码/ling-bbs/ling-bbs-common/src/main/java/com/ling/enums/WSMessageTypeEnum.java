package com.ling.enums;

/**
 * Web Socket 消息类型
 */
public enum WSMessageTypeEnum {
    LOAD_UNREAD(0, "加载未读消息"),
    PADDING_ARTICLE(1, "待审核文章");

    WSMessageTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private Integer type;
    private String desc;

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
