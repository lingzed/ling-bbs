package com.ling.enums;

/**
 * 文章排序枚举类
 */
public enum ArticleOrderEnum {
    HOT(0, "top_type desc, comment_count desc, like_count desc, read_count desc", "热榜"),
    POST_TIME(1, "create_time", "发布时间"),
    NEW(2, "create_time desc", "最新");

    private Integer orderType;
    private String orderBySql;
    private String desc;

    ArticleOrderEnum(Integer orderType, String orderBySql, String desc) {
        this.orderType = orderType;
        this.orderBySql = orderBySql;
        this.desc = desc;
    }

    public static ArticleOrderEnum getArticleOrderEnum(Integer orderType) {
        for (ArticleOrderEnum value : ArticleOrderEnum.values()) {
            if (orderType.equals(value.getOrderType())) {
                return value;
            }
        }
        return null;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public String getOrderBySql() {
        return orderBySql;
    }

    public String getDesc() {
        return desc;
    }
}
