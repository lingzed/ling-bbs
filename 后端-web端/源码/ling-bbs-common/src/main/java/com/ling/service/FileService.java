package com.ling.service;

import com.ling.entity.dto.UploadFileProp;
import com.ling.entity.po.FileUseRecord;
import com.ling.entity.vo.UploadResultVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.List;

public interface FileService {
    /**
     * 处理图片上传
     *
     * @param imgFile
     * @param imgDir
     * @param w
     * @param h
     * @return
     */
    UploadResultVo processUploadImage(MultipartFile imgFile, String imgDir, Integer w, Integer h);

    /**
     * 处理文章图片上传
     *
     * @param imgFile
     * @param fileUseRecord
     * @return
     */
    UploadResultVo processArticleImageUpload(MultipartFile imgFile, FileUseRecord fileUseRecord) throws Exception;

    /**
     * 存储文件
     *
     * @param file     // 文件
     * @param rTypeDir // 资源类型目录
     * @return
     * @throws Exception
     */
    UploadFileProp saveFile(MultipartFile file, String rTypeDir) throws Exception;

    /**
     * 下载文件
     *
     * @param filepath
     * @param filename
     * @param contentType
     * @return
     */
    ResponseEntity<byte[]> downloadFile(String filepath, String filename, String contentType) throws Exception;

    /**
     * 获取服务器资源路径
     *
     * @param rTypeDir 资源类型目录
     * @param dateDir  日期目录
     * @return
     */
    String getSysResourceDirPath(String rTypeDir, String dateDir);

    /**
     * 变更文章使用的图片: 已使用
     *
     * @param uploaderId
     * @param articleId
     * @param imageId
     * @param status
     */
    void processArticleImageStatus(String uploaderId, String articleId, String imageId, Short status);

    /**
     * 删除未使用文件
     */
    void processDeleteUnusedFile();

    /**
     * 删除文件
     *
     * @param filepath
     */
    void deleteFile(String filepath);

}
