package com.ling.service.tx;

import com.ling.commons.CommonMsg;
import com.ling.config.AdminConfig;
import com.ling.constant.Constant;
import com.ling.entity.po.Article;
import com.ling.entity.po.Comment;
import com.ling.entity.po.UserMessage;
import com.ling.enums.MessageStatusEnum;
import com.ling.enums.MessageTypeEnum;
import com.ling.enums.OperationTypeEnum;
import com.ling.enums.TargetStatusEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.ArticleMapper;
import com.ling.mappers.CommentMapper;
import com.ling.mappers.UserMessageMapper;
import com.ling.service.CommentService;
import com.ling.service.UserMessageService;
import com.ling.service.UserPointsRecordService;
import com.ling.service.impl.UserMessageServiceImpl;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * CommentServiceImpl事务代理类
 */
@Service
public class CommentTxService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserPointsRecordService userPointsRecordService;
    @Resource
    private UserMessageMapper userMessageMapper;
    @Resource
    private UserMessageService userMessageService;

    /**
     * 审核评论
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditComment(Integer id) {
        Comment comment = commentMapper.selectById(id);
        if (Objects.isNull(comment))
            throw new BusinessException(CommonMsg.COMMENT_NOT_EXISTS);

        Integer status = comment.getStatus();
        if (Objects.equals(status, TargetStatusEnum.AUDITED.getStatus())) return;
        // 删除的评论不能审核
        if (Objects.equals(status, TargetStatusEnum.DELETED.getStatus()))
            throw new BusinessException(CommonMsg.UNABLE_AUDIT_DELETE_COMMENT);

        Integer pid = comment.getpCommentId();
        boolean isL1 = Objects.equals(pid, Constant.NUM_0);     // 是否1级评论

        // 1级直接审核 2级先判断1级是否审核
        Comment pcd = commentMapper.selectById(pid);    // 父级评论
        if (!isL1 && Objects.equals(pcd.getStatus(), TargetStatusEnum.PENDING.getStatus())) {
            throw new BusinessException(String.format(CommonMsg.AUDIT_FIRST_LEVEL1, pid));
        }

        Comment editComment = new Comment();
        editComment.setCommentId(id);
        editComment.setStatus(TargetStatusEnum.AUDITED.getStatus());
        commentMapper.update(editComment);  // 审核

        // 更新积分
        Integer commentPoints = SysCacheUtil.getSysSettingManager().getSysSetting4Comment().getCommentPoints();
        userPointsRecordService.processUserPoints(comment.getSenderId(), OperationTypeEnum.POST_COMMENT.getType(), commentPoints);
        // 更新文章评论数
        articleMapper.updateCommentCount(comment.getArticleId(), Constant.NUM_1);


        String articleId = comment.getArticleId();
        Article article = articleMapper.selectById(articleId);

        // 确认评论回复方和评论发送方
        String receiver = isL1 ? article.getUserId() :
                comment.getReceiverId() == null ? pcd.getSenderId() : comment.getReceiverId();
        String sender = comment.getSenderId();
        // 不一样才发消息
        if (!Objects.equals(sender, receiver)) {
            UserMessage userMessage = new UserMessage();
            userMessage.setReceivedUserId(receiver);
            userMessage.setArticleId(articleId);
            userMessage.setArticleTitle(article.getTitle());
            userMessage.setCommentId(id);
            userMessage.setSenderAvatar(comment.getSenderAvatar());
            userMessage.setSendUserId(comment.getSenderId());
            userMessage.setSendNickName(comment.getSenderNickname());
            userMessage.setMessageType(MessageTypeEnum.COMMENT.getType());
            userMessage.setMessageContent(isL1 ? Constant.ARTICLE_COMMENT_MESSAGE_CONTENT : Constant.BACK_COMMENT_MESSAGE_CONTENT);
            userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
            Date date = new Date();
            userMessage.setCreateTime(date);
            userMessage.setUpdateTime(date);
            userMessageMapper.insert(userMessage);  // 记录消息

            userMessageService.requestPushNotice(receiver); // 请求web端通知用户
        }
    }

    /**
     * 删除评论
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delComment(Integer id) {
        Comment comment = commentMapper.selectById(id);
        if (Objects.isNull(comment))
            throw new BusinessException(CommonMsg.COMMENT_NOT_EXISTS);
        if (Objects.equals(comment.getStatus(), TargetStatusEnum.DELETED.getStatus()))
            return;

        boolean isLevel1 = Objects.equals(comment.getpCommentId(), Constant.NUM_0); // 是否1级评论

        commentMapper.updateToDeleteL1AndL2(id, isLevel1);

        // 更新文章评论数量
        int count = isLevel1 ? commentMapper.countByIdsAndPIds(id) : 1;
        articleMapper.updateCommentCount(comment.getArticleId(), -Constant.NUM_1 * count);
    }
}
