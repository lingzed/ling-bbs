package com.ling.utils;

import com.ling.entity.dto.SysSettingContainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统设置缓存工具类
 */
public class SysCacheUtil {
    private static final String SYS_CACHE = "sysCache";     // 系统设置缓存key
    private static final Map<String, SysSettingContainer> sysCache = new ConcurrentHashMap<>(); // 系统设置缓存

    /**
     * 获取系统设置缓存
     *
     * @return
     */
    public static SysSettingContainer getSysSettingContainer() {
        return sysCache.get(SYS_CACHE);
    }

    /**
     * 设置系统设置缓存
     *
     * @param sysSettingContainer
     */
    public static void setSysCache(SysSettingContainer sysSettingContainer) {
        sysCache.put(SYS_CACHE, sysSettingContainer);
    }
}
