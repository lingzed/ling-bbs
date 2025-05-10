package com.ling.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ling.entity.dto.SysSettingManager;
import com.ling.enums.SysSettingItemEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * SysSettingManager getter & setter 工具类
 */
public class SysSettingManagerUtil {
    private static final Map<String, Function<SysSettingManager, Object>> getterMap = new HashMap<>();
    private static final Map<String, BiConsumer<SysSettingManager, Object>> setterMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(SysSettingManagerUtil.class);
    private static final ObjectMapper om = new ObjectMapper();

    static {
        for (SysSettingItemEnum value : SysSettingItemEnum.values()) {
            String code = value.getCode();
            try {
                PropertyDescriptor pd = new PropertyDescriptor(value.getField(), SysSettingManager.class);
                Method getter = pd.getReadMethod();
                Method setter = pd.getWriteMethod();

                getterMap.put(code, ssm -> {
                    try {
                        return getter.invoke(ssm);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("getter({})注入失败", code, e);
                        return null;
                    }
                });

                setterMap.put(code, (ssm, val) -> {
                    try {
                        setter.invoke(ssm, val);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("setter({})注入失败", code, e);
                    }
                });
            } catch (IntrospectionException e) {
                log.error("反射属性({})失败", code, e);
            }
        }
    }

    /**
     * 执行SysSettingManager的属性的getter
     * @param ssm
     * @param code
     * @return
     */
    public static Object getterInvoke(SysSettingManager ssm, String code) {
        Function<SysSettingManager, Object> getter = getterMap.get(code);
        if (getter == null) return null;
        return getter.apply(ssm);
    }

    /**
     * 执行SysSettingManager的属性的setter
     * @param ssm
     * @param code
     * @param value
     */
    public static void setterInvoke(SysSettingManager ssm, String code, Object value) {
        BiConsumer<SysSettingManager, Object> setter = setterMap.get(code);
        if (setter == null) return;
        setter.accept(ssm, value);
    }

    /**
     * 执行SysSettingManager的属性的setter
     * @param ssm
     * @param code
     * @param jsonStr
     * @param aClass
     */
    public static void setterInvoke(SysSettingManager ssm, String code, String jsonStr, Class<?> aClass) throws JsonProcessingException {
        BiConsumer<SysSettingManager, Object> setter = setterMap.get(code);
        if (setter == null) return;
        Object o = om.readValue(jsonStr, aClass);
        setter.accept(ssm, o);
    }
}
