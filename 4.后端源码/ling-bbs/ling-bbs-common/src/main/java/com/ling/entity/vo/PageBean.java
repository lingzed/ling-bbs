package com.ling.entity.vo;

import java.util.List;

public class PageBean<T> {
    private Long total;         // 总记录数
    private Integer page;       // 当前页码
    private Integer pageSize;   // 每页显示条数
    private Integer pageTotal;  // 总页数
    private List<T> rows;       // 当前页数据

    public PageBean(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public static <T> PageBean<T> of(Long total, List<T> rows) {
        return new PageBean<>(total, rows);
    }

    public static <T> PageBean<T> of(Long total, Integer page, Integer pageSize, Integer pageTotal,
                                     List<T> rows) {
        return new PageBean<>(total, page, pageSize, pageTotal, rows);
    }

    public static <T> PageBean<T> of(Long total, Integer page, Integer pageSize, List<T> rows) {
        total = total < 0 ? 0 : total;
        int pages = pageSize == null ? 0 : (int) Math.ceil((double) total / pageSize);
        return PageBean.of(total, page, pageSize, pages, rows);
    }

    public PageBean(Long total, Integer page, Integer pageSize, Integer pageTotal, List<T> rows) {
        this.total = total < 0 ? 0 : total;
        this.page = page;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.rows = rows;
    }

    public PageBean() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
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

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
