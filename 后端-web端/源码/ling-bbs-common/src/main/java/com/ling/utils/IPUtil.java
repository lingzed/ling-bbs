package com.ling.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具类
 */
public class IPUtil {
    /**
     * 获取客户端IP地址
     *
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && !ip.isEmpty() && !ip.equalsIgnoreCase("unknown")) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown"))
            ip = request.getHeader("Proxy-Client-IP");  // nginx反向代理
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown"))
            ip = request.getHeader("WL-Proxy-Client-IP");   // 阿里云反向代理
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown"))
            ip = request.getHeader("HTTP_CLIENT_IP");   // 西云数据
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown"))
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); // 普通代理
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown"))
            ip = request.getHeader("X-Real-IP");    // 云服务器
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown"))
            ip = request.getRemoteAddr();   // 如果没有获取到，就直接取IP
        return ip;
    }
}
