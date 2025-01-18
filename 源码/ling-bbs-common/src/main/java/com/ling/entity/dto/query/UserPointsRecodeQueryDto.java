package com.ling.entity.dto.query;

/**
 * 积分记录查询dto
 */
public class UserPointsRecodeQueryDto extends BaseQueryDto {
    private String userId;          //用户id
    private Integer operationType;  //操作类型


    public UserPointsRecodeQueryDto() {
    }

    public UserPointsRecodeQueryDto(String userId, Integer operationType) {
        this.userId = userId;
        this.operationType = operationType;
    }

    /**
     * 获取
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return operationType
     */
    public Integer getOperationType() {
        return operationType;
    }

    /**
     * 设置
     * @param operationType
     */
    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String toString() {
        return "UserPointsRecodeQuery{userId = " + userId + ", operationType = " + operationType + "}";
    }
}
