package com.ling.enums;

/**
 * 频次限制类型枚举
 */
public enum FrequencyLimitTypeEnum {
    UNLIMITED("unlimited", "无限制", null),
    COMMENT_LIMIT("comment_num_limit", "评论限制", "评论次数已达上限（每日%d次），请明日再试"),
    LIKE_LIMIT("like_num_limit", "点赞限制", "点赞次数已达上限（每日%d次），请明日再试"),
    POST_LIMIT("post_num_limit", "发文限制", "发文次数已达上限（每日%d次），请明日再试"),
    UPLOAD_IMG_LIMIT("upload_img_num_limit", "上传图片限制", "图片上传次数已达上限（每日%d次），请明日再试");

    private String type;
    private String desc;
    private String errMsg;

    FrequencyLimitTypeEnum(String type, String desc, String errMsg) {
        this.type = type;
        this.desc = desc;
        this.errMsg = errMsg;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
