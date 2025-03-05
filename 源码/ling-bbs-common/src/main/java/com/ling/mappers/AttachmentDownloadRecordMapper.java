package com.ling.mappers;

import com.ling.entity.dto.query.AttachmentDownloadRecordQueryDto;
import com.ling.entity.po.AttachmentDownloadRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachmentDownloadRecordMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<AttachmentDownloadRecord> selectByCondition(AttachmentDownloadRecordQueryDto attachmentDownloadRecordQueryDto);

    /**
     * 条件查询总数量
     *
     * @param
     */
    Long selectCountByCondition(AttachmentDownloadRecordQueryDto attachmentDownloadRecordQueryDto);

    /**
     * 查询所有
     */
    List<AttachmentDownloadRecord> selectAll();

    /**
     * 查询总数量
     */
    Long selectCount();

    /**
     * 通过id查询
     *
     * @param
     */
    AttachmentDownloadRecord selectById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    AttachmentDownloadRecord selectById(Integer id);

    /**
     * 通过 文章id & 下载人id 查询
     * @param articleId
     * @param downloaderId
     * @return
     */
    AttachmentDownloadRecord selectByArticleIdAndDownloaderId(String articleId, String downloaderId);

    /**
     * 添加
     *
     * @param
     */
    void insert(AttachmentDownloadRecord attachmentDownloadRecord);

    /**
     * 批量添加
     *
     * @param
     */
    void batchInsert(List<AttachmentDownloadRecord> list);

    /**
     * 编辑
     *
     * @param
     */
    void update(AttachmentDownloadRecord attachmentDownloadRecord);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchUpdate(List<AttachmentDownloadRecord> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);
}
