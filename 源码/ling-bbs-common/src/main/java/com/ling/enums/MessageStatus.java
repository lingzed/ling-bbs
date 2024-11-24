package com.ling.enums;

/**
 * 消息状态枚举
 */
public enum MessageStatus {
    NO_READ(0, "未读"), READ(1, "已读");
    private Integer code;
    private String message;

    MessageStatus(Integer code, String message) {
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
