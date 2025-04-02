package com.ling.entity.vo;

/**
 * 文件上传返回结果vo
 */
public class UploadResultVo {
    private String fileId;      // 文件id
    private String fileURL;    // 上传完成返回的文件路径

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }
}
