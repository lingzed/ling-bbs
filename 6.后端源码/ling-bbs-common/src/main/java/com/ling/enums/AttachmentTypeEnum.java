package com.ling.enums;

/**
 * 文章有无附件类型枚举
 */
public enum AttachmentTypeEnum {
    NOT_ATTACHMENT(0, "无附件"),
    HAVE_ATTACHMENT(1, "有附件");
    private Integer type;
    private String desc;

    AttachmentTypeEnum(Integer type, String desc) {
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
