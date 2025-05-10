package com.ling.entity.vo;

/**
 * 评论点赞情况
 */
public class CommentLikeInfo {
    private Integer commentId;
    private Integer likeCount;
    private Boolean doLike;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean isDoLike() {
        return doLike;
    }

    public void setDoLike(Boolean doLike) {
        this.doLike = doLike;
    }
}
