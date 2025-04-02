package com.ling.interceptor;

import com.ling.config.AdminConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.session.SessionAdminInfo;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.Objects;

/**
 * 登录状态校验拦截器
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Resource
    private AdminConfig adminConfig;

    /**
     * 请求拦截预处理
     * 在控制器方法执行前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        SessionAdminInfo adminInfo = (SessionAdminInfo) session.getAttribute(Constant.ADMIN_SESSION_KEY);

        if (Objects.isNull(adminInfo)) {
            // 若为开发环境，可以免登录，手动创建会话信息
            if (adminConfig.isDev()) {
                SessionAdminInfo admin = new SessionAdminInfo();
                admin.setAccount("admin");
                session.setAttribute(Constant.ADMIN_SESSION_KEY, admin);
                return true;
            }
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
