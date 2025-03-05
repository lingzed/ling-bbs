package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.commons.CommonMsg;
import com.ling.config.WebConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.AttachmentUploadItem;
import com.ling.entity.dto.WrapperAttachmentList;
import com.ling.entity.dto.query.ArticleQueryDto;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.po.Article;
import com.ling.entity.po.Attachment;
import com.ling.entity.vo.ArticleDetailVo;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.PageBean;
import com.ling.entity.vo.Result;
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
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private Logger log = LoggerFactory.getLogger(ArticleController.class);
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
     * @param status
     * @param page
     * @return
     */
    @GetMapping
    @AccessControl
    public Result<PageBean<ArticleVo>> loadArticles(HttpSession session, String title,
                                                    @Validation(required = false) Integer boardId,
                                                    @Validation(required = false) Integer pBoardId,
                                                    @RequestParam(defaultValue = "0") @Validation(max = 2, required = false) Integer orderType,
                                                    @Validation(required = false, max = 2) Integer status,
                                                    @RequestParam(defaultValue = "1") @Validation(min = 1, required = false) Integer page) {
        ArticleQueryDto articleQueryDto = new ArticleQueryDto();
        articleQueryDto.setTitle(title);
        articleQueryDto.setBoardId(Objects.equals(boardId, 0) ? null : boardId); // 板块id为0时，表示查询所有专栏的文章
        articleQueryDto.setpBoardId(pBoardId);
        // 根据前端传来的orderType，获取对应的排序规则
        ArticleOrderEnum articleOrderEnum = ArticleOrderEnum.getArticleOrderEnum(orderType);
        articleQueryDto.setOrderBy(articleOrderEnum.getOrderBySql());
        UserInfoSessionDto userinfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        articleQueryDto.setUserId(userinfo == null ? null : userinfo.getUserId());
        articleQueryDto.setAdmin(userinfo != null && userinfo.getIsAdmin());
        articleQueryDto.setStatus(status);// 查询文章的场景中，状态单独作为条件基本没用上，但是我还是留着
        articleQueryDto.setPage(page);
        articleQueryDto.setPageSize(Constant.NUM_10);
        PageBean<ArticleVo> articleVoPageBean = articleService.findVoByCondition(articleQueryDto);
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
        UserInfoSessionDto userInfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
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
    @GetMapping("/like/{articleId}")
    @AccessControl(loginRequired = true)
    public Result<Void> articleLike(HttpSession session, @PathVariable @Validation(max = 15) String articleId) {
        UserInfoSessionDto userInfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
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
    @PostMapping("/post")
    @AccessControl(loginRequired = true)
    public Result<String> postArticleHandle(HttpSession session, @Validation(max = 50) String title,
                                            @Validation Integer pBoardId,
                                            @Validation(required = false, min = 1) Integer boardId,
                                            String cover,
                                            @Validation String content,
                                            String mdContent,
                                            @Validation(required = false, max = 200) String summary,
                                            @Validation(max = 1) Integer editorType,
                                            WrapperAttachmentList attachments) {
        UserInfoSessionDto userinfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);

        // 若为md编辑器，则md内容不能为空
        if (EditorTypeEnum.MD_EDITOR.getType().equals(editorType) && StrUtil.isEmpty(mdContent))
            throw new BusinessException(ResponseCodeEnum.CODE_600);

        // 对附件进行校验：文件格式 && 大小 && 积分
        List<AttachmentUploadItem> items = attachments.getAttachments();
        if (!Objects.isNull(items) && !items.isEmpty()) {
            items.forEach(e -> {
                MultipartFile attachment = e.getAttachment();
                String suffix = StrUtil.getFilenameSuffix(attachment.getOriginalFilename());
                MIMETypeEnum mimeTypeEnum = MIMETypeEnum.getMIMEBySuffix(suffix);
                if (Objects.isNull(mimeTypeEnum))
                    throw new BusinessException(CommonMsg.UNSUPPORTED_FILE_FORMAT);
                Integer allowSize = SysCacheUtil.getSysSettingManager().getSysSetting4Post().getAttachmentSize();
                double size = attachment.getSize() / (1024.0 * 1024.0);
                if (size > allowSize)
                    throw new BusinessException(String.format(CommonMsg.FILE_TOO_LARGE, size, allowSize));
                if (e.getPoints() < 0)
                    throw new BusinessException(ResponseCodeEnum.CODE_600);
                e.setMimeTypeEnum(mimeTypeEnum);    // 校验通过添加附件类型
            });
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

        String articleId = articleService.processPostArticle(userinfo, article, items);
        return Result.success(articleId);
    }

    /**
     * 文章附件下载
     *
     * @param session
     * @param articleId
     * @param title
     * @param authorId
     * @param needPoints
     */
    @GetMapping("/attachment")
    @AccessControl(loginRequired = true)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<byte[]> attachmentDownload(HttpSession session,
                                                     @Validation(max = 15) String articleId,
                                                     @Validation String title,
                                                     @Validation(max = 15) String authorId,
                                                     @Validation Integer needPoints) {
        try {
            UserInfoSessionDto userInfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
            List<Attachment> attachments = articleService.processAttachmentDownload(userInfo, articleId, title, authorId, needPoints);
            if (attachments.size() > 1) {
                return downloadAsZip(attachments);           // 压缩下载
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

    /**
     * 多文件压缩下载
     *
     * @param attachments
     * @return
     * @throws IOException
     */
    private ResponseEntity<byte[]> downloadAsZip(List<Attachment> attachments) throws IOException {
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(aos)) {
            for (Attachment attachment : attachments) {
                File file = new File(attachment.getFilepath());
                if (!file.exists() || file.isDirectory())
                    throw new BusinessException(CommonMsg.FILE_NOT_FOUND);
                try (InputStream is = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(attachment.getFilename());
                    zos.putNextEntry(zipEntry);
                    IOUtils.copy(is, zos);
                    zos.closeEntry();
                }
            }
        }
        // 压缩流关闭后，再写出到字节数组，否则压缩文件内容缺失
        byte[] byteArray = aos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MIMETypeEnum.ZIP.getContentType());
        String filename = URLEncoder.encode(String.format(Constant.ZIP_FILENAME, StrUtil.formatDate("_yyyyMMdd_hhmmss")));
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        headers.add("Content-Length", String.valueOf(byteArray.length));

        return ResponseEntity.ok()
                .headers(headers)
                .body(byteArray);
    }
}
