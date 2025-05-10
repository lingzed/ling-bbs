package com.ling.service.impl;

import com.ling.commons.CommonMsg;
import com.ling.constant.Constant;
import com.ling.entity.dto.ArticleCommentCount;
import com.ling.entity.dto.session.SessionUserinfo;
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
import com.ling.service.CommentService;
import com.ling.service.LikeRecordService;
import com.ling.service.UserMessageService;
import com.ling.service.UserPointsRecordService;
import com.ling.service.tx.CommentTxService;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

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
    @Resource
    private CommentTxService commentTxService;

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
    public PageBean<CommentVo> findL1AndL2Top10(CommentQuery commentQueryDto) {
        Article article = articleMapper.selectById(commentQueryDto.getArticleId());
        if (Objects.isNull(article))
            throw new BusinessException(CommonMsg.ARTICLE_NOT_EXISTS);

        // 1级评论id
        List<Integer> pIds = new ArrayList<>();

        // 1级评论
        List<CommentVo> commentVos = commentMapper.selectByCondition(commentQueryDto).stream().map(e -> {
            // 收集1级评论id
            pIds.add(e.getCommentId());

            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
        commentQueryDto.setpCommentId(null);
        Long total = commentMapper.selectCountByCondition(commentQueryDto);  // 评论总数

        attachTop10SubCommentsToL1(commentQueryDto, commentVos, pIds); // 查询2级评论，组合1、2级评论

        return PageBean.of(total, commentQueryDto.getPage(), commentQueryDto.getPageSize(), commentVos);
    }

    /**
     * 从1级评论列表中查询出2级评论前10条，并与1级评论组合
     * @param commentQuery
     * @param l1List
     * @param pIdList
     */
    private void attachTop10SubCommentsToL1(CommentQuery commentQuery, List<CommentVo> l1List, List<Integer> pIdList) {
        if (CollectionUtils.isEmpty(l1List)) {
            return;
        }

        CommentQuery subQuery = new CommentQuery();
        subQuery.setArticleId(commentQuery.getArticleId());
        subQuery.setUserId(commentQuery.getUserId());
        subQuery.setStatusControl(commentQuery.getStatusControl());
        subQuery.setAdmin(commentQuery.isAdmin());
        subQuery.setOrderBy(CommentOrderEnum.HOT.getOrderBy());
        subQuery.setpIds(pIdList);

        // 查询2级评论
        List<Comment> subComments = commentMapper.selectByCondition(subQuery);

        // 按pid分组2级评论
        Map<Integer, List<CommentVo>> byPid = subComments.stream().map(e -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(e, commentVo);
            return commentVo;
        }).collect(Collectors.groupingBy(CommentVo::getpCommentId));

        // 组合1、2级评论
        l1List.forEach(e -> {
            List<CommentVo> subList = byPid.get(e.getCommentId());
            if (CollectionUtils.isEmpty(subList)) return;

            // 截取前10条
            List<CommentVo> limitList = subList.stream()
                    .limit(PageSizeEnum.SIZE_10.getPageSize())
                    .collect(Collectors.toList());
            Long subTotal = (long) subList.size();

            e.setSubComment(PageBean.of(subTotal, 1, PageSizeEnum.SIZE_10.getPageSize(), limitList));
        });
    }

    /**
     * 查询2级评论
     * @param commentQuery
     * @return
     */
    @Override
    public PageBean<CommentVo> findL2Comment(CommentQuery commentQuery) {
        Article article = articleMapper.selectById(commentQuery.getArticleId());
        if (Objects.isNull(article))
            throw new BusinessException(CommonMsg.ARTICLE_NOT_EXISTS);

        return findByCondition(commentQuery);
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
     * 批量id查询
     * @param ids
     * @return
     */
    @Override
    public List<Comment> findByIds(List<Integer> ids) {
        return commentMapper.selectByIds(ids);
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

//    @Override
//    public void auditComments(List<Integer> ids) {
//        ids.forEach(commentTxService::auditComment);
//    }

    /**
     * 批量审核评论
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditComments(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        List<Comment> comments = findByIds(ids);    //批量id查询

        if (CollectionUtils.isEmpty(comments)) {
            return;
        }

        // 预处理，筛选出未审核评论
        List<Comment> toAuditComments = comments.stream().filter(comment -> {
            Integer status = comment.getStatus();
            if (status.equals(TargetStatusEnum.DELETED.getStatus())) {
                throw new BusinessException(CommonMsg.UNABLE_AUDIT_DELETE_COMMENT);
            }

            return !status.equals(TargetStatusEnum.AUDITED.getStatus());
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(toAuditComments)) {
            return;
        }

        // 批量审核评论
        batchEditStatus(toAuditComments.stream()
                        .map(Comment::getCommentId)
                        .collect(Collectors.toList()),
                TargetStatusEnum.AUDITED.getStatus());

        Integer points = SysCacheUtil.getSysSettingManager().getSysSetting4Comment().getCommentPoints();    // 积分
        // 加积分、更新评论数、通知消息
        toAuditComments.forEach(comment -> {
            userPointsRecordService.processUserPoints(comment.getSenderId(), OperationTypeEnum.POST_COMMENT.getType(), points);
            articleMapper.updateCommentCount(comment.getArticleId(), Constant.NUM_1);
            // 记录消息并通知
            recordAndNotify(comment);
        });
    }

    /**
     * 记录消息并通知
     * @param comment
     */
    private void recordAndNotify(Comment comment) {
        String articleId = comment.getArticleId();
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            return;
        }

        Integer pid = comment.getpCommentId();
        log.info("父评论id：{}", pid);
        boolean isL1 = pid.equals(0);
        // 得到接收人
        String receiver = isL1 ? article.getUserId() :
                Optional.ofNullable(comment.getReceiverId()).orElseGet(() -> {
                    Comment pComment = findById(pid);
                    return pComment == null ? null : pComment.getSenderId();
                });

        if (receiver != null && !receiver.equals(comment.getSenderId())) {
            // 记录消息
            UserMessage userMessage = new UserMessage();
            userMessage.setReceivedUserId(receiver);
            userMessage.setArticleId(articleId);
            userMessage.setArticleTitle(article.getTitle());
            userMessage.setCommentId(isL1 ? pid : comment.getpCommentId());
            userMessage.setSenderAvatar(comment.getSenderAvatar());
            userMessage.setSendUserId(comment.getSenderId());
            userMessage.setSendNickName(comment.getSenderNickname());
            userMessage.setMessageType(MessageTypeEnum.COMMENT.getType());
            String content = isL1 ? Constant.ARTICLE_COMMENT_MESSAGE_CONTENT : Constant.BACK_COMMENT_MESSAGE_CONTENT;
            userMessage.setMessageContent(content);
            userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
            Date date = new Date();
            userMessage.setCreateTime(date);
            userMessage.setUpdateTime(date);
            userMessageService.add(userMessage);

            userMessageService.requestPushNotice(receiver); // 请求web端通知用户
        }
    }

    /**
     * 批量更新评论状态
     * @param ids
     * @param status
     */
    public void batchEditStatus(List<Integer> ids, Integer status) {
        commentMapper.batchUpdateStatus(ids, status);
    }

//    @Override
//    public void delComments(List<Integer> ids) {
//        ids.forEach(commentTxService::delComment);
//    }

    /**
     * 删除评论，同时减少文章评论数量
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delComments(List<Integer> ids) {
        List<Comment> comments = findByIds(ids);
        if (CollectionUtils.isEmpty(comments)) {
            return;
        }

        List<Comment> l2List = new ArrayList<>();  // 2级评论列表
        Set<Integer> toDelIds = new HashSet<>(); // 待删除id列表
        Map<String, Integer> commentDecCountMap = new HashMap<>();  // 文章与评论数的减少量

        // 筛选1级评论，统计其直接需要删除的数量
        comments.forEach(comment -> {
            if (comment == null || comment.getStatus().equals(TargetStatusEnum.DELETED.getStatus())) return;

            Integer id = comment.getCommentId();
            boolean isL1 = comment.getpCommentId().equals(0);

            if (isL1) {
                toDelIds.add(id);   // 收集1级评论id
                commentDecCountMap.merge(comment.getArticleId(), -1, Integer::sum);  // 1级评论本身为1个数量
            } else {
                l2List.add(comment);
            }
        });

        // 无需删除的评论，提前结束
        if (CollectionUtils.isEmpty(toDelIds) && CollectionUtils.isEmpty(l2List)) return;

        // 查出所有一级评论的2级子评论，并统计删除数量
        List<Comment> subComments = commentMapper.selectByPIds(toDelIds);
        subComments.forEach(subC -> {
            if (subC.getStatus().equals(TargetStatusEnum.DELETED.getStatus())) return;  // 子评论已删除，跳过
            toDelIds.add(subC.getCommentId());
            commentDecCountMap.merge(subC.getArticleId(), -1, Integer::sum);
        });

        // 处理孤立的2级评论（其父评论未在本次删除列表中）
        l2List.forEach(c -> {
            if (toDelIds.add(c.getCommentId())) {
                // 统计非1级子评论的2级评论对应文章评论减少量
                commentDecCountMap.merge(c.getArticleId(), -1, Integer::sum);
            }
        });

        // 批量删除评论
        if (!CollectionUtils.isEmpty(toDelIds)) {
            commentMapper.updateToDelete(toDelIds);
            log.info("删除评论: {}", toDelIds);
        }

        // 减少文章评论数量
        commentDecCountMap.forEach(articleMapper::updateCommentCount);
    }

    /**
     * 通过父id批量查询
     * @param ids
     * @return
     */
    @Override
    public List<Comment> findByPid(List<Integer> ids) {
        return commentMapper.selectByPids(ids);
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
    private void processPassCommentReview(boolean isLevel1, String senderAvatar, String senderId, String
            senderNickName,
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
