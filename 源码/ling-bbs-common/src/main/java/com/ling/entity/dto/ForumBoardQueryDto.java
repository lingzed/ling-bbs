package com.ling.entity.dto;

/**
 * 论坛板块查询DTO
 */
public class ForumBoardQueryDto extends BaseQueryParamDto {
    private Integer boardId;    //  板块ID
    private Integer pBoardId;   //  父板块ID
    private String boardName;   //  板块名称
    private Integer postType; // 0：只能管理员发帖，1：所有人都可以发帖


    public ForumBoardQueryDto() {
    }

    public ForumBoardQueryDto(Integer boardId, Integer pBoardId, String boardName, Integer postType) {
        this.boardId = boardId;
        this.pBoardId = pBoardId;
        this.boardName = boardName;
        this.postType = postType;
    }

    /**
     * 获取
     *
     * @return boardId
     */
    public Integer getBoardId() {
        return boardId;
    }

    /**
     * 设置
     *
     * @param boardId
     */
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    /**
     * 获取
     *
     * @return pBoardId
     */
    public Integer getPBoardId() {
        return pBoardId;
    }

    /**
     * 设置
     *
     * @param pBoardId
     */
    public void setPBoardId(Integer pBoardId) {
        this.pBoardId = pBoardId;
    }

    /**
     * 获取
     *
     * @return boardName
     */
    public String getBoardName() {
        return boardName;
    }

    /**
     * 设置
     *
     * @param boardName
     */
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    /**
     * 获取
     *
     * @return postType
     */
    public Integer getPostType() {
        return postType;
    }

    /**
     * 设置
     *
     * @param postType
     */
    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public String toString() {
        return "ForumBoardQueryDto{boardId = " + boardId + ", pBoardId = " + pBoardId + ", boardName = " + boardName + ", postType = " + postType + "}";
    }
}
