package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.commons.CommonMsg;
import com.ling.config.web.WebpageConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.SessionUserinfo;
import com.ling.entity.po.FileUseRecord;
import com.ling.entity.vo.UploadResultVo;
import com.ling.entity.vo.Result;
import com.ling.enums.FrequencyLimitTypeEnum;
import com.ling.exception.BusinessException;
import com.ling.service.FileService;
import com.ling.utils.StrUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/file")
public class FileController {
    private Logger log = LoggerFactory.getLogger(FileController.class);
    @Resource
    private FileService fileService;
    @Resource
    private WebpageConfig webpageConfig;


    /**
     * 图片上传接口
     *
     * @param imgFile
     * @return
     */
    @PostMapping("/upload/image/{imgDir:comment|avatar|cover}")
    @AccessControl(loginRequired = true, frequency = FrequencyLimitTypeEnum.UPLOAD_IMG_LIMIT)
    public Result<UploadResultVo> uploadImageHandle(MultipartFile imgFile, @PathVariable String imgDir,
                                                    Integer w, Integer h) {
        checkAllowImgSuffix(imgFile.getOriginalFilename());
        UploadResultVo uploadResultVo = fileService.processUploadImage(imgFile, imgDir, w, h);
        return Result.success(uploadResultVo);
    }

    /**
     * 文章图片上传接口
     *
     * @param session
     * @param request
     * @param imgFile
     * @return
     */
    @PostMapping("/upload/image/article")
    @AccessControl(loginRequired = true)
    public Result<UploadResultVo> uploadArticleImgHandle(HttpSession session, HttpServletRequest request,
                                                         MultipartFile imgFile) throws Exception {
        checkAllowImgSuffix(imgFile.getOriginalFilename());
        String imgId = request.getHeader(Constant.IMAGE_ID_HEADER);   // 请求头获取图片id
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);

        FileUseRecord fileUseRecord = new FileUseRecord();
        fileUseRecord.setRecordId(imgId);
        fileUseRecord.setUploaderId(userinfo.getUserId());

        UploadResultVo uploadResultVo = fileService.processArticleImageUpload(imgFile, fileUseRecord);
        return Result.success(uploadResultVo);
    }

    /**
     * 检查支持图片格式
     *
     * @param filename
     */
    private void checkAllowImgSuffix(String filename) {
        String suffix = StrUtil.getFilenameSuffix(filename);
        // 判断允许上传的图片格式
        if (!webpageConfig.getAllowImgSuffixList().contains(suffix))
            throw new BusinessException(CommonMsg.UNSUPPORTED_IMAGE_FORMAT);
    }

    /**
     * 读取图片接口
     *
     * @param imgDir
     * @param dateDir
     * @param filename
     */
    @GetMapping("/read/{imgDir}/{dateDir}/{filename}")
    public void readImgHandle(HttpServletResponse response,
                              @PathVariable String imgDir, @PathVariable String dateDir, @PathVariable String filename) throws Exception {
        String imgDirPath = fileService.getSysResourceDirPath(imgDir, dateDir);
        File file = new File(imgDirPath, filename);
        if (!file.exists())
            throw new BusinessException(CommonMsg.FILE_NOT_FOUND);
        response.setContentType("image/" + StrUtil.getFilenameSuffixWithoutDot(filename));
        response.setHeader(Constant.CACHE_CONTROL, "max-age=259200");  // 缓存3天
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        try (OutputStream os = response.getOutputStream();
             InputStream is = new FileInputStream(file)) {
            IOUtils.copy(is, aos);
            response.setHeader(Constant.CONTENT_LENGTH, String.valueOf(aos.size()));
            os.write(aos.toByteArray());
        }
    }

    /**
     * 更新图片为 已使用 接口
     *
     * @param request
     * @param session
     * @param targetId
     */
    @PutMapping("/used")
    @AccessControl(loginRequired = true)
    public Result<Void> editFile2UsedHandle(HttpServletRequest request, HttpSession session,
                                            @Validation String targetId,
                                            @Validation(max = 1) Short status) {
        String imageId = request.getHeader(Constant.IMAGE_ID_HEADER);
        if (Objects.isNull(imageId))
            throw new BusinessException(CommonMsg.INVALID_IMAGE_ID);
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);

        fileService.processArticleImageStatus(userinfo.getUserId(), targetId, imageId, status);
        return Result.success(null);
    }

    @DeleteMapping("/del")
    public Result<Void> del() {
        fileService.processDeleteUnusedFile();
        return Result.success(null);
    }
}
