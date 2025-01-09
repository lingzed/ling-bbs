package com.ling.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ling.utils.TreeUtil;

import java.util.Date;
import java.util.List;

/**
 * 论坛板块实体类
 */
public class ForumBoard implements TreeUtil.TreeNode<ForumBoard> {
    private Integer boardId;    //  板块ID
    private Integer pBoardId;   //  父板块ID
    private String boardName;   //  板块名称
    private String cover;       //  板块封面图地址
    private String description; //  板块描述
    private Integer sort;     //  板块排序值
    private Integer postType; // 0：只能管理员发帖，1：所有人都可以发帖
    @JsonIgnore
    private Date createTime;  // 创建时间
    @JsonIgnore
    private Date updateTime;  // 更新时间

    private List<ForumBoard> children; // 子板块列表


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<ForumBoard> getChildren() {
        return children;
    }

    @Override
    @JsonIgnore
    public String getId() {
        return String.valueOf(getBoardId());
    }

    @Override
    @JsonIgnore
    public String getParentId() {
        return String.valueOf(getpBoardId());
    }

    @Override
    public void setChildren(List<ForumBoard> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ForumBoard{" +
                "boardId=" + boardId +
                ", pBoardId=" + pBoardId +
                ", boardName='" + boardName + '\'' +
                ", cover='" + cover + '\'' +
                ", description='" + description + '\'' +
                ", sort=" + sort +
                ", postType=" + postType +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", children=" + children +
                '}';
    }
}
