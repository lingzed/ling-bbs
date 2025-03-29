package com.ling.service.impl;

import com.ling.commons.CommonMsg;
import com.ling.constant.Constant;
import com.ling.entity.dto.SessionUserinfo;
import com.ling.entity.dto.query.CommentQuery;
import com.ling.entity.po.Article;
import com.ling.entity.po.Comment;
import com.ling.entity.po.UserMessage;
import com.ling.entity.vo.CommentLikeInfo;
import com.ling.entity.vo.CommentVo;
import com.ling.entity.vo.PageBean;
import com.ling.enums.*;
import com.ling.exception.BusinessException;
import com.ling.mappers.ArticleMapper;
import com.ling.mappers.CommentMapper;
import com.ling.mappers.UserMessageMapper;
import com.ling.service.CommentService;
import com.ling.service.LikeRecordService;
import com.ling.service.UserMessageService;
import com.ling.service.UserPointsRecordService;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private LikeRecordService likeRecordService;
    @Resource
    private UserMessageService userMessageService;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserPointsRecordService userPointsRecordService;

    /**
     * 条件查询
     *
     * @param commentQueryDto
     * @return
     */
    @Override
    public PageBean<CommentVo> findByCondition(CommentQuery commentQueryDto) {
        List<CommentVo> comments = commentMapper.selectByCondition(commentQueryDto).stream().map(e -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(e, commentVo);
            return commentVo;
        }).collect(Collectors.toList());
        Long total = findTotalByCondition(commentQueryDto);
        return PageBean.of(total, commentQueryDto.getPage(), commentQueryDto.getPageSize(), comments);
    }

    /**
     * 查询1级评论和1级评论下2级评论, 均为前10条
     *
     * @param
     */
    @Override
    public PageBean<CommentVo> findLeve1andLeve2Top10(CommentQuery commentQueryDto) {
        // 1级评论
        List<CommentVo> commentVos = commentMapper.selectByCondition(commentQueryDto).stream().map(e -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(e, commentVo);
            return commentVo;
        }).collect(Collectors.toList());
        Long total = findTotalByCondition(commentQueryDto);

        if (!commentVos.isEmpty()) {
            CommentQuery subCommentQuery = new CommentQuery();
            subCommentQuery.setArticleId(commentQueryDto.getArticleId());
            subCommentQuery.setUserId(commentQueryDto.getUserId());
            subCommentQuery.setAdmin(commentQueryDto.isAdmin());
            subCommentQuery.setOrderBy(CommentOrderEnum.HOT.getOrderBy());
            List<Integer> pCommentIds = commentVos.stream().map(CommentVo::getCommentId).collect(Collectors.toList());
            subCommentQuery.setpIds(pCommentIds);
            List<Comment> subComments = commentMapper.selectByCondition(subCommentQuery);   // 2级评论
            // 2级评论转换为vo同时按pid分组
            Map<Integer, List<CommentVo>> byPid = subComments.stream().map(e -> {
                CommentVo commentVo = new CommentVo();
                BeanUtils.copyProperties(e, commentVo);
                return commentVo;
            }).collect(Collectors.groupingBy(CommentVo::getpCommentId));
            // 组合1级和2级评论
            commentVos.forEach(e -> {
                List<CommentVo> subList = byPid.get(e.getCommentId());
                if (Objects.isNull(subList)) return;
                // 截取前10条
                List<CommentVo> limitList = subList.stream()
                        .limit(PageSizeEnum.SIZE_10.getPageSize())
                        .collect(Collectors.toList());
                Long subTotal = (long) subList.size();
                e.setSubComment(PageBean.of(subTotal, 1, PageSizeEnum.SIZE_10.getPageSize(), limitList));
            });
        }
        return PageBean.of(total, commentQueryDto.getPage(), commentQueryDto.getPageSize(), commentVos);
    }

    /**
     * 条件查询总数
     *
     * @param
     */
    @Override
    public Long findTotalByCondition(CommentQuery commentQueryDto) {
        return commentMapper.selectCountByCondition(commentQueryDto);
    }

    @Override
    public List<CommentVo> findVoListByCondition(CommentQuery commentQueryDto) {
        return commentMapper.selectByCondition(commentQueryDto).stream().map(e -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(e, commentVo);
            return commentVo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询所有
     */
    @Override
    public List<Comment> findAll() {
        return commentMapper.selectAll();
    }

    /**
     * 查询总数
     */
    @Override
    public Long findTotal() {
        return commentMapper.selectCount();
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public Comment findById(String id) {
        return commentMapper.selectById(id);
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public Comment findById(Integer id) {
        return commentMapper.selectById(id);
    }

    /**
     * 查询评论点赞信息
     *
     * @param commentId
     * @param userId
     * @return
     */
    @Override
    public CommentLikeInfo findCommentLikeInfo(Integer commentId, String userId) {
        return commentMapper.selectCommentLikeInfo(commentId, userId);
    }

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(Comment comment) {
        Date date = new Date();
        comment.setPostTime(date);
        commentMapper.insert(comment);
    }

//    /**
//     * 添加
//     *
//     * @param
//     */
//    @Override
//    public void add(CommentDto commentDto) {
//        Comment comment = new Comment();
//        Date date = new Date();
//        comment.setCreateTime(date);
//        comment.setUpdateTime(date);
//        BeanUtils.copyProperties(commentDto, comment);
//        commentMapper.insert(comment);
//    }

    /**
     * 批量添加
     *
     * @param
     */
    @Override
    public void batchAdd(List<Comment> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setPostTime(date);
        });
        commentMapper.batchInsert(list);
    }

//    /**
//     * 批量添加
//     *
//     * @param
//     */
//    @Override
//    public void batchAdd(List<CommentDto> list) {
//        Date date = new Date();
//        List<Comment> comments = list.stream().map(e -> {
//            Comment comment = new Comment();
//            comment.setCreateTime(date);
//            comment.setUpdateTime(date);
//            BeanUtils.copyProperties(e, comment);
//            return comment;
//        }).collect(Collectors.toList());
//        commentMapper.batchInsert(comments);
//    }

    /**
     * 编辑
     *
     * @param
     */
    @Override
    public void edit(Comment comment) {
        commentMapper.update(comment);
    }

    /**
     * 更新评论点赞量
     *
     * @param likeCount
     * @param commentId
     */
    @Override
    public void editLikeCount(Integer likeCount, Integer commentId) {
        commentMapper.updateLikeCount(likeCount, commentId);
    }

//    /**
//     * 编辑
//     *
//     * @param
//     */
//    @Override
//    public void edit(CommentDto commentDto) {
//        Comment comment = new Comment();
//        Date date = new Date();
//        comment.setUpdateTime(date);
//        BeanUtils.copyProperties(commentDto, comment);
//        commentMapper.update(comment);
//    }

    /**
     * 批量编辑
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<Comment> list) {
        commentMapper.batchUpdate(list);
    }

//    /**
//     * 批量编辑
//     *
//     * @param
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void batchEdit(List<CommentDto> list) {
//        Date date = new Date();
//        List<Comment> comments = list.stream().map(e -> {
//            Comment comment = new Comment();
//            comment.setUpdateTime(date);
//            BeanUtils.copyProperties(e, comment);
//            return comment;
//        }).collect(Collectors.toList());
//        commentMapper.batchUpdate(comments);
//    }

    /**
     * 删除
     *
     * @param
     */
    @Override
    public void delete(List<Integer> list) {
        commentMapper.delete(list);
    }

    /**
     * 处理评论点赞
     *
     * @param commentId
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processCommentLike(Integer commentId, SessionUserinfo userInfo) {
        Comment comment = findById(commentId);
        if (Objects.isNull(comment))
            throw new BusinessException(CommonMsg.COMMENT_NOT_EXISTS);
        // 已审核才能点赞
        if (!Objects.equals(comment.getStatus(), TargetStatusEnum.AUDITED.getStatus()))
            throw new BusinessException(CommonMsg.ONLY_LIKE_AUDITED_COMMENT);
        boolean canRecordMsg = likeRecordService.processLike(String.valueOf(commentId), comment.getSenderId(),
                userInfo.getUserId(),
                LikeTypeEnum.COMMENT.getType(),
                likeCount -> editLikeCount(likeCount, commentId));
        if (canRecordMsg) {
            UserMessage userMessage = new UserMessage();
            userMessage.setReceivedUserId(comment.getSenderId());
            userMessage.setCommentId(commentId);
            userMessage.setSenderAvatar(userInfo.getAvatar());
            userMessage.setSendUserId(userInfo.getUserId());
            userMessage.setSendNickName(userInfo.getNickName());
            userMessage.setMessageType(MessageTypeEnum.COMMENT_LIKE.getType());
            userMessage.setMessageContent(Constant.COMMENT_LIKE_MESSAGE_CONTENT);
            userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
            Date date = new Date();
            userMessage.setCreateTime(date);
            userMessage.setUpdateTime(date);
//            userMessageMapper.insert(userMessage);
            userMessageService.add(comment.getSenderId(), userMessage);
        }
    }

    /**
     * 处理评论置顶
     *
     * @param userInfo
     * @param commentId
     * @param topType
     */
    @Override
    public void processCommentTop(SessionUserinfo userInfo, Integer commentId, Integer topType) {
        CommentQuery commentQueryDto = new CommentQuery();
        commentQueryDto.setCommentId(commentId);
        commentQueryDto.setAdmin(userInfo.getIsAdmin());
        commentQueryDto.setUserId(userInfo.getUserId());
        List<Comment> comments = commentMapper.selectByCondition(commentQueryDto);
        // 检查评论是否存在
        if (Objects.isNull(comments) || comments.isEmpty())
            throw new BusinessException(CommonMsg.COMMENT_NOT_EXISTS);
        // 2级评论不能置顶
        if (!Objects.equals(comments.get(0).getpCommentId(), Constant.NUM_0))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        String articleId = comments.get(0).getArticleId();
        Article article = articleMapper.selectById(articleId);
        // 只有作者才能置顶
        if (!Objects.equals(article.getUserId(), userInfo.getUserId()))
            throw new BusinessException(CommonMsg.ONLY_AUTHOR_TOP_COMMENT);
        // 若更新置顶状态与当前置顶状态一致，直接中断，避免重复操作
        if (Objects.equals(comments.get(0).getTopType(), topType))
            return;
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setTopType(topType);
        edit(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommentVo processPostL1Comment(Comment comment, boolean needAudit) {
        Article article = articleMapper.selectById(comment.getArticleId());
        // 判断文章 是否存在 | 是否已审核
        if (Objects.isNull(article) && !Objects.equals(article.getStatus(), TargetStatusEnum.AUDITED.getStatus()))
            throw new BusinessException(CommonMsg.ARTICLE_NOT_EXISTS);
        // 记录评论
        add(comment);
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        // 若设置评论不需要审核，直接让评论通过审核
        if (!needAudit)
            processPassCommentReview(true, comment.getSenderAvatar(), comment.getSenderId(),
                    comment.getSenderNickname(), article, comment, article.getUserId());
        return commentVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommentVo processPostL2Comment(Comment comment, boolean needAudit) {
        Article article = articleMapper.selectById(comment.getArticleId());
        // 判断文章 是否存在 | 是否已审核
        if (Objects.isNull(article) && !Objects.equals(article.getStatus(), TargetStatusEnum.AUDITED.getStatus()))
            throw new BusinessException(CommonMsg.ARTICLE_NOT_EXISTS);
        boolean noReceiver = Objects.isNull(comment.getReceiverId());   // 是否没有接收人
        CommentQuery commentQueryDto = new CommentQuery();
        commentQueryDto.setArticleId(comment.getArticleId());
        if (noReceiver) {
            commentQueryDto.setCommentId(comment.getpCommentId());
        } else {
            commentQueryDto.setpCommentId(comment.getpCommentId());
            commentQueryDto.setSenderId(comment.getReceiverId());
        }
        List<Comment> comments = commentMapper.selectByCondition(commentQueryDto);
        // 判断回复的评论是否存在
        if (Objects.isNull(comments) || comments.isEmpty()) {
            throw new BusinessException(CommonMsg.REPLY_COMMENT_NOT_EXISTS);
        }
        // 无接收人表示对1级评论回复，但可能查出2级评论，故判断
        if (noReceiver && !Objects.equals(comments.get(0).getpCommentId(), Constant.NUM_0)) {
            throw new BusinessException(CommonMsg.REPLY_COMMENT_NOT_EXISTS);
        }
        String msgReceiver = comments.get(0).getSenderId();
        if (!noReceiver) comment.setReceiverNickname(comments.get(0).getSenderNickname());
        // 记录评论
        add(comment);
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        // 若设置评论不需要审核，直接让评论通过审核
        if (!needAudit)
            processPassCommentReview(false, comment.getSenderAvatar(), comment.getSenderId(),
                    comment.getSenderNickname(), article, comment, msgReceiver);
        return commentVo;
    }

    /**
     * 处理评论审核通过的逻辑
     *
     * @param isLevel1
     * @param senderAvatar
     * @param senderId
     * @param senderNickName
     * @param article
     * @param comment
     * @param msgReceiver
     */
    private void processPassCommentReview(boolean isLevel1, String senderAvatar, String senderId, String senderNickName,
                                          Article article, Comment comment, String msgReceiver) {
        // 更新用户积分
        Integer points = SysCacheUtil.getSysSettingManager().getSysSetting4Comment().getCommentPoints();
        userPointsRecordService.processUserPoints(senderId, OperationTypeEnum.POST_COMMENT.getType(), points);
        // 更新文章评论数量
        articleMapper.updateCommentCount(comment.getArticleId(), Constant.NUM_1);
        // 接收人为当前用户，不发消息
        if (!Objects.equals(msgReceiver, senderId)) {
            UserMessage userMessage = new UserMessage();
            userMessage.setReceivedUserId(msgReceiver);
            userMessage.setArticleId(article.getArticleId());
            userMessage.setArticleTitle(article.getTitle());
            userMessage.setCommentId(comment.getCommentId());
            userMessage.setSenderAvatar(senderAvatar);
            userMessage.setSendUserId(senderId);
            userMessage.setSendNickName(senderNickName);
            userMessage.setMessageType(MessageTypeEnum.COMMENT.getType());
            String msgContent = isLevel1 ? Constant.ARTICLE_COMMENT_MESSAGE_CONTENT : Constant.BACK_COMMENT_MESSAGE_CONTENT;
            userMessage.setMessageContent(msgContent);
            userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
            Date date = new Date();
            userMessage.setCreateTime(date);
            userMessage.setUpdateTime(date);
//            userMessageMapper.insert(userMessage);
            userMessageService.add(msgReceiver, userMessage);
        }
    }
}
