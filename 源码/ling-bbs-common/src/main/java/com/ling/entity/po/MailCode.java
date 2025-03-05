package com.ling.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ling.entity.dto.query.BaseQueryDto;

import java.util.Date;

/**
 * 邮箱验证码实体类
 */
public class MailCode extends BaseQueryDto {
    private String mail;        // 邮箱
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")// 邮箱验证码
    private Date createTime;    // 创建时间
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;// 0: 未使用 1:已使用

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public MailCode() {
    }

    public MailCode(String mail, String code, Date createTime, Integer status) {
        this.mail = mail;
        this.code = code;
        this.createTime = createTime;
        this.status = status;
    }

    /**
     * 获取
     *
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * 设置
     *
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 获取
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取
     *
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     *
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString() {
        return "MailCode{mail = " + mail + ", code = " + code + ", createTime = " + createTime + ", status = " + status + "}";
    }
}
