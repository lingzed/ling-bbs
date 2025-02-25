package com.ling.enums;

import com.alibaba.fastjson.JSON;

import java.util.Objects;

public enum MIMETypeEnum {
    TXT(0, "text/plain", ".txt", "文本文件"),
    PDF(1, "application/pdf", ".pdf", "pdf文件"),
    EXCEL_XLSX(2, "application/vnd.ms-excel", ".xlsx", "excel文件"),
    EXCEL_XLS(3, "application/vnd.ms-excel", ".xls", "excel文件"),
    ZIP(4, "application/zip", ".zip", "zip文件"),
    JSON(5, "application/json", ".json", "json文件"),
    JPEG(6, "image/jpeg", ".jpeg", "jpeg文件"),
    PNG(7, "image/png", ".png", "png文件");
    private Integer type;
    private String contentType;
    private String desc;
    private String suffix;

    MIMETypeEnum(Integer type, String contentType, String suffix, String desc) {
        this.type = type;
        this.contentType = contentType;
        this.desc = desc;
        this.suffix = suffix;
    }

    public static String getContentType(Integer type) {
        if (Objects.isNull(type)) return null;
        MIMETypeEnum[] mimeTypeEnums = MIMETypeEnum.values();
        for (MIMETypeEnum mimeTypeEnum : mimeTypeEnums) {
            if (Objects.equals(mimeTypeEnum.getType(), type))
                return mimeTypeEnum.getContentType();
        }
        return null;
    }

    public static MIMETypeEnum getMIMEBySuffix(String suffix) {
        if (Objects.isNull(suffix)) return null;
        MIMETypeEnum[] values = MIMETypeEnum.values();
        for (MIMETypeEnum mimeTypeEnum : values) {
            if (Objects.equals(mimeTypeEnum.getSuffix(), suffix))
                return mimeTypeEnum;
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getContentType() {
        return contentType;
    }

    public String getDesc() {
        return desc;
    }

    public String getSuffix() {
        return suffix;
    }
}
