package com.ling.enums;

import java.util.Objects;

/**
 * 评论排序枚举
 */
public enum CommentOrderEnum {
    HOT(0, "top_type desc, like_count desc, post_time desc", "最热排序"),
    NEW(1, "top_type desc, post_time desc", "最新排序");
    private Integer type;
    private String orderBy;
    private String desc;

    public static CommentOrderEnum getCommentOrder(Integer type) {
        CommentOrderEnum[] values = CommentOrderEnum.values();
        for (CommentOrderEnum value : values) {
            if (Objects.equals(value.getType(), type))
                return value;
        }
        return null;
    }

    CommentOrderEnum(Integer type, String orderBy, String desc) {
        this.type = type;
        this.orderBy = orderBy;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getDesc() {
        return desc;
    }
}
