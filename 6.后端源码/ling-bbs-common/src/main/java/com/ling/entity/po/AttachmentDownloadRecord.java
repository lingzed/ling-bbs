package com.ling.entity.po;

import java.util.Date;

/**
 * 下载记录实体
 */
public class AttachmentDownloadRecord {
    private Integer downloadRecordId;   // 下载记录id
    private String articleId;   // 文章id
    private String downloaderId;    // 下载人id
    private Date downloadTime;  // 下载时间

    public Integer getDownloadRecordId() {
        return downloadRecordId;
    }

    public void setDownloadRecordId(Integer downloadRecordId) {
        this.downloadRecordId = downloadRecordId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getDownloaderId() {
        return downloaderId;
    }

    public void setDownloaderId(String downloaderId) {
        this.downloaderId = downloaderId;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }
}
