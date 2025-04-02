package com.ling.entity.dto;

/**
 * 上传文件属性传输对象
 */
public class UploadFileProp {
    private String fileId;      // 文件id
    private String originalName;    // 原始文件名
    private String filepath;        // 文件路径
    private String fileURL;         // 文件URL路径
    private Long filesize;      // 大小

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
}
