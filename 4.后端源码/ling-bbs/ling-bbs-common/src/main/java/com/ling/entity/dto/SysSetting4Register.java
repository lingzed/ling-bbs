package com.ling.entity.dto;

import com.ling.annotation.Validation;

/**
 * 系统设置-注册设置
 */
public class SysSetting4Register {
    @Validation
    private String welcomeInfo;     // 注册成功后的欢迎消息


    public SysSetting4Register() {
    }

    public SysSetting4Register(String welcomeInfo) {
        this.welcomeInfo = welcomeInfo;
    }

    /**
     * 获取
     *
     * @return welcomeInfo
     */
    public String getWelcomeInfo() {
        return welcomeInfo;
    }

    /**
     * 设置
     *
     * @param welcomeInfo
     */
    public void setWelcomeInfo(String welcomeInfo) {
        this.welcomeInfo = welcomeInfo;
    }

    public String toString() {
        return "SysSetting4Register{welcomeInfo = " + welcomeInfo + "}";
    }
}
