package com.ling.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数校验注解
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {
    boolean required() default true;        // 是否必填

    int max() default Integer.MAX_VALUE;    // 最大值

    int min() default 0;                   // 最小值

    String regex() default "";            // 校验规则
}
