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
import java.util.List;

/**
 * 校验参数切面
 */
@Aspect
@Component
public class ValidationAspect {
    private Logger log = LoggerFactory.getLogger(Validation.class);
    // 常见参数类型
    private String[] types = {
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Long",
            "java.util.Date",
            "java.util.List"};

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.ling.annotation.AccessControl)")
    public void pt() {
    }

    /**
     * 校验参数
     *
     * @param joinPoint 切点
     */
    @Before("pt()")
    public void validation(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            AccessControl accessControl = method.getAnnotation(AccessControl.class);
            if (accessControl == null || !accessControl.enableValidation()) return;  // 未开启参数校验
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
                    validObjParam(validation, value);
                }
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Throwable e) {
            throw new BusinessException(ResponseCodeEnum.CODE_500, e);
        }
    }

    /**
     * 常见类型校验
     *
     * @param validation 被校验参数的注解
     * @param value      参数值
     * @throws BusinessException
     */
    private void validParam(Validation validation, Object value) {
        // 校验是否必填
        boolean required = validation.required();
        if (!required && value == null) return;
        if (required && value == null) throw new BusinessException(ResponseCodeEnum.CODE_600);
        // 校验空串、字符长度
        if (value instanceof String) {
            String val = (String) value;
            if (StrUtil.isEmpty(val) || val.length() < validation.min() || val.length() > validation.max())
                throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        // 校验最值
        if (value instanceof Integer && ((int) value < validation.min() || (int) value > validation.max()))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        // 校验空集合
        if (value instanceof List<?> && ((List<?>) value).isEmpty())
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        // 正则校验
        String regex = validation.regex();
        if (!StrUtil.isEmpty(regex) && !RegexUtil.match(regex, value.toString()))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
    }

    /**
     * 实体校验
     *
     * @param validation 被校验参数的注解
     * @param value      参数值
     * @throws Exception
     */
    private void validObjParam(Validation validation, Object value) throws Exception {
        // 校验是否必填
        boolean required = validation.required();
        if (!required && value == null) return;
        if (required && value == null) throw new BusinessException(ResponseCodeEnum.CODE_600);
        Field[] fields = value.getClass().getDeclaredFields();  // 确保反射出私有字段
        for (Field field : fields) {
            Validation validAnno = field.getAnnotation(Validation.class);
            if (validAnno == null) continue;
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), value.getClass());
            Method getter = pd.getReadMethod();
            Object val = getter.invoke(value);
            if (ArrayUtils.contains(types, field.getType().getName())) {
                validParam(validAnno, val);
            } else {
                validObjParam(validAnno, val);
            }
        }
    }
}
