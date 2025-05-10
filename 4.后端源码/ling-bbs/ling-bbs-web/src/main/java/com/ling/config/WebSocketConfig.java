package com.ling.config;

import com.ling.interceptor.UserIdHandshakeInterceptor;
import com.ling.service.websocket.WebSocketServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * WebSocket配置类
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Resource
    private WebSocketServer webSocketServer;
    @Resource
    private UserIdHandshakeInterceptor userIdHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketServer, "/ws") // 监听/ws
                .addInterceptors(userIdHandshakeInterceptor)    // 添加拦截器
                .setAllowedOrigins("*");    // 允许所有URL跨域
    }
}
