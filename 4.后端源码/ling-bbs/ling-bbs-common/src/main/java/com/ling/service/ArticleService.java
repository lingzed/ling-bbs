package com.ling.service;

import com.ling.entity.dto.AttachmentUploadItem;
import com.ling.entity.dto.query.ArticleQuery;
import com.ling.entity.dto.session.SessionUserinfo;
import com.ling.entity.po.Article;
import com.ling.entity.po.Attachment;
import com.ling.entity.vo.ArticleDetailVo;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.PageBean;
import com.ling.enums.TragetTopTypeEnum;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    /**
     * 条件查询
     *
     * @param
     */
    PageBean<Article> findByCondition(ArticleQuery articleQuery);

    /**
     * 条件查询
     *
     * @param articleQuery
     * @return
     */
    PageBean<ArticleVo> findVoByCondition(ArticleQuery articleQuery);

    /**
     * 条件查询
     * 返回: List, T: ArticleVo
     *
     * @param articleQuery
     * @return
     */
    List<ArticleVo> findVoListByCondition(ArticleQuery articleQuery);

    /**
     * 条件查询总数
     *
     * @param
     */
    Long findTotalByCondition(ArticleQuery articleQuery);

    /**
     * 查询所有
     */
    List<Article> findAll();

    /**
     * 查询总数
     */
    Long findTotal();

    /**
     * 通过id查询
     *
     * @param
     */
    ArticleVo findById(String id);

    /**
     * 通过 userId 和 status 查询
     *
     * @param userId
     * @param showPadding
     * @return
     */
    List<ArticleVo> findListByUserAndStatus(String userId, boolean showPadding);

    /**
     * 通过 userId 计数已审核数量
     *
     * @param userId
     * @return
     */
    Long countByUserAndAudit(String userId);

    /**
     * 文章详情
     *
     * @param userinfo
     * @param articleId
     * @return
     */
    ArticleDetailVo articleDetail(SessionUserinfo userinfo, String articleId);

    /**
     * 文章点赞
     *
     * @param userinfo
     * @param articleId
     */
    void articleLike(SessionUserinfo userinfo, String articleId);

    /**
     * 处理文章附件下载中积分情况记录&消息记录
     *
     * @param userinfo
     * @param articleId
     * @param title
     * @param authorId
     * @return
     */
    List<Attachment> processAttachmentDownload(SessionUserinfo userinfo, String articleId, String title, String authorId);

    /**
     * 处理文章发布
     *
     * @param userinfo
     * @param article
     * @param items
     */
    String processPostArticle(SessionUserinfo userinfo, Article article, List<AttachmentUploadItem> items);

    /**
     * 处理文章编辑(未包括附件)
     *
     * @param userinfo
     * @param article
     * @return
     */
    void processAttachmentEdit(SessionUserinfo userinfo, Article article);


    /**
     * 处理附件补充
     *
     * @param articleId
     * @param userId
     * @param attachmentUploadItem
     */
    void processAttachmentSupplement(String articleId, String userId, AttachmentUploadItem attachmentUploadItem);

    /**
     * 处理附件删除
     *
     * @param userId
     * @param articleId
     * @param attachmentIds
     */
    void processAttachmentDel(String userId, String articleId, List<String> attachmentIds);

    /**
     * 处理附件积分更新
     *
     * @param attachment
     */
    void processEditAttachmentPoints(SessionUserinfo userinfo, Attachment attachment);

    /**
     * 添加
     *
     * @param
     */
    void add(Article article);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<Article> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(Article article);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<Article> list);

    /**
     * 更新文章点赞量
     *
     * @param targetId
     * @param likeCount
     */
    void editLikeCount(String targetId, Integer likeCount);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<String> list);

    /**
     * 置顶文章
     *
     * @param articleId
     */
    TragetTopTypeEnum topArticle(String articleId);

    /**
     * 审核文章
     *
     * @param ids
     */
    void auditArticle(List<String> ids);
}
