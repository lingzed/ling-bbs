package com.ling.entity.po;

/**
 * 文件使用记录实体
 */
public class FileUseRecord {
    private String recordId;    // 记录id
    private String targetId;    // 目标id
    private String uploaderId;  // 上传人id
    private String filename;    // 文件名
    private String filepath;    // 文件路径
    private Short status;   // 0: 未使用, 1: 已使用

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
