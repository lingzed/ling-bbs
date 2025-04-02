package com.ling.entity.dto.query;

import java.util.Date;

/**
 * 用户消息查询参数dto
 */
public class UserMessageQuery extends BaseQuery {
    private String articleTitle;    // 文章标题
    private String sendNickName;    // 发送者昵称
    private Integer messageType;    // 消息类型，0：系统消息，1：评论，2：文章点赞，3：评论点赞4：附件下载
    private Integer status;         // 消息状态，0：未读，1：已读
    private String receivedUserId;

    public String getReceivedUserId() {
        return receivedUserId;
    }

    public void setReceivedUserId(String receivedUserId) {
        this.receivedUserId = receivedUserId;
    }

    /**
     * 获取
     * @return articleTitle
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * 设置
     * @param articleTitle
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * 获取
     * @return sendNickName
     */
    public String getSendNickName() {
        return sendNickName;
    }

    /**
     * 设置
     * @param sendNickName
     */
    public void setSendNickName(String sendNickName) {
        this.sendNickName = sendNickName;
    }

    /**
     * 获取
     * @return messageType
     */
    public Integer getMessageType() {
        return messageType;
    }

    /**
     * 设置
     * @param messageType
     */
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    /**
     * 获取
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
