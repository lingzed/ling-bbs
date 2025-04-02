package com.ling.service.impl;

import com.ling.commons.CommonMsg;
import com.ling.config.WebConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.UploadFileProp;
import com.ling.entity.po.Article;
import com.ling.entity.po.Attachment;
import com.ling.entity.po.FileUseRecord;
import com.ling.entity.vo.UploadResultVo;
import com.ling.enums.MIMETypeEnum;
import com.ling.enums.ResponseCodeEnum;
import com.ling.enums.ThumbnailSizeEnum;
import com.ling.enums.UseStatusEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.ArticleMapper;
import com.ling.mappers.CommentMapper;
import com.ling.mappers.UserInfoMapper;
import com.ling.service.*;
import com.ling.utils.StrUtil;
import com.ling.utils.ThumbnailUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private WebConfig webConfig;
    @Resource
    private FileUseRecordService fileUseRecordService;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UploadResultVo processUploadImage(MultipartFile imgFile, String imgDir, Integer w, Integer h) {
        try {
            UploadFileProp uploadFileProp = saveFile(imgFile, imgDir);
            String filepath = uploadFileProp.getFilepath();

            // 生成缩略图
            ThumbnailSizeEnum tSize = ThumbnailSizeEnum.getByType(imgDir);
            w = w == null ? tSize.getWidth() : w;
            h = h == null ? tSize.getHeight() : h;
            String destPath = filepath.replace(".", "_t_n.");
            ThumbnailUtil.createThumbnail(filepath, destPath, w, h);

            UploadResultVo uploadResultVo = new UploadResultVo();
            BeanUtils.copyProperties(uploadFileProp, uploadResultVo);

            return uploadResultVo;
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.FILE_UPLOAD_FAIL, e);
        }
    }

    @Override
    public UploadResultVo processArticleImageUpload(MultipartFile imgFile, FileUseRecord fileUseRecord) throws Exception {
        String recordId = fileUseRecord.getRecordId();
        if (!Objects.isNull(recordId)) {
            List<FileUseRecord> rList = fileUseRecordService
                    .findByIdAndUploaderId(recordId, fileUseRecord.getUploaderId());
            // 校验image-id的有效性
            if (Objects.isNull(rList) || rList.isEmpty())
                throw new BusinessException(CommonMsg.INVALID_IMAGE_ID);
        }

        // 上传图片
        UploadFileProp uploadFileProp = saveFile(imgFile, Constant.RESOURCE_DIR_ARTICLE);

        recordId = Objects.isNull(recordId) ? uploadFileProp.getFileId() : recordId;
        fileUseRecord.setRecordId(recordId);
        fileUseRecord.setFilename(uploadFileProp.getOriginalName());
        fileUseRecord.setFilepath(uploadFileProp.getFilepath());
        fileUseRecord.setStatus(UseStatusEnum.NOT_USE.getStatus().shortValue());
        fileUseRecordService.add(fileUseRecord);

        UploadResultVo uploadResultVo = new UploadResultVo();
        uploadResultVo.setFileId(recordId);
        uploadResultVo.setFileURL(uploadFileProp.getFileURL());
        return uploadResultVo;
    }

    /**
     * 存储文件
     *
     * @param file     // 文件
     * @param rTypeDir // 资源类型目录
     * @return
     * @throws Exception
     */
    @Override
    public UploadFileProp saveFile(MultipartFile file, String rTypeDir) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String fileId = StrUtil.getUUID();
        String filename = fileId + StrUtil.getFilenameSuffix(originalFilename);
        String dateDir = StrUtil.formatDate("yyyy-MM");
        File pDir = new File(getSysResourceDirPath(rTypeDir, dateDir));
        if (!pDir.exists()) {
            pDir.mkdirs();
        }
        File rFile = new File(pDir, filename);
        // 存储图片
        file.transferTo(rFile);

        UploadFileProp uploadFileProp = new UploadFileProp();
        uploadFileProp.setFileId(fileId);
        uploadFileProp.setOriginalName(originalFilename);
        uploadFileProp.setFilepath(rFile.getAbsolutePath());
        uploadFileProp.setFileURL(rTypeDir + "/" + dateDir + "/" + filename);
        uploadFileProp.setFilesize(file.getSize());
        return uploadFileProp;
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(String filepath, String filename, String contentType) throws Exception {
        File file = new File(filepath);
        if (!file.exists() || file.isDirectory())
            throw new BusinessException(CommonMsg.FILE_NOT_FOUND);
        InputStream is = new FileInputStream(file);
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        IOUtils.copy(is, aos);      // 输入流中的数据复制到输出流
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", contentType);
        headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename));
        headers.add("Content-Length", String.valueOf(aos.size()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(aos.toByteArray());   // 响应体填充字节数据
    }

    @Override
    public ResponseEntity<byte[]> downloadAsZip(List<? extends FileItem> fileItems) throws Exception {
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(aos)) {
            for (FileItem fileItem : fileItems) {
                File file = new File(fileItem.getFilepath());
                if (!file.exists() || file.isDirectory())
                    throw new BusinessException(CommonMsg.FILE_NOT_FOUND);
                try (InputStream is = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(fileItem.getFilename());
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

    public String getSysResourceDirPath(String rTypeDir, String dateDir) {
        StringBuffer sb = new StringBuffer();
        return sb.append(webConfig.getProjectFolder())
                .append(File.separator)
                .append(rTypeDir)
                .append(File.separator)
                .append(dateDir)
                .toString();
    }

    @Override
    public void processArticleImageStatus(String uploaderId, String articleId, String imageId, Short status) {
        Article article = articleMapper.selectById(articleId);
        if (Objects.isNull(article))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        editFileStatus(uploaderId, articleId, imageId, status);
    }

    /**
     * 更新文件: 已使用
     *
     * @param uploaderId
     * @param targetId
     * @param imageId
     * @param status
     */
    private void editFileStatus(String uploaderId, String targetId, String imageId, Short status) {
        // 校验image-id的有效性
        List<FileUseRecord> fileUseRecords = fileUseRecordService.findByIdAndUploaderId(imageId, uploaderId);
        if (Objects.isNull(fileUseRecords) || fileUseRecords.isEmpty())
            throw new BusinessException(CommonMsg.INVALID_IMAGE_ID);
        FileUseRecord fileUseRecord = new FileUseRecord();
        fileUseRecord.setRecordId(imageId);
        fileUseRecord.setStatus(status);
        fileUseRecord.setTargetId(targetId);

        fileUseRecordService.edit(fileUseRecord);
    }

    @Override
    public void processDeleteUnusedFile() {
        List<FileUseRecord> unuseds = fileUseRecordService.findUnused();
        unuseds.forEach(e -> deleteFile(e.getFilepath()));
        fileUseRecordService.deleteUnused();
    }

    @Override
    public void deleteFile(String filepath) throws BusinessException {
        try {
            File file = new File(filepath);
            if (!file.exists() || file.isDirectory()) return;
            file.delete();
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.FILE_DELETE_FAIL, e);
        }
    }
}
