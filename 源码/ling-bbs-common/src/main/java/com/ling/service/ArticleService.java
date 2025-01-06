package com.ling.service;

import com.ling.entity.dto.ArticleQueryDto;
import com.ling.entity.po.Article;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.PageBean;

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
     * @param articleId
     * @return
     */
    ArticleVo articleDetails(String articleId);

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
     * 删除
     *
     * @param
     */
    void delete(List<String> list);
}
