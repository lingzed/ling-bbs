package com.ling.mappers;

import com.ling.entity.dto.query.AttachmentDownloadRecodeQueryDto;
import com.ling.entity.po.AttachmentDownloadRecode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachmentDownloadRecodeMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<AttachmentDownloadRecode> selectByCondition(AttachmentDownloadRecodeQueryDto attachmentDownloadRecodeQueryDto);

    /**
     * 条件查询总数量
     *
     * @param
     */
    Long selectCountByCondition(AttachmentDownloadRecodeQueryDto attachmentDownloadRecodeQueryDto);

    /**
     * 查询所有
     */
    List<AttachmentDownloadRecode> selectAll();

    /**
     * 查询总数量
     */
    Long selectCount();

    /**
     * 通过id查询
     *
     * @param
     */
    AttachmentDownloadRecode selectById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    AttachmentDownloadRecode selectById(Integer id);

    /**
     * 通过 文章id & 下载人id 查询
     * @param articleId
     * @param downloaderId
     * @return
     */
    AttachmentDownloadRecode selectByArticleIdAndDownloaderId(String articleId, String downloaderId);

    /**
     * 添加
     *
     * @param
     */
    void insert(AttachmentDownloadRecode attachmentDownloadRecode);

    /**
     * 批量添加
     *
     * @param
     */
    void batchInsert(List<AttachmentDownloadRecode> list);

    /**
     * 编辑
     *
     * @param
     */
    void update(AttachmentDownloadRecode attachmentDownloadRecode);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchUpdate(List<AttachmentDownloadRecode> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);
}
