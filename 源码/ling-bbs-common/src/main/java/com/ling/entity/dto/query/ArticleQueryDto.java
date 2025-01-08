package com.ling.entity.dto.query;

import java.util.Date;

/**
 * 文章查询DTO
 */
public class ArticleQueryDto extends BaseQueryDto {
    private String title;
    private Integer boardId;
    private Integer pBoardId;
    private Integer status;
    private String orderBy;
    private String userId;
    private boolean isAdmin;
    private Integer topType;
    private Date createTime;

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
