package com.ling.enums;

import java.util.Objects;

/**
 * 缩略图尺寸
 */
public enum ThumbnailSizeEnum {
    COMMENT_IMAGE_SIZE("comment", 200, 200, "评论缩略图默认尺寸"),
    AVATAR_SIZE("avatar", 120, 120, "头像缩略图默认尺寸"),
    COVER_SIZE("cover", 412, 232, "封面缩略图默认尺寸");

    private String type;
    private Integer width;
    private Integer height;
    private String desc;

    ThumbnailSizeEnum(String type, Integer width, Integer height, String desc) {
        this.type = type;
        this.width = width;
        this.height = height;
        this.desc = desc;
    }

    public static ThumbnailSizeEnum getByType(String type) {
        ThumbnailSizeEnum[] values = ThumbnailSizeEnum.values();
        for (ThumbnailSizeEnum value : values) {
            if (Objects.equals(type, value.getType()))
                return value;
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getDesc() {
        return desc;
    }
}
