package com.ling.entity.vo;

/**
 * 文章详情VO
 */
public class ArticleDetailVo {
    private ArticleVo article;  // 文章信息
    private AttachmentVo attachment; // 附件信息
    private boolean haveLike; // 是否点赞

    public ArticleVo getArticle() {
        return article;
    }

    public void setArticle(ArticleVo article) {
        this.article = article;
    }

    public AttachmentVo getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentVo attachment) {
        this.attachment = attachment;
    }

    public boolean isHaveLike() {
        return haveLike;
    }

    public void setHaveLike(boolean haveLike) {
        this.haveLike = haveLike;
    }
}
