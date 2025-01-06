package com.ling.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ling.annotation.RequestLogRecode;
import com.ling.annotation.ExcludeParamLog;
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
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录方法执行切面
 */
@Aspect
@Component
@Order(2)
public class RequestLogRecodeAspect {
    private Logger log = LoggerFactory.getLogger(RequestLogRecodeAspect.class);

    @Resource
    private ObjectMapper objectMapper;

    @Pointcut("@annotation(com.ling.annotation.RequestLogRecode)")
    public void pt() {

    }

    @Before("pt()")
    public void logMethodExecute(JoinPoint joinPoint) {
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            RequestLogRecode lme = method.getAnnotation(RequestLogRecode.class);
            String classname = joinPoint.getTarget().getClass().getName();
            if (lme == null || !lme.enable()) return;
            Object[] args = joinPoint.getArgs();
            Parameter[] parameters = method.getParameters();
            Map<String, String> paramMap = new HashMap<>();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.getAnnotation(ExcludeParamLog.class) != null) continue;
                // 序列化深层对象
                String argsStr = objectMapper.writer().writeValueAsString(args[i]);
                log.info("{}", args[i]);
                paramMap.put(parameter.getName(), argsStr);
            }
            log.info("执行方法: {}.{}() 参数: {}", classname, method.getName(), paramMap);
        } catch (Exception e) {
            log.error("记录方法执行日志时发生异常: {}", e.getMessage(), e);
        }
    }
}
