package com.ling.entity.vo;

/**
 * 附件信息VO
 */
public class AttachmentVo {
    private String fileId;  // 文件ID
    private String filename; // 文件名
    private Long filesize; // 文件大小
    private Integer fileType; // 文件类型
    private Integer downloadCount; // 下载次数
    private Integer downloadPoints; // 下载所需积分

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getDownloadPoints() {
        return downloadPoints;
    }

    public void setDownloadPoints(Integer downloadPoints) {
        this.downloadPoints = downloadPoints;
    }
}
