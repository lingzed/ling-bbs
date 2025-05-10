package com.ling.entity.dto.board;

import com.ling.annotation.Validation;

/**
 * 编辑板块DTO
 */
public class EditBoard {
    @Validation
    private Integer boardId;   //  板块ID
    @Validation
    private String boardName;   //  板块名称
    private String cover;       //  板块封面图地址
    private String description; //  板块描述
    @Validation
    private Integer sort;     //  板块排序值
    @Validation
    private Integer postType; // 0：只能管理员发帖，1：所有人都可以发帖

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }
}
