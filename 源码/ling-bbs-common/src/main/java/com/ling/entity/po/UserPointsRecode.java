package com.ling.entity.po;

import java.util.Date;

/**
 * 用户积分记录实体
 */
public class UserPointsRecode {
    private Integer recodeId;       //记录id
    private String userId;          //用户id
    private Integer operationType;  //操作类型
    private Integer points;         //积分
    private Date createTime;        //创建时间
    private Date updateTime;        //更新时间


    public UserPointsRecode() {
    }

    public UserPointsRecode(Integer recodeId, String userId, Integer operationType, Integer points, Date createTime, Date updateTime) {
        this.recodeId = recodeId;
        this.userId = userId;
        this.operationType = operationType;
        this.points = points;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 获取
     * @return recodeId
     */
    public Integer getRecodeId() {
        return recodeId;
    }

    /**
     * 设置
     * @param recodeId
     */
    public void setRecodeId(Integer recodeId) {
        this.recodeId = recodeId;
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

    /**
     * 获取
     * @return points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * 设置
     * @param points
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * 获取
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        return "UserPointsRecode{recodeId = " + recodeId + ", userId = " + userId + ", operationType = " + operationType + ", points = " + points + ", createTime = " + createTime + ", updateTime = " + updateTime + "}";
    }
}
