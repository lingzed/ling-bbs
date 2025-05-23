package com.ling.entity.dto;

import com.ling.annotation.Validation;

/**
 * 系统设置-评论设置
 */
public class SysSetting4Comment {
    @Validation
    private Boolean openComment;        // 是否开启评论
    @Validation
    private Integer commentPoints;      // 评论积分
    @Validation
    private Integer commentNum;         // 每天可发评论数量

    public Boolean isOpenComment() {
        return openComment;
    }

    public void setOpenComment(Boolean openComment) {
        this.openComment = openComment;
    }

    public Integer getCommentPoints() {
        return commentPoints;
    }

    public void setCommentPoints(Integer commentPoints) {
        this.commentPoints = commentPoints;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
