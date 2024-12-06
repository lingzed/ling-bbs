package com.ling.aspect;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import com.ling.utils.RegexUtil;
import com.ling.utils.StrUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 校验参数切面
 */
@Aspect
@Component
public class ValidationAspect {
    private Logger log = LoggerFactory.getLogger(Validation.class);
    private String[] types = {
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Long"};

    @Pointcut("@annotation(com.ling.annotation.AccessControl)")
    public void pt() {
    }

    @Before("pt()")
    public void validation(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            AccessControl accessControl = method.getAnnotation(AccessControl.class);
            if (accessControl == null) return;  // 未开启参数校验
            Parameter[] parameters = method.getParameters();    // 形参列表
            for (int i = 0; i < parameters.length; i++) {
                Parameter param = parameters[i];
                Validation validation = param.getAnnotation(Validation.class);
                if (validation == null) continue;
                Object value = args[i];
                if (ArrayUtils.contains(types, param.getType().getName())) {
                    // 常见类型校验
                    validParam(validation, value);
                } else {
                    // 实体校验
                    validObjParam(value);
                }
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Throwable e) {
            throw new BusinessException(ResponseCodeEnum.CODE_500, e);
        }
    }

    private void validParam(Validation validation, Object value) throws BusinessException {
        // 校验是否必填
        boolean required = validation.required();
        if (!required && value == null) return;
        if (required && value == null) throw new BusinessException(ResponseCodeEnum.CODE_600);
        // 校验空串
        if (value instanceof String && StrUtil.isEmpty(value.toString()))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        // 校验最值
        if (value instanceof Integer && ((int) value < validation.min() || (int) value > validation.max()))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        // 正则校验
        String regex = validation.regex();
        if (!regex.equals("") && !RegexUtil.match(regex, value.toString()))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
    }

    private void validObjParam(Object value) throws Exception {
        Field[] fields = value.getClass().getDeclaredFields();
        for (Field field : fields) {
            Validation validAnno = field.getAnnotation(Validation.class);
            if (validAnno == null) continue;
            // field.setAccessible(true); // 使私有字段可访问
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), value.getClass());
            Method getter = pd.getReadMethod();
            validParam(validAnno, getter.invoke(value));
        }
    }
}
