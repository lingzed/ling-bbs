package com.ling.service;

import com.ling.entity.dto.query.AttachmentDownloadRecordQueryDto;
import com.ling.entity.po.AttachmentDownloadRecord;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface AttachmentDownloadRecordService {
    /**
     * 条件查询
     *
     * @param
     */
    PageBean<AttachmentDownloadRecord> findByCondition(AttachmentDownloadRecordQueryDto attachmentDownloadRecordQueryDto);

    /**
     * 条件查询总数
     *
     * @param
     */
    Long findTotalByCondition(AttachmentDownloadRecordQueryDto attachmentDownloadRecordQueryDto);

    /**
     * 查询所有
     */
    List<AttachmentDownloadRecord> findAll();

    /**
     * 查询总数
     */
    Long findTotal();

    /**
     * 通过id查询
     *
     * @param
     */
    AttachmentDownloadRecord findById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    AttachmentDownloadRecord findById(Integer id);

    /**
     * 添加
     *
     * @param
     */
    void add(AttachmentDownloadRecord attachmentDownloadRecord);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<AttachmentDownloadRecord> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(AttachmentDownloadRecord attachmentDownloadRecord);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<AttachmentDownloadRecord> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);
}
