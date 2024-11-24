package com.ling.enums;

/**
 * 消息类型枚举
 */
public enum MessageTypeEnum {
    SYS_MESSAGE(0, "系统消息"),
    COMMENT(1, "评论"),
    LIKE(2, "文章点赞"),
    COMMENT_LIKE(3, "评论点赞"),
    ATTACHMENT(4, "附件下载");

    private Integer code;
    private String message;

    MessageTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
