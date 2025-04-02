package com.ling.enums;

/**
 * 积分操作类型枚举
 */
public enum OperationTypeEnum {
    REGISTER(1, "账号注册"),
    DOWNLOAD_ATTACHMENTS(2, "下载附件"),
    ATTACHMENT_DOWNLOADED(3, "附件被下载"),
    POST_COMMENT(4, "发表评论"),
    POST_ARTICLE(5, "发布文章"),
    ADMIN_OPERATION(6, "管理员操作"),
    ARTICLE_DELETE(7, "删除文章"),
    COMMENT_DELETE(8, "删除评论");

    private Integer type;
    private String desc;


    OperationTypeEnum() {
    }

    OperationTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * 获取
     *
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置
     *
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取
     *
     * @return desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置
     *
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
