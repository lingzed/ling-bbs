package com.ling.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户会话信息传输实体
 */
public class SessionUserinfo {
    private String userId;  //  用户id
//    @JsonIgnore
    private String mail;    // 邮箱
    private String avatar;  // 头像
    private String nickName; // 昵称
    private String province; // 省份
    private boolean isAdmin; // 是否管理员

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
