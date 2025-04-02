package com.ling.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 文章/评论置顶类型枚举
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TragetTopTypeEnum {
    NO_TOP(0, "未置顶"),
    TOP(1, "置顶");

    private Integer topType;
    private String desc;

    TragetTopTypeEnum(Integer topType, String desc) {
        this.topType = topType;
        this.desc = desc;
    }

    public Integer getTopType() {
        return topType;
    }

    public String getDesc() {
        return desc;
    }
}
