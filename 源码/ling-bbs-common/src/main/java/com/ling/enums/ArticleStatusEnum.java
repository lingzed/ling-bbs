package com.ling.enums;

/**
 * 文章状态枚举类
 */
public enum ArticleStatusEnum {
    DELETED(0, "已删除"),
    PENDING(1, "待审核"),

    AUDITED(2, "已审核");

    private Integer status;
    private String desc;

    ArticleStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static ArticleStatusEnum getArticleStatic(Integer status) {
        for (ArticleStatusEnum e : ArticleStatusEnum.values()) {
            if (e.getStatus() == status) {
                return e;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
