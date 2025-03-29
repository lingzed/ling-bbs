package com.ling.interceptor;

import com.ling.commons.CommonMsg;
import com.ling.constant.Constant;
import com.ling.entity.dto.SessionUserinfo;
import com.ling.entity.po.UserInfo;
import com.ling.exception.BusinessException;
import com.ling.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Component
public class UserIdHandshakeInterceptor implements HandshakeInterceptor {
    private Logger log = LoggerFactory.getLogger(UserIdHandshakeInterceptor.class);

    @Resource
    private UserInfoService userInfoService;

    /**
     * 握手前的处理(关键)
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;

            // 可以选择将id作为参数传递，但这样不安全
            String userId = servletRequest.getServletRequest().getParameter("userId");
            checkUser(userId);
            attributes.put("userId", userId); // 存入WebSocketSession属性
        }
        return true; // 返回true表示允许握手
    }

    /**
     * 握手后的处理（一般无需操作）
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private UserInfo checkUser(String userId) {
        UserInfo userInfo = userInfoService.findById(userId);
        if (Objects.isNull(userInfo))
            throw new BusinessException(CommonMsg.WS_CONNECTION_FAIL);
        return userInfo;
    }
}
