package com.ling.entity.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * 文章查询DTO
 */
public class ArticleQuery extends BaseQuery {
    private String title;
    private Integer boardId;
    private Integer pBoardId;
    private Integer status;
    private String orderBy;
    private String userId;
    private boolean isAdmin;
    private Integer topType;
    private Date createTime;
    private LocalDate startCreateTime;
    private LocalDate endCreateTime;

    public LocalDate getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(LocalDate startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public LocalDate getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(LocalDate endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Integer getTopType() {
        return topType;
    }

    public void setTopType(Integer topType) {
        this.topType = topType;
    }

    public String getUserId() {
        return userId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-8")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public Integer getpBoardId() {
        return pBoardId;
    }

    public void setpBoardId(Integer pBoardId) {
        this.pBoardId = pBoardId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
