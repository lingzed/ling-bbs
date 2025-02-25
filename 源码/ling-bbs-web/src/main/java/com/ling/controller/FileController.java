package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.commons.CommonMsg;
import com.ling.config.WebConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.vo.FileUploadVo;
import com.ling.entity.vo.Result;
import com.ling.enums.ThumbnailSizeEnum;
import com.ling.exception.BusinessException;
import com.ling.utils.StrUtil;
import com.ling.utils.ThumbnailUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {
    private Logger log = LoggerFactory.getLogger(FileController.class);
    @Resource
    private WebConfig webConfig;

    /**
     * 图片上传接口
     *
     * @param imgFile
     * @return
     */
    @PostMapping("/upload/image/{imgDir:comment|avatar|cover}")
    @AccessControl(loginRequired = true)
    public Result<FileUploadVo> uploadImage(MultipartFile imgFile, @PathVariable String imgDir, Integer w, Integer h) {
        try {
            String filename = imgFile.getOriginalFilename();
            String suffix = StrUtil.getFilenameSuffix(filename);
            // 判断允许上传的图片格式
            if (!webConfig.getAllowImgSuffixList().contains(suffix))
                throw new BusinessException(CommonMsg.UNSUPPORTED_IMAGE_FORMAT);
            String randomFilename = StrUtil.getUUID() + suffix;
            String dateDir = StrUtil.formatDate("yyyy-MM");
            String imgDirPath = getProjectImgDirPath(imgDir, dateDir);
            File dir = new File(imgDirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File resourceImg = new File(dir, randomFilename);
            // 存储图片
            imgFile.transferTo(resourceImg);

            // 缩略图宽高
            ThumbnailSizeEnum tSize = ThumbnailSizeEnum.getByType(imgDir);
            w = w == null ? tSize.getWidth() : w;
            h = h == null ? tSize.getHeight() : h;

            // 生成缩略图
            String destPath = imgDirPath + File.separatorChar + randomFilename.replace(".", "_t_n.");
            ThumbnailUtil.createThumbnail(resourceImg, destPath, w, h);

            FileUploadVo fileUploadVo = new FileUploadVo();
            fileUploadVo.setFileUrl(imgDir + "/" + dateDir + "/" + randomFilename);
            return Result.success(fileUploadVo);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.FILE_UPLOAD_FAIL, e);
        }
    }

    /**
     * 读取图片接口
     *
     * @param response
     * @param imgDir
     * @param dateDir
     * @param filename
     * @throws Exception
     */
    @GetMapping("/read/{imgDir}/{dateDir}/{filename}")
    public void readImg(HttpServletResponse response,
                        @PathVariable String imgDir, @PathVariable String dateDir, @PathVariable String filename) throws Exception {
        String imgDirPath = getProjectImgDirPath(imgDir, dateDir);
        File file = new File(imgDirPath, filename);
        if (!file.exists())
            throw new BusinessException(CommonMsg.FILE_NOT_FOUND);
        response.setContentType("image/" + StrUtil.getFilenameSuffixWithoutDot(filename));
        response.setHeader("Cache-Control", "max-age=259200");  // 缓存3天
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        try (OutputStream os = response.getOutputStream();
             InputStream is = new FileInputStream(file)) {
            IOUtils.copy(is, aos);
            response.setHeader("Content-Length", String.valueOf(aos.size()));
            os.write(aos.toByteArray());
        }
    }

    private String getProjectImgDirPath(String imgDir, String dateDir) {
        StringBuffer sb = new StringBuffer();
        return sb.append(webConfig.getProjectFolder())
                .append(File.separator)
                .append(imgDir)
                .append(File.separator)
                .append(dateDir)
                .toString();
    }
}
