package com.ling.enums;

/**
 * 消息状态枚举
 */
public enum MessageStatusEnum {
    NO_READ(0, "未读"), READ(1, "已读");
    private Integer status;
    private String message;

    MessageStatusEnum(Integer code, String message) {
        this.status = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
