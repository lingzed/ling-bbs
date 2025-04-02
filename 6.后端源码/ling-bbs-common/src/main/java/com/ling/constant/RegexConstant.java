package com.ling.constant;

/**
 * 正则表达式常量
 */
public class RegexConstant {
    // 校验IP地址
    public static final String REGEX_IP = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    // 校验Email
    public static final String REGEX_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
    // 校验身份证
    public static final String REGEX_ID_CARD = "\\d{17}[\\d|x]|\\d{15}";
    // 校验手机号
    public static final String REGEX_PHONE = "0?(13|14|15|18|17)[0-9]{9}";
    // 校验密码(字母和数字组合,8-16位)
    public static final String REGEX_PWD_MEDIUM = "^(?=.*[a-zA-Z])(?=.*\\d).{8,16}$";
    // 校验密码(字母数字特殊字符组合,8-16位)
    public static final String REGEX_PWD_STRONG = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+[\\]{};':\"\\\\|,.<>/?]).{8,16}$\n";
}
