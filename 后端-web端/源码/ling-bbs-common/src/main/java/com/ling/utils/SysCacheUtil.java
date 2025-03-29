package com.ling.utils;

import com.alibaba.fastjson.JSON;
import com.ling.entity.dto.SysSettingManager;
import com.ling.enums.SysSettingItemEnum;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 系统缓存工具类
 */
public class SysCacheUtil {
    private static final String SYS_SETTING_KEY = "sysSettingManager";     // 系统设置key
    private static final Map<String, Object> sysCache = new ConcurrentHashMap<>(); // 系统缓存
    // setter执行map
    private static final Map<String, BiConsumer<SysSettingManager, Object>> sysSettingSetterInvoke = new HashMap<>();

    static {
        cacheSysSettingManager(new SysSettingManager());    // 缓存中添加SysSettingManager
        for (SysSettingItemEnum value : SysSettingItemEnum.values()) {
            try {
                Class<?> aClass = Class.forName(value.getClassname());    // 配置项全类名获取字节码对象
                // 获取SysSettingManager的属性描述符, 由value.getField()对应的属性
                PropertyDescriptor pd = new PropertyDescriptor(value.getField(), SysSettingManager.class);
                Method setter = pd.getWriteMethod();    // 获取setter方法
                // ssManager: SysSettingManager对象, jsonStr: 被解析为实体对象的json字符串
                sysSettingSetterInvoke.put(value.getCode(), (ssManager, jsonStr) -> {
                    try {
                        // 将系统设置的JSON字符串内容转换为对应的Java对象, 调用写方法注入
                        setter.invoke(ssManager, JSON.parseObject((String) jsonStr, aClass));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });     // 将setter封装到map中
            } catch (ClassNotFoundException | IntrospectionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取系统设置
     *
     * @return
     */
    public static SysSettingManager getSysSettingManager() {
        return (SysSettingManager) getSysCache(SYS_SETTING_KEY);
    }

    /**
     * 缓存系统设置
     *
     * @param sysSettingManager
     */
    private static void cacheSysSettingManager(SysSettingManager sysSettingManager) {
        setSysCache(SYS_SETTING_KEY, sysSettingManager);
    }

    /**
     * 添加缓存
     *
     * @param k
     * @param v
     */
    public static void setSysCache(String k, Object v) {
        if (Objects.equals(k, SYS_SETTING_KEY) && !Objects.isNull(sysCache.get(k))) return;
        sysCache.put(k, v);
    }

    /**
     * 获取缓存
     *
     * @param k
     * @return
     */
    public static Object getSysCache(String k) {
        return sysCache.get(k);
    }

    /**
     * 执行SysSettingManager中属性的setter()
     *
     * @param code      通过code找到属性
     * @param ssManager SysSettingManager对象
     * @param jsonStr
     */
    public static void setterInvoke(String code, SysSettingManager ssManager, String jsonStr) {
        BiConsumer<SysSettingManager, Object> setter = sysSettingSetterInvoke.get(code);
        if (Objects.isNull(setter)) return;
        setter.accept(ssManager, jsonStr);
    }
}
