package com.ling.service.websocket;

import com.ling.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketServer extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static final Map<String, WebSocketSession> userSessionMap = new ConcurrentHashMap<>();

    /**
     * socket连接时执行
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = (String) session.getAttributes().get("userId");
        // 连接时存储用户的会话
        userSessionMap.put(userId, session);
        log.info("ws连接成功: 【{}】<--->【{}】建立会话", Constant.WEB_SERVER_NAME, userId);
    }

    /**
     * 接收发送方(如前端)的消息
     *
     * @param session
     * @param message
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("接收到消息: {}", message);
    }

    /**
     * socket关闭时执行
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = (String) session.getAttributes().get("userId");
        if (!Objects.isNull(userId)) {
            userSessionMap.remove(userId);
            log.info("ws连接关闭，移除【{}】会话", userId);
        }
    }

    /**
     * 发送消息
     *
     * @param userId
     * @param message
     */
    public void sendMessageToUser(String userId, String message) throws IOException {
        WebSocketSession userSession = userSessionMap.get(userId);  // 拿到userId对应会话
        if (userSession != null && userSession.isOpen()) {
            log.info("【{}】向【{}】推送消息", Constant.WEB_SERVER_NAME, userId);
            userSession.sendMessage(new TextMessage(message));
        }
    }
}
