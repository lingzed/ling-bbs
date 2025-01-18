package com.ling.utils;

import com.ling.enums.RegexEnum;

import java.util.regex.Pattern;

/**
 * 正则校验工具类
 */
public class RegexUtil {
    /**
     * 正则校验
     *
     * @param regex 正则表达式
     * @param str   检查的字符串
     * @return
     */
    public static boolean match(String regex, String str) {
        if (StrUtil.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    /**
     * 正则校验
     *
     * @param regexEnum 正则枚举
     * @param str       检查的字符串
     * @return
     */
    public static boolean match(RegexEnum regexEnum, String str) {
        return match(regexEnum.getRegex(), str);
    }
}
