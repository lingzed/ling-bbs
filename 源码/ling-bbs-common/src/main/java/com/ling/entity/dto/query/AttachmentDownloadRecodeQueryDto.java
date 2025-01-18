package com.ling.entity.dto.query;

import java.util.Date;

/**
 * 下载记录查询DTO
 */
public class AttachmentDownloadRecodeQueryDto extends BaseQueryDto {
    private Integer downloadRecodeId;   // 下载记录id
    private String articleId;   // 文章id
    private String downloaderId;    // 下载人id
    private Date downloadTime;  // 下载时间
}
