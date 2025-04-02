package com.ling.enums;

/**
 * 文本编辑器类型枚举
 */
public enum EditorTypeEnum {
    RICH_EDITOR(0, "富文本编辑器"), MD_EDITOR(1, "MarkDown编辑器");

    private Integer type;
    private String desc;

    EditorTypeEnum(Integer type, String desc) {
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
