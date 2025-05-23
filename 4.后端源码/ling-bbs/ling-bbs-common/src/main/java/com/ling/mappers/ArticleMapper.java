package com.ling.mappers;

import com.ling.entity.dto.query.ArticleQuery;
import com.ling.entity.po.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface ArticleMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<Article> selectByCondition(ArticleQuery articleQuery);

    /**
     * 条件查询总数量
     *
     * @param
     */
    Long selectCountByCondition(ArticleQuery articleQuery);

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
     * 通过 userId && status 查询
     *
     * @param userId
     * @param showPadding
     * @return
     */
    List<Article> selectByUserAndStatus(String userId, boolean showPadding);

    /**
     * 通过 userId && status 查询总数
     *
     * @param userId
     * @return
     */
    Long countByUserAndAudit(String userId);

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
     * 更新: 有附件
     *
     * @param articleId
     * @param attachmentType
     */
    void updateAttachmentType(String articleId, Integer attachmentType);

    /**
     * 增加浏览量
     *
     * @param articleId
     */
    void incrementReadCount(String articleId);

    /**
     * 更新点赞量
     *
     * @param articleId
     * @param likeCount
     */
    void updateLikeCount(String articleId, Integer likeCount);

    /**
     * 更新评论数量
     *
     * @param articleId
     * @param commentCount
     */
    void updateCommentCount(String articleId, Integer commentCount);

    /**
     * 更新头像
     *
     * @param avatar
     * @param userId
     */
    void updateAvatar(String avatar, String userId);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<String> list);

    /**
     * 简单查询，通过id集合
     * @param ids
     * @return
     */
    List<Article> simpleSelectByIds(List<String> ids);

    /**
     * 批量审核文章
     * @param ids
     */
    void batchUpdateToAudit(Set<String> ids);

    /**
     * 批量删除文章
     * @param ids
     */
    void batchUpdateToDelete(Set<String> ids);

    /**
     * 更新文章板块名称
     * @param id
     * @param name
     */
    void updateBoardName(Integer id, String name);

    /**
     * 更新子板块的夫板块名称
     * @param id
     * @param name
     */
    void updateSubPBoardName(Integer id, String name);

    /**
     * 通过板块id删除文章
     * @param bId
     * @param isParent
     */
    void updateToDeleteByBoard(Integer bId, Boolean isParent);
}
