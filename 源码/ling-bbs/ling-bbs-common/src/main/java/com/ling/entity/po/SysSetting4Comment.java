package com.ling.entity.po;

/**
 * 系统设置-评论设置
 */
public class SysSetting4Comment {
    private boolean openComment;        // 是否开启评论
    private Integer commentPoints;      // 评论积分
    private Integer commentNum;         // 每天可发评论数量


    public SysSetting4Comment() {
    }

    public SysSetting4Comment(boolean openComment, Integer commentPoints, Integer commentNum) {
        this.openComment = openComment;
        this.commentPoints = commentPoints;
        this.commentNum = commentNum;
    }

    /**
     * 获取
     *
     * @return openComment
     */
    public boolean isOpenComment() {
        return openComment;
    }

    /**
     * 设置
     *
     * @param openComment
     */
    public void setOpenComment(boolean openComment) {
        this.openComment = openComment;
    }

    /**
     * 获取
     *
     * @return commentPoints
     */
    public Integer getCommentPoints() {
        return commentPoints;
    }

    /**
     * 设置
     *
     * @param commentPoints
     */
    public void setCommentPoints(Integer commentPoints) {
        this.commentPoints = commentPoints;
    }

    /**
     * 获取
     *
     * @return commentNum
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * 设置
     *
     * @param commentNum
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String toString() {
        return "SysSetting4Comment{openComment = " + openComment + ", commentPoints = " + commentPoints + ", commentNum = " + commentNum + "}";
    }
}
