package com.ling.annotation;

import com.ling.enums.FrequencyLimitTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 访问控制
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessControl {
    boolean enableValidation() default true;           // 是否开启参数校验

    boolean loginRequired() default false;       // 是否登录后才能访问

    FrequencyLimitTypeEnum frequency() default FrequencyLimitTypeEnum.UNLIMITED;  // 访问频次限制
}
