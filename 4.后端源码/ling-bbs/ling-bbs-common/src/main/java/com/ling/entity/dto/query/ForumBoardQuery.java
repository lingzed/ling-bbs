package com.ling.entity.dto.query;

import com.ling.annotation.Validation;

/**
 * 论坛板块查询DTO
 */
public class ForumBoardQuery extends BaseQuery {
    @Validation(required = false, min = 1)
    private Integer boardId;    //  板块ID
    @Validation(required = false)
    private Integer pBoardId;   //  父板块ID
    @Validation(required = false)
    private String boardName;   //  板块名称
    @Validation(required = false, max = 1)
    private Integer postType; // 0：只能管理员发帖，1：所有人都可以发帖

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public Integer getPBoardId() {
        return pBoardId;
    }

    public void setPBoardId(Integer pBoardId) {
        this.pBoardId = pBoardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }
}
