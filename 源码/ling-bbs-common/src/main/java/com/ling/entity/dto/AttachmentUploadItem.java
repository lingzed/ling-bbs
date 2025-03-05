package com.ling.entity.dto;

import com.ling.annotation.Validation;
import com.ling.enums.MIMETypeEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * 附件上传项
 */
public class AttachmentUploadItem {
    private MultipartFile attachment;   // 附件
    private Integer points;             // 附件积分
    private MIMETypeEnum mimeTypeEnum;  // mime类型

    public AttachmentUploadItem() {
    }

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public MIMETypeEnum getMimeTypeEnum() {
        return mimeTypeEnum;
    }

    public void setMimeTypeEnum(MIMETypeEnum mimeTypeEnum) {
        this.mimeTypeEnum = mimeTypeEnum;
    }
}
