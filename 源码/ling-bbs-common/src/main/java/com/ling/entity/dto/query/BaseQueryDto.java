package com.ling.entity.dto.query;

import java.util.Date;

/**
 * 基础查询Dto
 */
public class BaseQueryDto {
    private Integer page;           // 当前页
    private Integer pageSize;       // 当前页展示条目
    private Date startDate;         // 开始时间
    private Date endDate;           // 结束时间

    public Integer getIndex() {
        return (page - 1) * pageSize;
    }

    public Integer getSize() {
        return getPageSize();
    }

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
}
