package com.ling.utils;

import com.alibaba.fastjson.JSON;
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
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 系统缓存工具类
 */
public class SysCacheUtil {
    private static final String SYS_SETTING_KEY = "sysSettingManager";     // 系统设置key
    private static final Map<String, Object> sysCache = new ConcurrentHashMap<>(); // 系统缓存
    private static final Logger log = LoggerFactory.getLogger(SysCacheUtil.class);

    static {
        addSysSettingManager(new SysSettingManager());
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
    private static void addSysSettingManager(SysSettingManager sysSettingManager) {
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
}
