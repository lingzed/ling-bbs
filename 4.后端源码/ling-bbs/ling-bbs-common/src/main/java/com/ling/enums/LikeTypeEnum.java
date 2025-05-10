package com.ling.enums;

/**
 * 点赞类型枚举
 */
public enum LikeTypeEnum {
    ARTICLE(0, "文章点赞"), COMMENT(1, "评论点赞");
    private Integer type;
    private String desc;

    LikeTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
