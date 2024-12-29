package com.ling.entity.dto;

import java.util.Date;

/**
 * 基础查询
 */
public class BaseQueryParamDto {
    private Integer page;           // 当前页
    private Integer pageSize;       // 当前页展示条目
    private Date startDate;         // 开始时间
    private Date endDate;           // 结束时间

    public BaseQueryParamDto() {
    }

    public BaseQueryParamDto(Integer page, Integer pageSize, Date startDate, Date endDate) {
        this.page = page;
        this.pageSize = pageSize;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 获取
     *
     * @return page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * 设置
     *
     * @param page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 获取
     *
     * @return pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置
     *
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取
     *
     * @return startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置
     *
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取
     *
     * @return endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置
     *
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String toString() {
        return "QueryParam{page = " + page + ", pageSize = " + pageSize + ", startDate = " + startDate + ", endDate = " + endDate + "}";
    }
}
