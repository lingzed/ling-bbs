package com.ling.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ling.commons.CommonMsg;
import com.ling.config.AdminConfig;
import com.ling.config.WebConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.AttachmentUploadItem;
import com.ling.entity.dto.SysSettingManager;
import com.ling.entity.dto.UploadFileProp;
import com.ling.entity.dto.query.ArticleQuery;
import com.ling.entity.dto.session.SessionUserinfo;
import com.ling.entity.po.*;
import com.ling.entity.vo.*;
import com.ling.enums.*;
import com.ling.exception.BusinessException;
import com.ling.mappers.*;
import com.ling.service.*;
import com.ling.service.websocket.WebSocketServer;
import com.ling.utils.OkHttpUtil;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private AttachmentMapper attachmentMapper;
    @Resource
    private LikeRecordService likeRecordService;
    @Resource
    private UserMessageService userMessageService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserPointsRecordService userPointsRecordService;
    @Resource
    private AttachmentDownloadRecordMapper attachmentDownloadRecordMapper;
    @Resource
    private ForumBoardMapper forumBoardMapper;
    @Resource
    private FileService fileService;
    @Resource
    private WebConfig webConfig;
    @Resource
    private ArticleTxService articleTxService;
    @Resource
    private WebSocketServer webSocketServer;

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public PageBean<Article> findByCondition(ArticleQuery articleQuery) {
        List<Article> list = articleMapper.selectByCondition(articleQuery);
        Long total = findTotalByCondition(articleQuery);
        return PageBean.of(total, articleQuery.getPage(), articleQuery.getPageSize(), list);
    }

    /**
     * 条件查询Vo
     *
     * @param articleQuery
     * @return
     */
    @Override
    public PageBean<ArticleVo> findVoByCondition(ArticleQuery articleQuery) {
        List<ArticleVo> articleVos = findVoListByCondition(articleQuery);
        Long total = findTotalByCondition(articleQuery);
        return PageBean.of(total, articleQuery.getPage(), articleQuery.getPageSize(), articleVos);
    }

    public List<ArticleVo> findVoListByCondition(ArticleQuery articleQuery) {
        return articleMapper.selectByCondition(articleQuery).stream().map(e -> {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(e, articleVo);
            return articleVo;
        }).collect(Collectors.toList());
    }

    /**
     * 条件查询总数
     *
     * @param
     */
    @Override
    public Long findTotalByCondition(ArticleQuery articleQuery) {
        return articleMapper.selectCountByCondition(articleQuery);
    }

    /**
     * 查询所有
     */
    @Override
    public List<Article> findAll() {
        return articleMapper.selectAll();
    }

    /**
     * 查询总数
     */
    @Override
    public Long findTotal() {
        return articleMapper.selectCount();
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public ArticleVo findById(String id) {
        Article article = articleMapper.selectById(id);
        if (Objects.isNull(article)) return null;
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        return articleVo;
    }

    @Override
    public List<ArticleVo> findListByUserAndStatus(String userId, boolean showPadding) {
        return articleMapper.selectByUserAndStatus(userId, showPadding).stream()
                .map(e -> {
                    ArticleVo articleVo = new ArticleVo();
                    BeanUtils.copyProperties(e, articleVo);
                    return articleVo;
                }).collect(Collectors.toList());
    }

    @Override
    public Long countByUserAndAudit(String userId) {
        return articleMapper.countByUserAndAudit(userId);
    }

    /**
     * 文章详情
     *
     * @param userinfo
     * @param articleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDetailVo articleDetail(SessionUserinfo userinfo, String articleId) {
        ArticleVo articleVo = findById(articleId);
        if (Objects.isNull(articleVo))
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        // 不能访问已删除文章
        boolean isDeleted = articleVo.getStatus().equals(TargetStatusEnum.DELETED.getStatus());
        if (isDeleted)
            throw new BusinessException(ResponseCodeEnum.CODE_404);

        boolean isLogin = Objects.nonNull(userinfo);
        boolean isPending = articleVo.getStatus().equals(TargetStatusEnum.PENDING.getStatus());
        // 未登录不能访问待审核文章
        if (!isLogin && isPending)
            throw new BusinessException(ResponseCodeEnum.CODE_404);

        boolean isAuthor = isLogin && articleVo.getUserId().equals(userinfo.getUserId());
        // 非管理员登录，不能访问待审核文章，除非是作者本人
        if (isLogin && !userinfo.getIsAdmin() && isPending && !isAuthor)
            throw new BusinessException(ResponseCodeEnum.CODE_404);

        boolean isAudited = articleVo.getStatus().equals(TargetStatusEnum.AUDITED.getStatus());
        // 登录用户访问已审核文章才增加阅读量
        if (isLogin && isAudited) articleMapper.incrementReadCount(articleId);

        ArticleDetailVo articleDetailVo = new ArticleDetailVo();
        articleDetailVo.setArticle(articleVo);

        if (Objects.equals(articleVo.getAttachmentType(), AttachmentTypeEnum.HAVE_ATTACHMENT.getType())) {
            List<Attachment> attachments = attachmentMapper.selectByArticleIdAndUserId(articleVo.getArticleId(), articleVo.getUserId());
            List<AttachmentVo> attachmentVos = attachments.stream().map(e -> {
                AttachmentVo attachmentVo = new AttachmentVo();
                BeanUtils.copyProperties(e, attachmentVo);
                return attachmentVo;
            }).collect(Collectors.toList());
            articleDetailVo.setAttachments(attachmentVos);
        }
        // 登录用户才判断有没有对文章点赞
        if (isLogin) {
            LikeRecord likeRecord = likeRecordService.findByTargetIdAndLikerIdAndLikeType(
                    articleVo.getArticleId(), userinfo.getUserId(), 0);
            articleDetailVo.setHaveLike(likeRecord != null);
        }
        return articleDetailVo;
    }

    /**
     * 文章点赞
     *
     * @param userinfo
     * @param articleId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void articleLike(SessionUserinfo userinfo, String articleId) {
        ArticleVo article = findById(articleId);
        if (Objects.isNull(article))
            throw new BusinessException(CommonMsg.ARTICLE_NOT_EXISTS);
        if (!Objects.equals(article.getStatus(), TargetStatusEnum.AUDITED.getStatus()))
            throw new BusinessException(CommonMsg.ONLY_LIKE_AUDITED_ARTICLE);
        boolean canRecordMsg = likeRecordService.processLike(articleId, article.getUserId(), userinfo.getUserId(),
                LikeTypeEnum.ARTICLE.getType(),
                likeCount -> editLikeCount(articleId, likeCount));  // 具体行为：变更文章点赞量
        // 点赞且非文章或评论的作者，才记录点赞消息
        if (canRecordMsg) {
            UserMessage userMessage = new UserMessage();
            userMessage.setReceivedUserId(article.getUserId());
            userMessage.setArticleId(article.getArticleId());
            userMessage.setArticleTitle(article.getTitle());
            userMessage.setSenderAvatar(userinfo.getAvatar());
            userMessage.setSendUserId(userinfo.getUserId());
            userMessage.setSendNickName(userinfo.getNickName());
            userMessage.setMessageType(MessageTypeEnum.ARTICLE_LIKE.getType());
            userMessage.setMessageContent(Constant.ARTICLE_LIKE_MESSAGE_CONTENT);
            userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
            Date date = new Date();
            userMessage.setCreateTime(date);
            userMessage.setUpdateTime(date);
//            userMessageMapper.insert(userMessage);
            userMessageService.add(article.getUserId(), userMessage);
        }
    }

    /**
     * 处理文章附件下载中积分情况记录&消息记录
     *
     * @param userinfo
     * @param articleId
     * @param title
     * @param authorId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Attachment> processAttachmentDownload(SessionUserinfo userinfo, String articleId, String title,
                                                      String authorId) {
        List<Attachment> attachments = attachmentMapper.selectByArticleIdAndUserId(articleId, authorId);
        if (Objects.isNull(attachments) || attachments.isEmpty())
            throw new BusinessException(CommonMsg.FILE_NOT_FOUND);
        // 作者 || 管理员 直接下载，不消耗积分，不更新积分记录，不记录消息
        if (!userinfo.getIsAdmin() && !Objects.equals(userinfo.getUserId(), authorId)) {
            // 如果已经下载过，直接下载
            AttachmentDownloadRecord downloadRecord = attachmentDownloadRecordMapper
                    .selectByArticleIdAndDownloaderId(articleId, userinfo.getUserId());
            if (Objects.isNull(downloadRecord)) {
                UserInfo user = userInfoMapper.selectById(userinfo.getUserId());
                int needPoints = totalPoints(attachments);  // 计算下载需要的积分
                if (user.getCurrentIntegral() < needPoints)
                    throw new BusinessException(CommonMsg.INSUFFICIENT_POINTS);
                // 用户下载，扣除积分，记录积分操作
                userPointsRecordService.processUserPoints(userinfo.getUserId(), OperationTypeEnum.DOWNLOAD_ATTACHMENTS.getType(),
                        -needPoints);
                // 作者，增加积分，记录积分操作
                userPointsRecordService.processUserPoints(authorId, OperationTypeEnum.ATTACHMENT_DOWNLOADED.getType(),
                        needPoints);
                AttachmentDownloadRecord adr = new AttachmentDownloadRecord();
                adr.setArticleId(articleId);
                adr.setDownloaderId(userinfo.getUserId());
                adr.setDownloadTime(new Date());
                attachmentDownloadRecordMapper.insert(adr); // 下载行为记录
            }
            // 记录下载消息，作者接收
            UserMessage userMessage = new UserMessage();
            userMessage.setReceivedUserId(authorId);
            userMessage.setArticleId(articleId);
            userMessage.setArticleTitle(title);
            userMessage.setSenderAvatar(userinfo.getAvatar());
            userMessage.setSendUserId(userinfo.getUserId());
            userMessage.setSendNickName(userinfo.getNickName());
            userMessage.setMessageType(MessageTypeEnum.ATTACHMENT.getType());
            userMessage.setMessageContent(Constant.DOWNLOAD_MESSAGE_CONTENT);
            userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
            Date date = new Date();
            userMessage.setCreateTime(date);
            userMessage.setUpdateTime(date);
//            userMessageMapper.insert(userMessage);
            userMessageService.add(authorId, userMessage);
        }
        attachmentMapper.updateAttachmentDownloadCount(articleId, authorId);    // 更新附件下载量
        return attachments;
    }

    // 计算下载所需积分
    private Integer totalPoints(List<Attachment> attachments) {
        // 声明为AtomicInteger，因为lambda表达式内部无法直接修改外部的局部变量
        // lambda 表达式只能访问外部的 final 或 effectively final（事实上不可变）的局部变量
        // 因此我们可以定义一个包装类来包装这个变量，其引用地址值不变（满足事实上不可变），但是内部的值可以变化
        AtomicInteger total = new AtomicInteger(0);
        attachments.forEach(e -> total.addAndGet(e.getDownloadPoints()));
        return total.get();
    }

    /**
     * 处理文章发布
     *
     * @param userinfo
     * @param article
     * @param items
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String processPostArticle(SessionUserinfo userinfo, Article article, List<AttachmentUploadItem> items) {
        Integer pBid = article.getpBoardId();
        // 校验板块id和父板块id的准确性 && 判断板块是否只能管理员发文
        ForumBoard board = forumBoardMapper.selectByPidAndId(pBid, article.getBoardId());
        if (Objects.isNull(board) || (Objects.equals(board.getPostType(), Constant.NUM_0) && !userinfo.getIsAdmin())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String pBoardName = Objects.equals(pBid, Constant.NUM_0)
                ? null
                : forumBoardMapper.selectById(pBid).getBoardName();
        article.setBoardName(board.getBoardName());
        article.setpBoardName(pBoardName);

        String articleId = StrUtil.getRandomNum(Constant.NUM_15);
        article.setArticleId(articleId);
        article.setReadCount((long) Constant.NUM_0);
        article.setLikeCount((long) Constant.NUM_0);
        article.setCommentCount((long) Constant.NUM_0);
        article.setTopType(TragetTopTypeEnum.NO_TOP.getTopType());
        AttachmentTypeEnum attachmentType = Objects.isNull(items) || items.isEmpty()
                ? AttachmentTypeEnum.NOT_ATTACHMENT
                : AttachmentTypeEnum.HAVE_ATTACHMENT;
        article.setAttachmentType(attachmentType.getType());
        SysSettingManager sysSettingManager = SysCacheUtil.getSysSettingManager();
        // 管理员直接已审核，否则如果开启审核，那么默认待审核，反之已审核
        TargetStatusEnum targetStatus = userinfo.getIsAdmin() ? TargetStatusEnum.AUDITED
                : sysSettingManager.getSysSetting4Audit().isPostAudit()
                ? TargetStatusEnum.PENDING : TargetStatusEnum.AUDITED;
        article.setStatus(targetStatus.getStatus());
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);

        articleMapper.insert(article);  // 插入文章

        processAttachmentUpload(articleId, userinfo.getUserId(), items);    // 处理文章附件

        // 发文，更新用户积分
        Integer postPoints = sysSettingManager.getSysSetting4Post().getPostPoints();
        if (Objects.equals(targetStatus, TargetStatusEnum.AUDITED) && postPoints > 0) {
            userPointsRecordService.processUserPoints(userinfo.getUserId(), OperationTypeEnum.POST_ARTICLE.getType(), postPoints);
        } else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String msg = objectMapper.writeValueAsString(WSMessage.ofWebServer(WSMessageTypeEnum.PADDING_ARTICLE));
                webSocketServer.sendMessageToUser(Constant.ADMIN_CLIENT_NAME, msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return articleId;
    }

    /**
     * 处理文章编辑(未包括附件)
     *
     * @param userinfo
     * @param editArticle
     */
    @Override
    public void processAttachmentEdit(SessionUserinfo userinfo, Article editArticle) {
        Article article = articleMapper.selectById(editArticle.getArticleId());
        if (Objects.isNull(article))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        if (!Objects.equals(article.getUserId(), userinfo.getUserId()))
            throw new BusinessException(CommonMsg.UNAUTHORIZED_ACCESS);

        // 如果是Markdown编辑器，但没有Markdown内容，抛出异常
        boolean isMdEdit = Objects.equals(EditorTypeEnum.MD_EDITOR.getType(), article.getEditorType());
        boolean notMdContent = StrUtil.isEmpty(editArticle.getMdContent());
        if (isMdEdit && notMdContent)
            throw new BusinessException(ResponseCodeEnum.CODE_600);

        edit(editArticle);
    }

    /**
     * 处理附件补充
     *
     * @param articleId
     * @param userId
     * @param attachmentUploadItem
     */
    @Override
    public void processAttachmentSupplement(String articleId, String userId, AttachmentUploadItem attachmentUploadItem) {
        Article article = articleMapper.selectById(articleId);
        if (Objects.isNull(article))
            throw new BusinessException(CommonMsg.ARTICLE_NOT_EXISTS);
        if (!Objects.equals(article.getUserId(), userId))
            throw new BusinessException(CommonMsg.FILE_UPLOAD_FAIL);
        Attachment attachment = saveAttachment(articleId, userId, attachmentUploadItem);
        // 如果没有附件，更新为有附件
        if (Objects.equals(AttachmentTypeEnum.NOT_ATTACHMENT.getType(), article.getAttachmentType())) {
            articleMapper.updateAttachmentType(articleId, AttachmentTypeEnum.HAVE_ATTACHMENT.getType());
        }
        attachmentMapper.insert(attachment);
    }

    /**
     * 处理附件上传
     *
     * @param articleId
     * @param userId
     * @param items
     */
    private void processAttachmentUpload(String articleId, String userId, List<AttachmentUploadItem> items) {
        if (Objects.isNull(items) || items.isEmpty()) return;
        List<Attachment> attachments = items.stream()
                .map(e -> saveAttachment(articleId, userId, e))
                .collect(Collectors.toList());

        attachmentMapper.batchInsert(attachments);
    }

    // 保存附件
    private Attachment saveAttachment(String articleId, String userId, AttachmentUploadItem item) {
        try {
            UploadFileProp ufProp = fileService.saveFile(item.getAttachment(), webConfig.getProjectFolderAttachment());
            Attachment attachment = new Attachment();
            attachment.setFileId(ufProp.getFileId());
            attachment.setArticleId(articleId);
            attachment.setUserId(userId);
            attachment.setFilesize(ufProp.getFilesize());
            attachment.setFilename(ufProp.getOriginalName());
            attachment.setDownloadCount(Constant.NUM_0);
            attachment.setFilepath(ufProp.getFilepath());
            attachment.setFiletype(item.getMimeTypeEnum().getType());
            attachment.setDownloadPoints(item.getPoints());
            return attachment;
        } catch (Exception ex) {
            throw new BusinessException(CommonMsg.FILE_UPLOAD_FAIL, ex);
        }
    }

    /**
     * 处理附件删除
     *
     * @param userId
     * @param articleId
     * @param attachmentIds
     */
    @Transactional(rollbackFor = Exception.class)
    public void processAttachmentDel(String userId, String articleId, List<String> attachmentIds) {
        List<Attachment> attachments = attachmentMapper.selectByArticleAndUploaderAndAttachments(articleId, userId,
                attachmentIds);
        if (Objects.isNull(attachments) || attachments.isEmpty())
            throw new BusinessException(ResponseCodeEnum.CODE_600);

        attachmentMapper.delete(attachmentIds);
        attachments.forEach(e -> {
            File file = new File(e.getFilepath());
            if (!file.exists()) log.error(CommonMsg.FILE_NOT_FOUND);
            file.delete();
        });

        List<Attachment> attachmentsList = attachmentMapper.selectByArticleId(articleId);
        if (attachmentsList.isEmpty()) {
            articleMapper.updateAttachmentType(articleId, AttachmentTypeEnum.NOT_ATTACHMENT.getType());
        }
    }

    /**
     * 处理附件积分更新
     *
     * @param attachment
     */
    public void processEditAttachmentPoints(SessionUserinfo userinfo, Attachment attachment) {
        Attachment attachmentById = attachmentMapper.selectById(attachment.getFileId());
        if (Objects.isNull(attachmentById))
            throw new BusinessException(CommonMsg.FILE_NOT_FOUND);
        if (!Objects.equals(attachmentById.getUserId(), userinfo.getUserId()))
            throw new BusinessException(CommonMsg.UNAUTHORIZED_ACCESS);
        // 更新前后积分相同，不用处理，直接返回
        if (Objects.equals(attachmentById.getDownloadPoints(), attachment.getDownloadPoints())) return;
        attachmentMapper.update(attachment);
    }

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(Article article) {
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);
        articleMapper.insert(article);
    }

    /**
     * 批量添加
     *
     * @param
     */
    @Override
    public void batchAdd(List<Article> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setCreateTime(date);
            e.setUpdateTime(date);
        });
        articleMapper.batchInsert(list);
    }

//    /**
//     * 批量添加
//     *
//     * @param
//     */
//    @Override
//    public void batchAdd(List<ArticleDto> list) {
//        Date date = new Date();
//        List<Article> articles = list.stream().map(e -> {
//            Article article = new Article();
//            article.setCreateTime(date);
//            article.setUpdateTime(date);
//            BeanUtils.copyProperties(e, article);
//            return article;
//        }).collect(Collectors.toList());
//        articleMapper.batchInsert(articles);
//    }

    /**
     * 编辑
     *
     * @param
     */
    @Override
    public void edit(Article article) {
        Date date = new Date();
        article.setUpdateTime(date);
        articleMapper.update(article);
    }

//    /**
//     * 编辑
//     *
//     * @param
//     */
//    @Override
//    public void edit(ArticleDto articleDto) {
//        Article article = new Article();
//        Date date = new Date();
//        article.setUpdateTime(date);
//        BeanUtils.copyProperties(articleDto, article);
//        articleMapper.update(article);
//    }

    /**
     * 批量编辑
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<Article> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setUpdateTime(date);
        });
        articleMapper.batchUpdate(list);
    }

    /**
     * 更新文章点赞量
     *
     * @param targetId
     * @param likeCount
     */
    @Override
    public void editLikeCount(String targetId, Integer likeCount) {
        articleMapper.updateLikeCount(targetId, likeCount);
    }

//    /**
//     * 删除
//     *
//     * @param
//     */
//    @Override
//    public void delete(List<String> list) {
//        articleMapper.delete(list);
//    }


    @Override
    public void delete(List<String> list) {
        list.forEach(articleTxService::delete);
    }

    @Override
    public TragetTopTypeEnum topArticle(String articleId) {
        Article article = articleMapper.selectById(articleId);
        if (Objects.isNull(article))
            throw new BusinessException(CommonMsg.ARTICLE_NOT_EXISTS);

        TragetTopTypeEnum topType = Objects.equals(article.getTopType(), TragetTopTypeEnum.TOP.getTopType()) ?
                TragetTopTypeEnum.NO_TOP : TragetTopTypeEnum.TOP;

        Article editArticle = new Article();
        editArticle.setArticleId(article.getArticleId());
        editArticle.setTopType(topType.getTopType());
        editArticle.setUpdateTime(new Date());

        articleMapper.update(editArticle);
        return topType;
    }

    @Override
    public void auditArticle(List<String> ids) {
        ids.forEach(articleTxService::auditArticle);
    }
}
