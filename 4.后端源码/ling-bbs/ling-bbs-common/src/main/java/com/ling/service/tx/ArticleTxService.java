package com.ling.service.tx;

import com.ling.commons.CommonMsg;
import com.ling.config.AdminConfig;
import com.ling.constant.Constant;
import com.ling.entity.po.Article;
import com.ling.entity.po.UserMessage;
import com.ling.enums.MessageStatusEnum;
import com.ling.enums.MessageTypeEnum;
import com.ling.enums.OperationTypeEnum;
import com.ling.enums.TargetStatusEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.ArticleMapper;
import com.ling.service.UserMessageService;
import com.ling.service.UserPointsRecordService;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * ArticleServiceImpl事务代理
 */
@Service
public class ArticleTxService {
    private static final Logger log = LoggerFactory.getLogger(ArticleTxService.class);
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserMessageService userMessageService;
    @Resource
    private AdminConfig adminConfig;
    @Resource
    private UserPointsRecordService userPointsRecordService;

    /**
     * 删除文章
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Article byId = articleMapper.selectById(id);
        if (Objects.isNull(byId) || Objects.equals(byId.getStatus(), TargetStatusEnum.DELETED.getStatus())) return;

        Article article = new Article();
        article.setArticleId(id);
        article.setStatus(TargetStatusEnum.DELETED.getStatus());
        article.setUpdateTime(new Date());

        articleMapper.update(article);  // 删除

        String title = byId.getTitle();
        // 发送消息
        sendMsg(byId.getUserId(), byId.getArticleId(), title, String.format(Constant.DEL_ARTICLE_MESSAGE_CONTENT, title));
    }

    /**
     * 审核文章
     *
     * @param articleId
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditArticle(String articleId) {
        Article byId = articleMapper.selectById(articleId);
        if (Objects.equals(byId.getStatus(), TargetStatusEnum.DELETED.getStatus()))
            throw new BusinessException(CommonMsg.UNABLE_AUDIT_DELETE_ARTICLE);
        if (Objects.equals(byId.getStatus(), TargetStatusEnum.AUDITED.getStatus())) return;

        Article article = new Article();
        String aId = byId.getArticleId();
        article.setArticleId(aId);
        article.setStatus(TargetStatusEnum.AUDITED.getStatus());
        article.setUpdateTime(new Date());
        articleMapper.update(article);  // 审核文章

        // 更新用户积分
        Integer postPoints = SysCacheUtil.getSysSettingManager().getSysSetting4Post().getPostPoints();
        String author = byId.getUserId();
        if (postPoints > 0) {
            userPointsRecordService.processUserPoints(author, OperationTypeEnum.POST_ARTICLE.getType(), postPoints);
        }

        sendMsg(author, aId, byId.getTitle(), Constant.AUDIT_ARTICLE_MESSAGE_CONTENT);  // 发送消息
    }

    // 记录消息 && 推送通知
    public void sendMsg(String receiver, String articleId, String title, String content) {
        UserMessage userMessage = new UserMessage();
        userMessage.setReceivedUserId(receiver);
        userMessage.setArticleId(articleId);
        userMessage.setArticleTitle(title);
        userMessage.setMessageType(MessageTypeEnum.SYS_MESSAGE.getType());
        userMessage.setMessageContent(content);
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        Date date = new Date();
        userMessage.setCreateTime(date);
        userMessage.setUpdateTime(date);

        userMessageService.add(userMessage);

        userMessageService.requestPushNotice(receiver);
    }
}
