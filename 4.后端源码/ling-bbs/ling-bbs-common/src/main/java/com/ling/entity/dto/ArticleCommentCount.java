package com.ling.entity.dto;

/**
 * 文章评论数量计数实体
 */
public class ArticleCommentCount {
    private String articleId;
    private Integer total;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
