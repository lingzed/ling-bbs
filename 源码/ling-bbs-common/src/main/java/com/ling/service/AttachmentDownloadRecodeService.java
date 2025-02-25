package com.ling.service;

import com.ling.entity.dto.query.AttachmentDownloadRecodeQueryDto;
import com.ling.entity.po.AttachmentDownloadRecode;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface AttachmentDownloadRecodeService {
    /**
     * 条件查询
     *
     * @param
     */
    PageBean<AttachmentDownloadRecode> findByCondition(AttachmentDownloadRecodeQueryDto attachmentDownloadRecodeQueryDto);

    /**
     * 条件查询总数
     *
     * @param
     */
    Long findTotalByCondition(AttachmentDownloadRecodeQueryDto attachmentDownloadRecodeQueryDto);

    /**
     * 查询所有
     */
    List<AttachmentDownloadRecode> findAll();

    /**
     * 查询总数
     */
    Long findTotal();

    /**
     * 通过id查询
     *
     * @param
     */
    AttachmentDownloadRecode findById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    AttachmentDownloadRecode findById(Integer id);

    /**
     * 添加
     *
     * @param
     */
    void add(AttachmentDownloadRecode attachmentDownloadRecode);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<AttachmentDownloadRecode> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(AttachmentDownloadRecode attachmentDownloadRecode);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<AttachmentDownloadRecode> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);
}
