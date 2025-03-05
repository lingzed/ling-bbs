package com.ling.entity.po;

import java.util.Date;

/**
 * 文章实体类
 */
public class Article {
    private String articleId; //文章id
    private Integer boardId; //板块id
    private String boardName; //板块名称
    private Integer pBoardId; //父板块id
    private String pBoardName; //父板块名称
    private String avatar; //头像
    private String userId; //用户id
    private String nickName; //用户昵称
    private String userIdAddress; //用户地址
    private String title; //标题
    private String cover; //封面图
    private String content; //内容
    private String mdContent; //markdown内容
    private Integer editorType; //编辑器类型, 0: 富文本编辑器, 1: markdown编辑器
    private String summary; //摘要
    private Long readCount; //阅读数
    private Long likeCount; //点赞数
    private Long CommentCount; //评论数
    private Integer topType; //置顶类型, 0: 未置顶, 1: 已置顶
    private Integer attachmentType; //附件类型, 0: 没有附件, 1: 有附件
    private Integer status; //状态, 0: 已删除, 1: 待审核, 2: 已审核
    private Date createTime; //创建时间
    private Date updateTime; //更新时间

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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

    public Integer getpBoardId() {
        return pBoardId;
    }

    public void setpBoardId(Integer pBoardId) {
        this.pBoardId = pBoardId;
    }

    public String getpBoardName() {
        return pBoardName;
    }

    public void setpBoardName(String pBoardName) {
        this.pBoardName = pBoardName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserIdAddress() {
        return userIdAddress;
    }

    public void setUserIdAddress(String userIdAddress) {
        this.userIdAddress = userIdAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMdContent() {
        return mdContent;
    }

    public void setMdContent(String mdContent) {
        this.mdContent = mdContent;
    }

    public Integer getEditorType() {
        return editorType;
    }

    public void setEditorType(Integer editorType) {
        this.editorType = editorType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getReadCount() {
        return readCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(Long commentCount) {
        CommentCount = commentCount;
    }

    public Integer getTopType() {
        return topType;
    }

    public void setTopType(Integer topType) {
        this.topType = topType;
    }

    public Integer getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
