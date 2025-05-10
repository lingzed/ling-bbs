package com.ling.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ling.entity.po.Comment;

import java.util.Date;

/**
 * 评论vo
 */
public class CommentVo {
    private Integer commentId;  // 评论id
    private Integer pCommentId; // 父级评论id
    private String articleId;   // 文章id
    private String imgPath;   // 图片路径
    private String content;   // 评论内容
    private String senderAvatar;   // 发送人头像
    private String senderId;   // 发送人id
    private String senderNickname;   // 发送人昵称
    private String senderIpAddress;   // 发送人IP地址
    private String receiverId;   // 接收人id
    private String receiverNickname;   // 接收人昵称
    private Integer likeCount;   // 点赞量
    private Integer topType;    // 0: 未置顶，1: 置顶
    private Integer status;     // 0: 删除, 1: 待审核, 2: 已审核
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;  // 发布时间
    private Boolean doLike; // 是否点赞
    private PageBean<CommentVo> subComment;   // 2级评论集合

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getSenderIpAddress() {
        return senderIpAddress;
    }

    public void setSenderIpAddress(String senderIpAddress) {
        this.senderIpAddress = senderIpAddress;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public PageBean<CommentVo> getSubComment() {
        return subComment;
    }

    public void setSubComment(PageBean<CommentVo> subComment) {
        this.subComment = subComment;
    }

    public Boolean getDoLike() {
        return doLike;
    }

    public void setDoLike(Boolean doLike) {
        this.doLike = doLike;
    }

    public Boolean isDoLike() {
        return doLike;
    }

    public Integer getTopType() {
        return topType;
    }

    public void setTopType(Integer topType) {
        this.topType = topType;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
