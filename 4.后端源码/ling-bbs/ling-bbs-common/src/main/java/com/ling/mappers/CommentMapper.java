package com.ling.mappers;

import com.ling.entity.dto.ArticleCommentCount;
import com.ling.entity.dto.query.CommentQuery;
import com.ling.entity.po.Comment;
import com.ling.entity.vo.CommentLikeInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface CommentMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<Comment> selectByCondition(CommentQuery commentQueryDto);

    /**
     * 条件查询总数量
     *
     * @param
     */
    Long selectCountByCondition(CommentQuery commentQueryDto);

    /**
     * 查询所有
     */
    List<Comment> selectAll();

    /**
     * 查询总数量
     */
    Long selectCount();

    /**
     * 通过id查询
     *
     * @param
     */
    Comment selectById(String id);

    /**
     * 批量id查询
     * @param ids
     * @return
     */
    List<Comment> selectByIds(List<Integer> ids);

    /**
     * 通过id查询
     *
     * @param
     */
    Comment selectById(Integer id);

    /**
     * 查询评论点赞信息
     *
     * @param commentId
     * @param userId
     * @return
     */
    CommentLikeInfo selectCommentLikeInfo(Integer commentId, String userId);

    /**
     * 通过pid集查询
     * @param pIds
     * @return
     */
    List<Comment> selectByPIds(Set<Integer> pIds);

    /**
     * 添加
     *
     * @param
     */
    void insert(Comment comment);

    /**
     * 批量添加
     *
     * @param
     */
    void batchInsert(List<Comment> list);

    /**
     * 编辑
     *
     * @param
     */
    void update(Comment comment);

    /**
     * 更新评论点赞量
     *
     * @param likeCount
     * @param commentId
     */
    void updateLikeCount(Integer likeCount, Integer commentId);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchUpdate(List<Comment> list);

    /**
     * 删除1级/2级评论
     *
     * @param commentId
     */
    void updateToDeleteL1AndL2(Integer commentId, Boolean isLevel1);

    /**
     * 删除评论
     * @param ids
     */
    void updateToDelete(Set<Integer> ids);

    /**
     * 批量删除1级评论
     * @param ids
     */
    void updateToDeleteL1OrL2(Set<Integer> ids);

    /**
     * 批量删除2级评论
     * @param ids
     */
    void updateToDeleteL2(List<Integer> ids);

    /**
     * 批量更新评论状态
     * @param ids
     * @param status
     */
    void batchUpdateStatus(List<Integer> ids, Integer status);

    /**
     * 统计评论数量，通过 ids && pIds
     *
     * @param commentId
     * @return
     */
    Integer countByIdsAndPIds(Integer commentId);

    /**
     * 统计1评论子评论的数量，通过 pIds
     * @param pIds
     * @return
     */
    List<ArticleCommentCount> countByPIds(Set<Integer> pIds);

    /**
     * 更新头像
     *
     * @param senderAvatar
     * @param senderId
     */
    void updateAvatar(String senderAvatar, String senderId);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);

    /**
     * 通过父id批量查询
     * @param ids
     * @return
     */
    List<Comment> selectByPids(List<Integer> ids);
}
