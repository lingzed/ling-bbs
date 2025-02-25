package com.ling.aspect;

import com.ling.annotation.AccessControl;
import com.ling.constant.Constant;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 控制登录访问切面
 */
@Aspect
@Component
@Order(0)
public class LoginRequiredAspect {
    private Logger log = LoggerFactory.getLogger(LoginRequiredAspect.class);
    @Resource
    private HttpServletRequest request;

    @Pointcut("@annotation(com.ling.annotation.AccessControl)")
    public void pt() {

    }

    @Before("pt()")
    public void checkLoginRequired(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        AccessControl accessControl = method.getAnnotation(AccessControl.class);
        if (Objects.isNull(accessControl) || !accessControl.loginRequired()) return;
        Object userinfo = request.getSession().getAttribute(Constant.USERINFO_SESSION_KEY);
        if (Objects.isNull(userinfo))
            throw new BusinessException(ResponseCodeEnum.CODE_901);
    }
}
