package com.ling.entity.dto.query;

import java.time.LocalDate;

public class LikeRecordQuery extends BaseQuery {
    private String targetId;
    private String likerId;
    private String targetAuthorId;
    private Integer likeType;
    private LocalDate startLikeTime;
    private LocalDate endLikeTime;

    public LocalDate getStartLikeTime() {
        return startLikeTime;
    }

    public void setStartLikeTime(LocalDate startLikeTime) {
        this.startLikeTime = startLikeTime;
    }

    public LocalDate getEndLikeTime() {
        return endLikeTime;
    }

    public void setEndLikeTime(LocalDate endLikeTime) {
        this.endLikeTime = endLikeTime;
    }

    public String getTargetAuthorId() {
        return targetAuthorId;
    }

    public void setTargetAuthorId(String targetAuthorId) {
        this.targetAuthorId = targetAuthorId;
    }


    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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
}
