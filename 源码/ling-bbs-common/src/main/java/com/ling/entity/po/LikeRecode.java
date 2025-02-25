package com.ling.entity.po;

import java.util.Date;

public class LikeRecode {
    private Integer likeRecodeId;
    private String targetId;
    private String targetAuthorId;
    private String likerId;
    private Integer likeType;
    private Date likeTime;

    public Integer getLikeRecodeId() {
        return likeRecodeId;
    }

    public void setLikeRecodeId(Integer likeRecodeId) {
        this.likeRecodeId = likeRecodeId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetAuthorId() {
        return targetAuthorId;
    }

    public void setTargetAuthorId(String targetAuthorId) {
        this.targetAuthorId = targetAuthorId;
    }

    public String getLikerId() {
        return likerId;
    }

    public void setLikerId(String likerId) {
        this.likerId = likerId;
    }

    public Integer getLikeType() {
        return likeType;
    }

    public void setLikeType(Integer likeType) {
        this.likeType = likeType;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }
}
