package com.ling.enums;

/**
 * 每页条目枚举
 */
public enum PageSizeEnum {
    SIZE_5(5, "每页5条目"),
    SIZE_10(10, "每页10条目"),
    SIZE_20(20, "每页20条目"),
    SIZE_50(50, "每页50条目"),
    SIZE_100(100, "每页100条目");
    private Integer pageSize;
    private String desc;

    PageSizeEnum(Integer pageSize, String desc) {
        this.pageSize = pageSize;
        this.desc = desc;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getDesc() {
        return desc;
    }
}
