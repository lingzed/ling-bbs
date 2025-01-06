package com.ling.entity.dto;

/**
 * 用户会话信息传输实体
 */
public class UserInfoSessionDto {
    private String userId;  //  用户id
    private String nickName; // 昵称
    private String province; // 省份
    private boolean isAdmin; // 是否管理员

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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
