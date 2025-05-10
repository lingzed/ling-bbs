package com.ling.entity.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ling.annotation.Validation;

import java.util.Date;
import java.util.Objects;

/**
 * 基础查询Dto
 */
public class BaseQuery {
    @Validation(min = 1)
    @JsonIgnore
    private Integer page;           // 当前页
    @JsonIgnore
    private Integer pageSize;       // 当前页展示条目
    @JsonIgnore
    private Date startDate;         // 开始时间
    @JsonIgnore
    private Date endDate;           // 结束时间
    @JsonIgnore
    private String orderBy;         // 排序sql


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /*分页起始索引*/
    public Integer getIndex() {
        if (Objects.isNull(page) || Objects.isNull(pageSize)) {
            return null;
        }
        return (page - 1) * pageSize;
    }

    /*每页条目*/
    public Integer getSize() {
        return getPageSize();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
