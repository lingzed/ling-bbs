package com.ling.entity.po;

import java.util.Date;

/**
 * 用户消息实体
 */
public class UserMessage {
    private Integer messageId;      // 消息id
    private String receivedUserId;  // 接收者id
    private String articleId;       // 文章id
    private String articleTitle;    // 文章标题
    private Integer commentId;      // 评论id
    private String sendUserId;      // 发送者id
    private String sendNickName;    // 发送者昵称
    private Integer messageType;    // 消息类型，0：系统消息，1：评论，2：文章点赞，3：评论点赞4：附件下载
    private String messageContent;  // 消息内容
    private Integer status;         // 消息状态，0：未读，1：已读
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间


    public UserMessage() {
    }

    public UserMessage(Integer messageId, String receivedUserId, String articleId, String articleTitle, Integer commentId, String sendUserId, String sendNickName, Integer messageType, String messageContent, Integer status, Date createTime, Date updateTime) {
        this.messageId = messageId;
        this.receivedUserId = receivedUserId;
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.commentId = commentId;
        this.sendUserId = sendUserId;
        this.sendNickName = sendNickName;
        this.messageType = messageType;
        this.messageContent = messageContent;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 获取
     *
     * @return messageId
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * 设置
     *
     * @param messageId
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    /**
     * 获取
     *
     * @return receivedUserId
     */
    public String getReceivedUserId() {
        return receivedUserId;
    }

    /**
     * 设置
     *
     * @param receivedUserId
     */
    public void setReceivedUserId(String receivedUserId) {
        this.receivedUserId = receivedUserId;
    }

    /**
     * 获取
     *
     * @return articleId
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     * 设置
     *
     * @param articleId
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    /**
     * 获取
     *
     * @return articleTitle
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * 设置
     *
     * @param articleTitle
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * 获取
     *
     * @return commentId
     */
    public Integer getCommentId() {
        return commentId;
    }

    /**
     * 设置
     *
     * @param commentId
     */
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    /**
     * 获取
     *
     * @return sendUserId
     */
    public String getSendUserId() {
        return sendUserId;
    }

    /**
     * 设置
     *
     * @param sendUserId
     */
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * 获取
     *
     * @return sendNickName
     */
    public String getSendNickName() {
        return sendNickName;
    }

    /**
     * 设置
     *
     * @param sendNickName
     */
    public void setSendNickName(String sendNickName) {
        this.sendNickName = sendNickName;
    }

    /**
     * 获取
     *
     * @return messageType
     */
    public Integer getMessageType() {
        return messageType;
    }

    /**
     * 设置
     *
     * @param messageType
     */
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    /**
     * 获取
     *
     * @return messageContent
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * 设置
     *
     * @param messageContent
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * 获取
     *
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取
     *
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     *
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        return "UserMessage{messageId = " + messageId + ", receivedUserId = " + receivedUserId + ", articleId = " + articleId + ", articleTitle = " + articleTitle + ", commentId = " + commentId + ", sendUserId = " + sendUserId + ", sendNickName = " + sendNickName + ", messageType = " + messageType + ", messageContent = " + messageContent + ", status = " + status + ", createTime = " + createTime + ", updateTime = " + updateTime + "}";
    }
}
