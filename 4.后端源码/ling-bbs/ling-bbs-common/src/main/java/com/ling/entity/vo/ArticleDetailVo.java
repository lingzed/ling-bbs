package com.ling.entity.vo;

import java.util.List;

/**
 * 文章详情VO
 */
public class ArticleDetailVo {
    private ArticleVo article;  // 文章信息
    private List<AttachmentVo> attachments; // 附件信息
    private Boolean haveLike; // 是否点赞

    public ArticleVo getArticle() {
        return article;
    }

    public void setArticle(ArticleVo article) {
        this.article = article;
    }

    public List<AttachmentVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentVo> attachments) {
        this.attachments = attachments;
    }

    public Boolean isHaveLike() {
        return haveLike;
    }

    public void setHaveLike(Boolean haveLike) {
        this.haveLike = haveLike;
    }
}
