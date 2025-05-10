package com.ling.entity.dto;

import com.ling.annotation.Validation;

/**
 * 系统设置-点赞设置
 */
public class SysSetting4Like {
    @Validation
    private Integer likeNum;    // 每天可点赞数量


    public SysSetting4Like() {
    }

    public SysSetting4Like(Integer likeNum) {
        this.likeNum = likeNum;
    }

    /**
     * 获取
     *
     * @return likeNum
     */
    public Integer getLikeNum() {
        return likeNum;
    }

    /**
     * 设置
     *
     * @param likeNum
     */
    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String toString() {
        return "SysSetting4Like{likeNum = " + likeNum + "}";
    }
}
