package com.ling.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class StrUtil {
    /**
     * 生成随机字符(字母+数字)
     *
     * @param len 字符长度
     * @return
     */
    public static String getRandomStr(Integer len) {
        return RandomStringUtils.random(len, true, true);
    }

    /**
     * 生成随机字符(纯数字)
     *
     * @param len 字符长度
     * @return
     */
    public static String getRandomNum(Integer len) {
        return RandomStringUtils.random(len, false, true);
    }

    /**
     * 字符判空
     *
     * @param str 原字符
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty() || str.trim().equals("null");
    }

    /**
     * MD5加密
     *
     * @param str 原字符
     * @return
     */
    public static String encodeMD5(String str) {
        return isEmpty(str) ? null : DigestUtils.md5Hex(str);
    }

    /**
     * 首字母大写
     *
     * @param str 原字符
     * @return
     */
    public static String upFirstLetter(String str) {
        if (isEmpty(str)) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 返回UUID，去除"-"
     *
     * @return
     */
    public static String getUUID() {
        return getUUID(true);
    }

    /**
     * 返回UUID
     *
     * @param rmConnector 是否去除"-"
     * @return
     */
    public static String getUUID(boolean rmConnector) {
        String uuid = UUID.randomUUID().toString();
        if (rmConnector) {
            return uuid.replace("-", "");
        }
        return uuid;
    }

    /**
     * 返回当前日期格式化字符串
     *
     * @param format
     * @return
     */
    public static String formatDate(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }

    /**
     * 获取文件后缀名
     *
     * @param filename
     * @return
     */
    public static String getFilenameSuffix(String filename) {
        if (filename == null || !filename.contains(".")) {
            return null;
        }
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 获取文件后缀名, 不带.
     *
     * @param filename
     * @return
     */
    public static String getFilenameSuffixWithoutDot(String filename) {
        if (filename == null || !filename.contains(".")) {
            return null;
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
}
