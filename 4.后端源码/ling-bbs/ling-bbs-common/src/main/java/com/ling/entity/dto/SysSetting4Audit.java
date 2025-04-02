package com.ling.entity.dto;

/**
 * 系统设置-审核设置
 */
public class SysSetting4Audit {
    private Boolean postAudit;          // 发布帖是否需要审核
    private Boolean commentAudit;       // 评论是否需要审核

    /**
     * 获取
     * @return postAudit
     */
    public Boolean isPostAudit() {
        return postAudit;
    }

    /**
     * 设置
     * @param postAudit
     */
    public void setPostAudit(Boolean postAudit) {
        this.postAudit = postAudit;
    }

    /**
     * 获取
     * @return commentAudit
     */
    public Boolean isCommentAudit() {
        return commentAudit;
    }

    /**
     * 设置
     * @param commentAudit
     */
    public void setCommentAudit(Boolean commentAudit) {
        this.commentAudit = commentAudit;
    }
}
