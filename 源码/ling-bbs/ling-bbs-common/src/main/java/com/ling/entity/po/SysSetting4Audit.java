package com.ling.entity.po;

/**
 * 系统设置-审核设置
 */
public class SysSetting4Audit {
    private boolean postAudit;          // 发布帖是否需要审核
    private boolean commentAudit;       // 评论是否需要审核


    public SysSetting4Audit() {
    }

    public SysSetting4Audit(boolean postAudit, boolean commentAudit) {
        this.postAudit = postAudit;
        this.commentAudit = commentAudit;
    }

    /**
     * 获取
     * @return postAudit
     */
    public boolean isPostAudit() {
        return postAudit;
    }

    /**
     * 设置
     * @param postAudit
     */
    public void setPostAudit(boolean postAudit) {
        this.postAudit = postAudit;
    }

    /**
     * 获取
     * @return commentAudit
     */
    public boolean isCommentAudit() {
        return commentAudit;
    }

    /**
     * 设置
     * @param commentAudit
     */
    public void setCommentAudit(boolean commentAudit) {
        this.commentAudit = commentAudit;
    }

    public String toString() {
        return "SysSetting4Audit{postAudit = " + postAudit + ", commentAudit = " + commentAudit + "}";
    }
}
