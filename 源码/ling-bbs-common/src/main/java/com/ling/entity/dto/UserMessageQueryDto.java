package com.ling.entity.dto;

/**
 * 用户消息查询参数dto
 */
public class UserMessageQueryDto extends BaseQueryDto {
    private String articleTitle;    // 文章标题
    private String sendNickName;    // 发送者昵称
    private Integer messageType;    // 消息类型，0：系统消息，1：评论，2：文章点赞，3：评论点赞4：附件下载
    private Integer status;         // 消息状态，0：未读，1：已读


    public UserMessageQueryDto() {
    }

    public UserMessageQueryDto(String articleTitle, String sendNickName, Integer messageType, Integer status) {
        this.articleTitle = articleTitle;
        this.sendNickName = sendNickName;
        this.messageType = messageType;
        this.status = status;
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

    public String toString() {
        return "UserMessageQuery{articleTitle = " + articleTitle + ", sendNickName = " + sendNickName + ", messageType = " + messageType + ", status = " + status + "}";
    }
}
