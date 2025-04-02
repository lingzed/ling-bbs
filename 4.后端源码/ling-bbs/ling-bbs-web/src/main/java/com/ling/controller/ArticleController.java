package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.commons.CommonMsg;
import com.ling.constant.Constant;
import com.ling.entity.dto.AttachmentUploadItem;
import com.ling.entity.dto.WrapperAttachmentList;
import com.ling.entity.dto.query.ArticleQuery;
import com.ling.entity.dto.session.SessionUserinfo;
import com.ling.entity.po.Article;
import com.ling.entity.po.Attachment;
import com.ling.entity.vo.*;
import com.ling.enums.*;
import com.ling.exception.BusinessException;
import com.ling.service.ArticleService;
import com.ling.service.FileService;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文章控制器
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    @Resource
    private ArticleService articleService;
    @Resource
    private FileService fileService;

    /**
     * 加载文章列表
     *
     * @param session
     * @param title
     * @param boardId
     * @param pBoardId
     * @param orderType
     * @param page
     * @return
     */
    @GetMapping
    @AccessControl
    public Result<PageBean<ArticleVo>> loadArticles(HttpSession session, String title,
                                                    @Validation(required = false) Integer boardId,
                                                    @Validation(required = false) Integer pBoardId,
                                                    @RequestParam(defaultValue = "0") @Validation(max = 2, required = false) Integer orderType,
                                                    @RequestParam(defaultValue = "1") @Validation(min = 1, required = false) Integer page) {
        ArticleQuery articleQuery = new ArticleQuery();
        articleQuery.setTitle(title);
        articleQuery.setBoardId(Objects.equals(boardId, 0) ? null : boardId); // 板块id为0时，表示查询所有专栏的文章
        articleQuery.setpBoardId(pBoardId);
        // 根据前端传来的orderType，获取对应的排序规则
        ArticleOrderEnum articleOrderEnum = ArticleOrderEnum.getArticleOrderEnum(orderType);
        articleQuery.setOrderBy(articleOrderEnum.getOrderBySql());
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        int statusControl;
        if (userinfo == null) {
            statusControl = 2;
        } else {
            statusControl = userinfo.getIsAdmin() ? 0 : 1;
            articleQuery.setScUserId(userinfo.getUserId());
        }
        articleQuery.setStatusControl(statusControl);
        articleQuery.setPage(page);
        articleQuery.setPageSize(Constant.NUM_10);
        PageBean<ArticleVo> articleVoPageBean = articleService.findVoByCondition(articleQuery);
        return Result.success(articleVoPageBean);
    }

    /**
     * 获取文章详情
     *
     * @param session
     * @param articleId
     * @return
     */
    @GetMapping("/{articleId}")
    @AccessControl
    public Result<ArticleDetailVo> getArticleDetails(HttpSession session,
                                                     @PathVariable @Validation(max = 15) String articleId) {
        SessionUserinfo userInfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        ArticleDetailVo articleDetailVo = articleService.articleDetail(userInfo, articleId);
        return Result.success(articleDetailVo);
    }

    /**
     * 文章点赞
     *
     * @param session
     * @param articleId
     * @return
     */
    @PutMapping("/{articleId}/like")
    @AccessControl(loginRequired = true, frequency = FrequencyLimitTypeEnum.LIKE_LIMIT)
    public Result<Void> articleLike(HttpSession session, @PathVariable @Validation(max = 15) String articleId) {
        SessionUserinfo userInfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        articleService.articleLike(userInfo, articleId);
        return Result.success();
    }

    /**
     * 发布文章
     *
     * @param session
     * @param title
     * @param pBoardId
     * @param boardId
     * @param cover
     * @param content
     * @param mdContent
     * @param summary
     * @param editorType
     * @param attachments
     * @return
     */
    @PostMapping
    @AccessControl(loginRequired = true, frequency = FrequencyLimitTypeEnum.POST_LIMIT)
    public Result<Map<String, String>> postArticleHandle(HttpSession session, @Validation(max = 50) String title,
                                                         @Validation Integer pBoardId,
                                                         @Validation(required = false, min = 1) Integer boardId,
                                                         String cover,
                                                         @Validation String content,
                                                         String mdContent,
                                                         @Validation(required = false, max = 200) String summary,
                                                         @Validation(max = 1) Integer editorType,
                                                         WrapperAttachmentList attachments) {
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);

        // 若为md编辑器，则md内容不能为空
        if (EditorTypeEnum.MD_EDITOR.getType().equals(editorType) && StrUtil.isEmpty(mdContent))
            throw new BusinessException(ResponseCodeEnum.CODE_600);

        // 对附件进行校验
        List<AttachmentUploadItem> items = attachments.getAttachments();
        if (!Objects.isNull(items) && !items.isEmpty()) {
            items.forEach(this::checkAttachmentUploadItem);
        }

        Article article = new Article();
        article.setTitle(StringEscapeUtils.escapeHtml4(title));
        article.setpBoardId(pBoardId);
        article.setBoardId(boardId);
        article.setCover(cover);
        article.setContent(StringEscapeUtils.escapeHtml4(content));
        article.setMdContent(mdContent);
        article.setSummary(StringEscapeUtils.escapeHtml4(summary));
        article.setEditorType(editorType);
        article.setAvatar(userinfo.getAvatar());
        article.setUserId(userinfo.getUserId());
        article.setNickName(userinfo.getNickName());
        article.setUserIdAddress(userinfo.getProvince());

        String articleId = articleService.processPostArticle(userinfo, article, items); // 文章发布的操作
        Map<String, String> res = new HashMap<>();
        res.put("articleId", articleId);
        return Result.success(res);
    }

    /**
     * 修改文章
     *
     * @param session
     * @param articleId
     * @param title
     * @param cover
     * @param content
     * @param mdContent
     * @param summary
     * @return
     */
    @PutMapping("/{articleId}")
    @AccessControl(loginRequired = true)
    public Result<Map<String, String>> editArticleHandle(HttpSession session,
                                                         @PathVariable @Validation(max = 15) String articleId,
                                                         @Validation(required = false, max = 50) String title,
                                                         String cover,
                                                         @Validation String content,
                                                         String mdContent,
                                                         @Validation(required = false, max = 200) String summary) {
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);

        Article editArticle = new Article();
        editArticle.setArticleId(articleId);
        editArticle.setTitle(title);
        editArticle.setCover(cover);
        editArticle.setContent(content);
        editArticle.setMdContent(mdContent);
        editArticle.setSummary(summary);
        editArticle.setUserIdAddress(userinfo.getProvince());   // 作者的ip地址可能会发生变化
        boolean postAudit = SysCacheUtil.getSysSettingManager().getSysSetting4Audit().isPostAudit();
        // 管理员修改直接已审核，否则若未开启审核则为已审核，已开启为待审核
        TargetStatusEnum statusEnum = userinfo.getIsAdmin()
                ? TargetStatusEnum.AUDITED
                : postAudit ? TargetStatusEnum.PENDING : TargetStatusEnum.AUDITED;
        editArticle.setStatus(statusEnum.getStatus());
        editArticle.setUpdateTime(new Date());

        articleService.processAttachmentEdit(userinfo, editArticle);    // 修改文章的操作
        Map<String, String> res = new HashMap<>();
        res.put("articleId", articleId);
        return Result.success(res);
    }

    /**
     * 附件补充接口
     *
     * @param session
     * @param articleId
     * @param attachmentUploadItem
     * @return
     */
    @PostMapping("/{articleId}/attachment")
    @AccessControl(loginRequired = true)
    public Result<Void> attachmentSupplementHandle(HttpSession session,
                                                   @PathVariable @Validation(max = 15) String articleId,
                                                   @Validation AttachmentUploadItem attachmentUploadItem) {
        checkAttachmentUploadItem(attachmentUploadItem);
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        articleService.processAttachmentSupplement(articleId, userinfo.getUserId(), attachmentUploadItem);
        return Result.success();
    }

    // 对附件进行校验: 文件格式 && 大小 && 积分
    private void checkAttachmentUploadItem(AttachmentUploadItem item) {
        MultipartFile attachment = item.getAttachment();
        String filename = attachment.getOriginalFilename();
        if (StrUtil.isEmpty(filename))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        String suffix = StrUtil.getFilenameSuffix(filename);
        MIMETypeEnum mimeTypeEnum = MIMETypeEnum.getMIMEBySuffix(suffix);
        if (Objects.isNull(mimeTypeEnum))
            throw new BusinessException(CommonMsg.UNSUPPORTED_FILE_FORMAT);
        Integer allowSize = SysCacheUtil.getSysSettingManager().getSysSetting4Post().getAttachmentSize();
        double size = attachment.getSize() / (1024.0 * 1024.0);
        if (size > allowSize)
            throw new BusinessException(String.format(CommonMsg.FILE_TOO_LARGE, size, allowSize));
        if (item.getPoints() < 0)
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        item.setMimeTypeEnum(mimeTypeEnum);    // 校验通过添加附件类型
    }

    /**
     * 更新附件积分接口
     *
     * @param session
     * @param attachmentId
     * @return
     */
    @PutMapping("/{attachmentId}/points")
    @AccessControl(loginRequired = true)
    public Result<Void> attachmentEditPointsHandle(HttpSession session,
                                                   @PathVariable @Validation String attachmentId,
                                                   @Validation Integer points) {
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        Attachment attachment = new Attachment();
        attachment.setFileId(attachmentId);
        attachment.setDownloadPoints(points);
        articleService.processEditAttachmentPoints(userinfo, attachment);
        return Result.success();
    }

    /**
     * 删除附件接口
     *
     * @param session
     * @param attachmentIds
     * @return
     */
    @DeleteMapping("/{articleId}/{attachmentIds}")
    @AccessControl(loginRequired = true)
    public Result<Void> attachmentDelHandle(HttpSession session,
                                            @PathVariable @Validation(max = 15) String articleId,
                                            @PathVariable List<String> attachmentIds) {
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        articleService.processAttachmentDel(userinfo.getUserId(), articleId, attachmentIds);
        return Result.success();
    }

    /**
     * 文章附件下载
     *
     * @param session
     * @param articleId
     */
    @GetMapping("/{articleId}/attachment")
    @AccessControl(loginRequired = true)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<byte[]> attachmentDownload(HttpSession session,
                                                     @PathVariable @Validation(max = 15) String articleId) {
        try {
            ArticleVo article = articleService.findById(articleId);
            if (Objects.isNull(article))
                throw new BusinessException(CommonMsg.ARTICLE_NOT_EXISTS);
            SessionUserinfo userInfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
            List<Attachment> attachments = articleService.processAttachmentDownload(userInfo, articleId,
                    article.getTitle(), article.getUserId());
            if (attachments.size() > 1) {
                return fileService.downloadAsZip(attachments);           // 压缩下载
            } else {
                Attachment attachment = attachments.get(0);
                String contentType = MIMETypeEnum.getContentType(attachment.getFiletype());
                // 单文件下载
                return fileService.downloadFile(attachment.getFilepath(), attachment.getFilename(), contentType);
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.FILE_DOWNLOAD_FAIL, e);
        }
    }
}
