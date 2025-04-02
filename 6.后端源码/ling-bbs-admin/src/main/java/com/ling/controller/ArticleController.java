package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.commons.CommonMsg;
import com.ling.constant.Constant;
import com.ling.entity.dto.query.ArticleQuery;
import com.ling.entity.dto.query.CommentQuery;
import com.ling.entity.po.Attachment;
import com.ling.entity.vo.*;
import com.ling.enums.*;
import com.ling.exception.BusinessException;
import com.ling.service.ArticleService;
import com.ling.service.AttachmentService;
import com.ling.service.CommentService;
import com.ling.service.FileService;
import com.ling.utils.StrUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

    /**
     * 文章控制器
     */
    @RestController
    @RequestMapping("/article")
    public class ArticleController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    @Resource
    private ArticleService articleService;
    @Resource
    private AttachmentService attachmentService;
    @Resource
    private FileService fileService;
    @Resource
    private CommentService commentService;

    /**
     * 加载文章列表接口
     *
     * @param page
     * @param pageSize
     * @param title
     * @param author
     * @param pBoardId
     * @param boardId
     * @param attachmentType
     * @param status
     * @param topType
     * @return
     */
    @GetMapping
    @AccessControl
    public Result<PageBean<ArticleVo>> loadArticles(@Validation(min = 1) Integer page, @Validation Integer pageSize,
                                                    @Validation(required = false, max = 50) String title,
                                                    @Validation(required = false, max = 50) String author,
                                                    @Validation(required = false) Integer pBoardId,
                                                    @Validation(required = false, min = 1) Integer boardId,
                                                    @Validation(required = false, max = 1) Integer attachmentType,
                                                    @Validation(required = false, max = 2) Integer status,
                                                    @Validation(required = false, max = 1) Integer topType) {
        ArticleQuery articleQuery = new ArticleQuery();
        articleQuery.setPage(page);
        articleQuery.setPageSize(pageSize);
        articleQuery.setTitle(title);
        articleQuery.setAuthor(author);
        articleQuery.setpBoardId(pBoardId);
        articleQuery.setBoardId(boardId);
        articleQuery.setAttachmentType(attachmentType);
        articleQuery.setStatus(status);
        articleQuery.setTopType(topType);
        articleQuery.setOrderBy("create_time desc");

        PageBean<ArticleVo> voByCondition = articleService.findVoByCondition(articleQuery);

        return Result.success(voByCondition);
    }

    /**
     * 加载文章接口
     *
     * @param articleId
     * @return
     */
    @GetMapping("/{article-id}")
    @AccessControl
    public Result<ArticleVo> loadArticle(@PathVariable("article-id") @Validation(max = 15) String articleId) {
        ArticleVo articleVo = articleService.findById(articleId);
        if (Objects.isNull(articleVo) || Objects.equals(articleVo.getStatus(), TargetStatusEnum.DELETED.getStatus()))
            throw new BusinessException(ResponseCodeEnum.CODE_404);

        return Result.success(articleVo);
    }

    /**
     * 加载附件列表接口
     *
     * @param articleId
     * @return
     */
    @GetMapping("/{article-id}/attachment")
    @AccessControl
    public Result<List<AttachmentVo>> loadAttachments(@PathVariable("article-id")
                                                      @Validation(max = 15) String articleId) {
        ArticleVo articleVo = articleService.findById(articleId);
        if (Objects.isNull(articleVo) || Objects.equals(articleVo.getStatus(), TargetStatusEnum.DELETED.getStatus()))
            throw new BusinessException(ResponseCodeEnum.CODE_404);

        List<Attachment> attachments = attachmentService.findByArticleId(articleId);

        List<AttachmentVo> attachmentVos = attachments.stream().map(e -> {
            AttachmentVo attachmentVo = new AttachmentVo();
            BeanUtils.copyProperties(e, attachmentVo);
            return attachmentVo;
        }).collect(Collectors.toList());

        return Result.success(attachmentVos);
    }

    /**
     * 下载附件接口
     *
     * @param articleId
     * @return
     * @throws Exception
     */
    @GetMapping("/{article-id}/attachment/download")
    @AccessControl
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable("article-id")
                                                     @Validation(max = 15) String articleId) throws Exception {
        ArticleVo articleVo = articleService.findById(articleId);
        if (Objects.isNull(articleVo) || Objects.equals(articleVo.getStatus(), TargetStatusEnum.DELETED.getStatus()))
            throw new BusinessException(ResponseCodeEnum.CODE_404);

        List<Attachment> attachments = attachmentService.findByArticleId(articleId);
        if (Objects.isNull(attachments) || attachments.isEmpty())
            throw new BusinessException(CommonMsg.FILE_NOT_FOUND);

        if (attachments.size() > 1) {
            return fileService.downloadAsZip(attachments);           // 压缩下载
        } else {
            Attachment attachment = attachments.get(0);
            String contentType = MIMETypeEnum.getContentType(attachment.getFiletype());
            // 单文件下载
            return fileService.downloadFile(attachment.getFilepath(), attachment.getFilename(), contentType);
        }
    }

    /**
     * 加载1级评论树接口
     *
     * @param articleId
     * @param page
     * @return
     */
    @GetMapping("/{article-id}/comment/l1")
    @AccessControl
    public Result<PageBean<CommentVo>> loadComments(@PathVariable("article-id") @Validation(max = 15) String articleId,
                                                    @Validation(min = 1) Integer page) {
        CommentQuery commentQueryDto = new CommentQuery();
        commentQueryDto.setpCommentId(Constant.NUM_0);
        commentQueryDto.setArticleId(articleId);
        commentQueryDto.setPage(page);
        commentQueryDto.setPageSize(PageSizeEnum.SIZE_10.getPageSize());    // 固定每页10条目
        commentQueryDto.setOrderBy(CommentOrderEnum.NEW.getOrderBy());  // 默认最新排序

        PageBean<CommentVo> commentVo = commentService.findLeve1andLeve2Top10(commentQueryDto);

        return Result.success(commentVo);
    }

    /**
     * 加载2级评论列表接口
     *
     * @param articleId
     * @param pCommentId
     * @param page
     * @return
     */
    @GetMapping("/{article-id}/comment/l2")
    @AccessControl
    public Result<PageBean<CommentVo>> loadL2Comments(@PathVariable("article-id") @Validation(max = 15) String articleId,
                                                      @Validation(min = 1) Integer pCommentId,
                                                      @Validation(min = 1) Integer page) {

        CommentQuery commentQueryDto = new CommentQuery();
        commentQueryDto.setArticleId(articleId);
        commentQueryDto.setpCommentId(pCommentId);
        commentQueryDto.setPage(page);
        commentQueryDto.setOrderBy(CommentOrderEnum.HOT.getOrderBy());
        commentQueryDto.setPageSize(PageSizeEnum.SIZE_10.getPageSize());    // 固定每页10条目

        PageBean<CommentVo> level2Comments = commentService.findL2Comment(commentQueryDto);

        return Result.success(level2Comments);
    }

    /**
     * 文章置顶接口
     *
     * @param articleId
     * @return
     */
    @PutMapping("/{article-id}/top")
    @AccessControl
    public Result<TragetTopTypeEnum> topArticle(@PathVariable("article-id") @Validation String articleId) {
        TragetTopTypeEnum tragetTopTypeEnum = articleService.topArticle(articleId);
        return Result.success(tragetTopTypeEnum);
    }

    /**
     * 审核文章接口
     *
     * @param ids
     * @return
     */
    @PutMapping("/{ids}/audit")
    @AccessControl
    public Result<Void> auditArticle(@PathVariable @Validation List<String> ids) {
        articleService.auditArticle(ids);
        return Result.success();
    }

    /**
     * 删除文章
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @AccessControl
    public Result<Void> delArticle(@PathVariable @Validation List<String> ids) {
        articleService.delete(ids);
        return Result.success();
    }
}
