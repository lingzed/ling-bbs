package com.ling.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

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
}
