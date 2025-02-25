package com.ling.controller;

import com.alibaba.fastjson.JSON;
import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.commons.CommonMsg;
import com.ling.config.WebConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.query.ArticleQueryDto;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.po.Attachment;
import com.ling.entity.vo.ArticleDetailVo;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.PageBean;
import com.ling.entity.vo.Result;
import com.ling.enums.ArticleOrderEnum;
import com.ling.enums.MIMETypeEnum;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import com.ling.service.ArticleService;
import com.ling.utils.StrUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
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
    private WebConfig webConfig;

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
    public ResponseEntity<Object> attachmentDownload(HttpSession session,
                                                     @Validation(max = 15) String articleId, @Validation String title,
                                                     @Validation(max = 15) String authorId, @Validation Integer needPoints) throws IOException {
        UserInfoSessionDto userInfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        List<Attachment> attachments = articleService.processAttachmentDownload(userInfo, articleId, title, authorId, needPoints);
        try {
            if (attachments.size() > 1) {
                return downloadAsZip(attachments);           // 压缩下载
            } else {
                return downloadAsOne(attachments.get(0));    // 单文件下载
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 单文件下载
     *
     * @param attachment
     * @return
     * @throws IOException
     */
    private ResponseEntity<Object> downloadAsOne(Attachment attachment) throws IOException {
        File file = new File(toServerPath(attachment.getFilepath()));
        if (!file.exists() || file.isDirectory())
            throw new BusinessException(CommonMsg.FILE_NOT_FOUND);
        InputStream is = new FileInputStream(file);
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        IOUtils.copy(is, aos);      // 输入流中的数据复制到输出流
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MIMETypeEnum.getContentType(attachment.getFiletype()));
        String filename = URLEncoder.encode(attachment.getFilename());
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        headers.add("Content-Length", String.valueOf(attachment.getFileSize()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(aos.toByteArray());   // 响应体填充字节数据
    }

    /**
     * 多文件压缩下载
     *
     * @param attachments
     * @return
     * @throws IOException
     */
    private ResponseEntity<Object> downloadAsZip(List<Attachment> attachments) throws IOException {
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(aos)) {
            for (Attachment attachment : attachments) {
                String serverPath = toServerPath(attachment.getFilepath());
                File file = new File(serverPath);
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

    /**
     * 拼接为服务器绝对路径
     *
     * @param res
     * @return
     */
    private String toServerPath(String res) {
        return new StringBuffer()
                .append(webConfig.getProjectFolder())
                .append(File.separator)
                .append(webConfig.getProjectFolderAttachment())
                .append(res).toString();
    }
}
