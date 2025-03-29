package com.ling.service.websocket;

import com.ling.commons.CommonMsg;
import com.ling.constant.Constant;
import com.ling.entity.dto.SessionUserinfo;
import com.ling.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketServer extends TextWebSocketHandler {
    private Logger log = LoggerFactory.getLogger(WebSocketServer.class);
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
            userSession.sendMessage(new TextMessage(message));
        }
    }
}
