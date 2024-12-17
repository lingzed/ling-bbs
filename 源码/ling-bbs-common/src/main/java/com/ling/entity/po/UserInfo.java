package com.ling.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ling.entity.param.BusinessParam;

import java.util.Date;

/**
 * 用户信息实体
 */
public class UserInfo extends BusinessParam<UserInfo> {
    private String userId;
    private String nickName;
    private String email;
    private String password;
    private Integer gander;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastLoginTime;
    private String lastLoginIp;
    private String lastLoginIpAddress;
    private Integer totalIntegral;
    private Integer currentIntegral;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private Integer version;    // 乐观锁


    public UserInfo() {
    }

    public UserInfo(String userId, String nickName, String email, String password, Integer gander, String description, Date joinTime, Date lastLoginTime, String lastLoginIp, String lastLoginIpAddress, Integer totalIntegral, Integer currentIntegral, Integer status, Date createTime, Date updateTime) {
        this.userId = userId;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.gander = gander;
        this.description = description;
        this.joinTime = joinTime;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginIpAddress = lastLoginIpAddress;
        this.totalIntegral = totalIntegral;
        this.currentIntegral = currentIntegral;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取
     *
     * @return nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置
     *
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    /**
     * 获取
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     *
     * @return gander
     */
    public Integer getGander() {
        return gander;
    }

    /**
     * 设置
     *
     * @param gander
     */
    public void setGander(Integer gander) {
        this.gander = gander;
    }

    /**
     * 获取
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取
     *
     * @return joinTime
     */
    public Date getJoinTime() {
        return joinTime;
    }

    /**
     * 设置
     *
     * @param joinTime
     */
    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    /**
     * 获取
     *
     * @return lastLoginTime
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置
     *
     * @param lastLoginTime
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取
     *
     * @return lastLoginIp
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置
     *
     * @param lastLoginIp
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * 获取
     *
     * @return lastLoginIpAddress
     */
    public String getLastLoginIpAddress() {
        return lastLoginIpAddress;
    }

    /**
     * 设置
     *
     * @param lastLoginIpAddress
     */
    public void setLastLoginIpAddress(String lastLoginIpAddress) {
        this.lastLoginIpAddress = lastLoginIpAddress;
    }

    /**
     * 获取
     *
     * @return totalIntegral
     */
    public Integer getTotalIntegral() {
        return totalIntegral;
    }

    /**
     * 设置
     *
     * @param totalIntegral
     */
    public void setTotalIntegral(Integer totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    /**
     * 获取
     *
     * @return currentIntegral
     */
    public Integer getCurrentIntegral() {
        return currentIntegral;
    }

    /**
     * 设置
     *
     * @param currentIntegral
     */
    public void setCurrentIntegral(Integer currentIntegral) {
        this.currentIntegral = currentIntegral;
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
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        return "UserInfo{userId = " + userId + ", nickName = " + nickName + ", email = " + email + ", password = " + password + ", gander = " + gander + ", description = " + description + ", joinTime = " + joinTime + ", lastLoginTime = " + lastLoginTime + ", lastLoginIp = " + lastLoginIp + ", lastLoginIpAddress = " + lastLoginIpAddress + ", totalIntegral = " + totalIntegral + ", currentIntegral = " + currentIntegral + ", status = " + status + ", createTime = " + createTime + ", updateTime = " + updateTime + "}";
    }
}
