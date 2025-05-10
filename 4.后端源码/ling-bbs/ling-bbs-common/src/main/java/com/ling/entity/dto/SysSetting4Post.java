package com.ling.entity.dto;

import com.ling.annotation.Validation;

/**
 * 系统设置-发帖设置
 */
public class SysSetting4Post {
    @Validation
    private Integer postPoints;     // 发帖积分
    @Validation
    private Integer postNum;        // 每天可发帖数量
    @Validation
    private Integer uploadImgNum;   // 每天可上传图片数量
    @Validation
    private Integer attachmentSize; // 附件大小


    public SysSetting4Post() {
    }

    public SysSetting4Post(Integer postPoints, Integer postNum, Integer uploadImgNum, Integer attachmentSize) {
        this.postPoints = postPoints;
        this.postNum = postNum;
        this.uploadImgNum = uploadImgNum;
        this.attachmentSize = attachmentSize;
    }

    /**
     * 获取
     *
     * @return postPoints
     */
    public Integer getPostPoints() {
        return postPoints;
    }

    /**
     * 设置
     *
     * @param postPoints
     */
    public void setPostPoints(Integer postPoints) {
        this.postPoints = postPoints;
    }

    /**
     * 获取
     *
     * @return postNum
     */
    public Integer getPostNum() {
        return postNum;
    }

    /**
     * 设置
     *
     * @param postNum
     */
    public void setPostNum(Integer postNum) {
        this.postNum = postNum;
    }

    /**
     * 获取
     *
     * @return uploadImgNum
     */
    public Integer getUploadImgNum() {
        return uploadImgNum;
    }

    /**
     * 设置
     *
     * @param uploadImgNum
     */
    public void setUploadImgNum(Integer uploadImgNum) {
        this.uploadImgNum = uploadImgNum;
    }

    /**
     * 获取
     *
     * @return attachmentSize
     */
    public Integer getAttachmentSize() {
        return attachmentSize;
    }

    /**
     * 设置
     *
     * @param attachmentSize
     */
    public void setAttachmentSize(Integer attachmentSize) {
        this.attachmentSize = attachmentSize;
    }

    public String toString() {
        return "SysSetting4Post{postPoints = " + postPoints + ", postNum = " + postNum + ", uploadImgNum = " + uploadImgNum + ", attachmentSize = " + attachmentSize + "}";
    }
}
