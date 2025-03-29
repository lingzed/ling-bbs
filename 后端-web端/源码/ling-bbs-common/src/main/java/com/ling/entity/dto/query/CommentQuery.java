package com.ling.entity.dto.query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 评论查询传输实体
 */
public class CommentQuery extends BaseQuery {
    private Integer commentId;  // 评论id
    private Integer pCommentId; // 父级评论id
    private String articleId;   // 文章id
    private String senderId;   // 发送人id
    private String receiverId;   // 接收人id
    private Integer topType;   // 0: 未置顶, 1: 置顶
    private Integer status;   // 0: 已删除, 1: 待审核, 2: 已审核
    private Date postTime;  // 发布时间
    private String userId;  // 用户id
    private boolean isAdmin;    // 是否管理员
    private List<Integer> pIds;   // 1级评论id集合
    private LocalDate startPostTime;
    private LocalDate endPostTime;

    public LocalDate getStartPostTime() {
        return startPostTime;
    }

    public void setStartPostTime(LocalDate startPostTime) {
        this.startPostTime = startPostTime;
    }

    public LocalDate getEndPostTime() {
        return endPostTime;
    }

    public void setEndPostTime(LocalDate endPostTime) {
        this.endPostTime = endPostTime;
    }

    public List<Integer> getpIds() {
        return pIds;
    }

    public void setpIds(List<Integer> pIds) {
        this.pIds = pIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getpCommentId() {
        return pCommentId;
    }

    public void setpCommentId(Integer pCommentId) {
        this.pCommentId = pCommentId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getTopType() {
        return topType;
    }

    public void setTopType(Integer topType) {
        this.topType = topType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}
