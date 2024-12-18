package com.ling.entity.po;

import com.ling.annotation.Validation;
import com.ling.constant.RegexConstant;

public class Address {
    @Validation
    private String code;
    @Validation(regex = RegexConstant.REGEX_PHONE)
    private String addr;


    public Address() {
    }

    public Address(String code, String addr) {
        this.code = code;
        this.addr = addr;
    }

    /**
     * 获取
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取
     * @return addr
     */
    public String getAddr() {
        return addr;
    }

    /**
     * 设置
     * @param addr
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String toString() {
        return "Address{code = " + code + ", addr = " + addr + "}";
    }
}
