package com.ling.service;

import com.ling.entity.dto.SessionUserinfo;
import com.ling.entity.dto.query.CommentQuery;
import com.ling.entity.po.Comment;
import com.ling.entity.vo.CommentLikeInfo;
import com.ling.entity.vo.CommentVo;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface CommentService {
    /**
     * 条件查询
     *
     * @param
     */
    PageBean<CommentVo> findByCondition(CommentQuery commentQueryDto);

    /**
     * 查询1级评论和1级评论下2级评论, 均为前10条
     *
     * @param commentQueryDto
     * @return
     */
    PageBean<CommentVo> findLeve1andLeve2Top10(CommentQuery commentQueryDto);

    /**
     * 条件查询总数
     *
     * @param
     */
    Long findTotalByCondition(CommentQuery commentQueryDto);

    /**
     * 条件查询
     * 返回List
     *
     * @param commentQueryDto
     * @return
     */
    List<CommentVo> findVoListByCondition(CommentQuery commentQueryDto);

    /**
     * 查询所有
     */
    List<Comment> findAll();

    /**
     * 查询总数
     */
    Long findTotal();

    /**
     * 通过id查询
     *
     * @param
     */
    Comment findById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    Comment findById(Integer id);

    /**
     * 查询评论点赞信息
     *
     * @param commentId
     * @param userId
     * @return
     */
    CommentLikeInfo findCommentLikeInfo(Integer commentId, String userId);

    /**
     * 添加
     *
     * @param
     */
    void add(Comment comment);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<Comment> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(Comment comment);

    /**
     * 更新评论点赞量
     *
     * @param likeCount
     * @param commentId
     */
    void editLikeCount(Integer likeCount, Integer commentId);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<Comment> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);

    /**
     * 处理评论点赞
     *
     * @param commentId
     * @param userInfo
     * @return
     */
    void processCommentLike(Integer commentId, SessionUserinfo userInfo);

    /**
     * 处理评论置顶
     *
     * @param userInfo
     * @param commentId
     * @param topType
     */
    void processCommentTop(SessionUserinfo userInfo, Integer commentId, Integer topType);

    /**
     * 处理发送评论
     *
     * @param userinfo
     * @param comment
     * @param needAudit
     * @return
     */
//    CommentVo processPostComment(UserInfoSessionDto userinfo, Comment comment, boolean needAudit);


    /**
     * 处理1级评论发布
     *
     * @param comment
     * @param needAudit
     * @return
     */
    CommentVo processPostL1Comment(Comment comment, boolean needAudit);


    /**
     * 处理2级评论发布
     *
     * @param comment
     * @param needAudit
     * @return
     */
    CommentVo processPostL2Comment(Comment comment, boolean needAudit);

//    void processReviewComment();
}
