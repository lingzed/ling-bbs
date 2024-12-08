package com.ling.entity.po;

import com.ling.annotation.Validation;
import com.ling.constant.RegexConstant;
import com.ling.enums.RegexEnum;
import org.springframework.boot.context.properties.bind.DefaultValue;

public class Person {
    @Validation
    private String name;
    @Validation(min = -10, max = -1)
    private Integer age;
    @Validation(regex = RegexConstant.REGEX_EMAIL)
    private String email;
    @Validation
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person() {
    }

    public Person(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置
     *
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "Person{name = " + name + ", age = " + age + ", email = " + email + "}";
    }
}
