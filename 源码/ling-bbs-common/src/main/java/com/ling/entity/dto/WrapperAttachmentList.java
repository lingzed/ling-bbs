package com.ling.entity.dto;

import java.util.List;

/**
 * 包装附件列表
 */
public class WrapperAttachmentList {
    private List<AttachmentUploadItem> attachments;

    public List<AttachmentUploadItem> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentUploadItem> attachments) {
        this.attachments = attachments;
    }
}
