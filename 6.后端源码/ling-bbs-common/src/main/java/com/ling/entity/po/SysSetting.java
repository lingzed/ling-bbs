package com.ling.entity.po;


import com.ling.entity.dto.query.BaseQuery;

import java.util.Date;

/**
 * 系统设置
 */
public class SysSetting extends BaseQuery {
    private String code;
    private String jsonContent;
    private Date createTime;
    private Date updateTime;

    public SysSetting() {
    }

    public SysSetting(String code, String jsonContent) {
        this.code = code;
        this.jsonContent = jsonContent;
    }

    public SysSetting(String code, String jsonContent, Date createTime, Date updateTime) {
        this.code = code;
        this.jsonContent = jsonContent;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    /**
     * 获取
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取
     *
     * @return jsonContent
     */
    public String getJsonContent() {
        return jsonContent;
    }

    /**
     * 设置
     *
     * @param jsonContent
     */
    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public String toString() {
        return "SysSetting{code = " + code + ", jsonContent = " + jsonContent + "}";
    }

    /**
     * 获取
     *
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     *
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
