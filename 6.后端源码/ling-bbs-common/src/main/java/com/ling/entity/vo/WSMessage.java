package com.ling.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ling.constant.Constant;
import com.ling.enums.WSMessageTypeEnum;

import java.util.Date;

/**
 * Web Socket 消息
 */
public class WSMessage {
    private String sender;
    private Integer type;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    public WSMessage(String sender, Integer type, String content, Date date) {
        this.sender = sender;
        this.type = type;
        this.content = content;
        this.date = date;
    }

    public WSMessage() {
    }

    public static WSMessage of(String sender, Integer type, String content, Date date) {
        return new WSMessage(sender, type, content, date);
    }

    /**
     * 构建web端websocket消息
     *
     * @param type
     * @param content
     * @return
     */
    public static WSMessage ofWebServer(Integer type, String content) {
        return new WSMessage(Constant.WEB_SERVER_NAME, type, content, new Date());
    }

    /**
     * 构建web端websocket消息
     *
     * @param wsMessageTypeEnum
     * @return
     */
    public static WSMessage ofWebServer(WSMessageTypeEnum wsMessageTypeEnum) {
        return new WSMessage(Constant.WEB_SERVER_NAME, wsMessageTypeEnum.getType(), wsMessageTypeEnum.getDesc(),
                new Date());
    }

    /**
     * 构建admin端websocket消息
     *
     * @param type
     * @param content
     * @return
     */
    public static WSMessage ofAdminServer(Integer type, String content) {
        return new WSMessage(Constant.ADMIN_SERVER_NAME, type, content, new Date());
    }

    /**
     * 构建admin端websocket消息
     *
     * @param wsMessageTypeEnum
     * @return
     */
    public static WSMessage ofAdminServer(WSMessageTypeEnum wsMessageTypeEnum) {
        return new WSMessage(Constant.ADMIN_SERVER_NAME, wsMessageTypeEnum.getType(), wsMessageTypeEnum.getDesc(),
                new Date());
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
