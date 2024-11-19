package com.ling.entity.param;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class BusinessParam<T> {
    @JsonIgnore
    private Integer page;       // 分页参数-当前页
    @JsonIgnore
    private Integer pageSize;   // 分页参数-每页显示条数
    @JsonIgnore
    private Date startDate;     // 查询开始时间
    @JsonIgnore
    private Date endDate;       // 查询结束时间
    @JsonIgnore
    private String orderBy;     // 排序字段
    //    @JsonIgnore
    private List<T> list;      // 实体列表

    public BusinessParam() {
    }

    public BusinessParam(Integer page, Integer pageSize, Date startDate, Date endDate, String orderBy, List<T> list) {
        this.page = page;
        this.pageSize = pageSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderBy = orderBy;
        this.list = list;
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

    /**
     * 获取
     *
     * @return orderBy
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置
     *
     * @param orderBy
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 获取
     *
     * @return list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置
     *
     * @param list
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    public String toString() {
        return "CommonParam{page = " + page + ", pageSize = " + pageSize + ", startDate = " + startDate + ", endDate = " + endDate + ", orderBy = " + orderBy + ", list = " + list + "}";
    }
}
