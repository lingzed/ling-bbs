package com.ling.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtil {
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
     * 字符判空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
