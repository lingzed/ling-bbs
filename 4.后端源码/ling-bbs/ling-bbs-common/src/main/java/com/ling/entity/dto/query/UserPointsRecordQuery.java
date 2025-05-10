package com.ling.entity.dto.query;

import java.util.Date;

/**
 * 积分记录查询dto
 */
public class UserPointsRecordQuery extends BaseQuery {
    private String userId;          //用户id
    private Integer operationType;  //操作类型
    private Integer points;
    private Date creatTime;
    private Date startDate;
    private Date endDate;

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public UserPointsRecordQuery() {
    }

    public UserPointsRecordQuery(String userId, Integer operationType) {
        this.userId = userId;
        this.operationType = operationType;
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
     * @return operationType
     */
    public Integer getOperationType() {
        return operationType;
    }

    /**
     * 设置
     *
     * @param operationType
     */
    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
