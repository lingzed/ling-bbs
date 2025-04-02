package com.ling.mappers;

import com.ling.entity.dto.query.CommentQuery;
import com.ling.entity.po.Comment;
import com.ling.entity.vo.CommentLikeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}
