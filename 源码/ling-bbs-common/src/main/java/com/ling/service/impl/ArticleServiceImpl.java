package com.ling.service.impl;

import com.ling.commons.CommonMsg;
import com.ling.config.WebConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.AttachmentUploadItem;
import com.ling.entity.dto.SysSettingManager;
import com.ling.entity.dto.UploadFileProp;
import com.ling.entity.dto.query.ArticleQueryDto;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.po.*;
import com.ling.entity.vo.ArticleDetailVo;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.AttachmentVo;
import com.ling.entity.vo.PageBean;
import com.ling.enums.*;
import com.ling.exception.BusinessException;
import com.ling.mappers.*;
import com.ling.service.*;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private AttachmentMapper attachmentMapper;
    @Resource
    private LikeRecordService likeRecordService;
    @Resource
    private UserMessageMapper userMessageMapper;
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

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public PageBean<Article> findByCondition(ArticleQueryDto articleQueryDto) {
        List<Article> list = articleMapper.selectByCondition(articleQueryDto);
        Long total = findTotalByCondition(articleQueryDto);
        return PageBean.of(total, articleQueryDto.getPage(), articleQueryDto.getPageSize(), list);
    }

    /**
     * 条件查询Vo
     *
     * @param articleQueryDto
     * @return
     */
    @Override
    public PageBean<ArticleVo> findVoByCondition(ArticleQueryDto articleQueryDto) {
        List<Article> list = articleMapper.selectByCondition(articleQueryDto);
        List<ArticleVo> articleVos = list.stream().map(e -> {
            ArticleVo vo = new ArticleVo();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
        Long total = findTotalByCondition(articleQueryDto);
        return PageBean.of(total, articleQueryDto.getPage(), articleQueryDto.getPageSize(), articleVos);
    }

    /**
     * 条件查询总数
     *
     * @param
     */
    @Override
    public Long findTotalByCondition(ArticleQueryDto articleQueryDto) {
        return articleMapper.selectCountByCondition(articleQueryDto);
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

    /**
     * 文章详情
     *
     * @param userinfo
     * @param articleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDetailVo articleDetail(UserInfoSessionDto userinfo, String articleId) {
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
            LikeRecord likeRecord = likeRecordService.findByTargetIdAndLikerIdAndLikeType(articleVo.getArticleId(), userinfo.getUserId(), 0);
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
    public void articleLike(UserInfoSessionDto userinfo, String articleId) {
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
            userMessageMapper.insert(userMessage);
        }
    }

    /**
     * 处理文章附件下载中积分情况记录&消息记录
     *
     * @param userinfo
     * @param articleId
     * @param title
     * @param authorId
     * @param needPoints
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Attachment> processAttachmentDownload(UserInfoSessionDto userinfo, String articleId, String title,
                                                      String authorId, Integer needPoints) {
        List<Attachment> attachments = attachmentMapper.selectByArticleIdAndUserId(articleId, authorId);
        if (Objects.isNull(attachments) || attachments.isEmpty())
            throw new BusinessException(CommonMsg.FILE_NOT_FOUND);
        // 作者 || 管理员 直接下载，不消耗积分，不更新积分记录，不记录消息
        if (!userinfo.getIsAdmin() || !Objects.equals(userinfo.getUserId(), authorId)) {
            // 如果已经下载过，直接下载
            AttachmentDownloadRecord downloadRecord = attachmentDownloadRecordMapper.selectByArticleIdAndDownloaderId(articleId, userinfo.getUserId());
            if (Objects.isNull(downloadRecord)) {
                UserInfo user = userInfoMapper.selectById(userinfo.getUserId());
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
            userMessageMapper.insert(userMessage);
        }
        attachmentMapper.updateAttachmentDownloadCount(articleId, authorId);    // 更新附件下载量
        return attachments;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String processPostArticle(UserInfoSessionDto userinfo, Article article, List<AttachmentUploadItem> items) {
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
        }

        return articleId;
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

        List<Attachment> attachments = items.stream().map(e -> {
            try {
                UploadFileProp ufProp = fileService.saveFile(e.getAttachment(), webConfig.getProjectFolderAttachment());

                Attachment attachment = new Attachment();
                attachment.setFileId(ufProp.getFileId());
                attachment.setArticleId(articleId);
                attachment.setUserId(userId);
                attachment.setFileSize(ufProp.getFilesize());
                attachment.setFilename(ufProp.getOriginalName());
                attachment.setDownloadCount(Constant.NUM_0);
                attachment.setFilepath(ufProp.getFilepath());
                attachment.setFiletype(e.getMimeTypeEnum().getType());
                attachment.setDownloadPoints(e.getPoints());
                return attachment;
            } catch (Exception ex) {
                throw new BusinessException(CommonMsg.FILE_UPLOAD_FAIL, ex);
            }
        }).collect(Collectors.toList());

        attachmentMapper.batchInsert(attachments);
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

//    /**
//     * 添加
//     *
//     * @param
//     */
//    @Override
//    public void add(ArticleDto articleDto) {
//        Article article = new Article();
//        Date date = new Date();
//        article.setCreateTime(date);
//        article.setUpdateTime(date);
//        BeanUtils.copyProperties(articleDto, article);
//        articleMapper.insert(article);
//    }

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
//     * 批量编辑
//     *
//     * @param
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void batchEdit(List<ArticleDto> list) {
//        Date date = new Date();
//        List<Article> articles = list.stream().map(e -> {
//            Article article = new Article();
//            article.setUpdateTime(date);
//            BeanUtils.copyProperties(e, article);
//            return article;
//        }).collect(Collectors.toList());
//        articleMapper.batchUpdate(articles);
//    }

    /**
     * 删除
     *
     * @param
     */
    @Override
    public void delete(List<String> list) {
        articleMapper.delete(list);
    }
}
