package com.ling.mappers;

import com.ling.entity.dto.ArticleQueryDto;
import com.ling.entity.po.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<Article> selectByCondition(ArticleQueryDto articleQueryDto);

    /**
     * 条件查询总数量
     *
     * @param
     */
    Long selectCountByCondition(ArticleQueryDto articleQueryDto);

    /**
     * 查询所有
     */
    List<Article> selectAll();

    /**
     * 查询总数量
     */
    Long selectCount();

    /**
     * 通过id查询
     *
     * @param
     */
    Article selectById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    Article selectById(Integer id);

    /**
     * 添加
     *
     * @param
     */
    void insert(Article article);

    /**
     * 批量添加
     *
     * @param
     */
    void batchInsert(List<Article> list);

    /**
     * 编辑
     *
     * @param
     */
    void update(Article article);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchUpdate(List<Article> list);

    /**
     * 增加浏览量
     *
     * @param articleId
     */
    void incrementReadCount(String articleId);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<String> list);
}
