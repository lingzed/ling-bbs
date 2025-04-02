package com.ling.enums;

public enum RegexEnum {
    REGEX_IP(RegexEnum.IP, "校验IP地址"),
    REGEX_EMAIL(RegexEnum.EMAIL, "校验Email"),
    REGEX_ID_CARD(RegexEnum.ID_CARD, "校验身份证"),
    REGEX_PHONE(RegexEnum.PHONE, "校验手机号"),
    REGEX_PWD_MEDIUM(RegexEnum.PWD_MEDIUM, "校验密码(字母和数字组合,8-16位)"),
    REGEX_PWD_STRONG(RegexEnum.PWD_STRONG, "校验密码(字母数字特殊字符组合,8-16位)");

    public static final String IP = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    public static final String EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
    public static final String ID_CARD = "\\d{17}[\\d|x]|\\d{15}";
    public static final String PHONE = "0?(13|14|15|18|17)[0-9]{9}";
    public static final String PWD_MEDIUM = "^(?=.*[a-zA-Z])(?=.*\\d).{8,16}$";
    public static final String PWD_STRONG = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+[\\]{};':\"\\\\|,.<>/?]).{8,16}$\n";

    private final String regex;   // 正则表达式
    private String desc;    // 描述

    RegexEnum(String regex, String desc) {
        this.regex = regex;
        this.desc = desc;
    }

    public String getRegex() {
        return regex;
    }

    public String getDesc() {
        return desc;
    }
}
