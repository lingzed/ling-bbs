package com.ling.service;

import com.ling.entity.dto.query.ArticleQueryDto;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.po.Article;
import com.ling.entity.po.Attachment;
import com.ling.entity.vo.ArticleDetailVo;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.PageBean;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.List;

public interface ArticleService {
    /**
     * 条件查询
     *
     * @param
     */
    PageBean<Article> findByCondition(ArticleQueryDto articleQueryDto);

    /**
     * 条件查询
     *
     * @param articleQueryDto
     * @return
     */
    PageBean<ArticleVo> findVoByCondition(ArticleQueryDto articleQueryDto);

    /**
     * 条件查询总数
     *
     * @param
     */
    Long findTotalByCondition(ArticleQueryDto articleQueryDto);

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
     * 通过id查询
     *
     * @param
     */
    ArticleVo findById(Integer id);

    /**
     * 文章详情
     *
     * @param userinfo
     * @param articleId
     * @return
     */
    ArticleDetailVo articleDetail(UserInfoSessionDto userinfo, String articleId);

    /**
     * 文章点赞
     *
     * @param userinfo
     * @param articleId
     */
    void articleLike(UserInfoSessionDto userinfo, String articleId);

    /**
     * 处理文章附件下载中积分情况记录&消息记录
     *
     * @param userinfo
     * @param articleId
     * @param title
     * @param authorId
     * @param needPoints
     * @return
     */
    List<Attachment> processAttachmentDownload(UserInfoSessionDto userinfo, String articleId, String title, String authorId, Integer needPoints);

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
}
