package com.ling.service;

import com.ling.entity.dto.query.AttachmentQueryDto;
import com.ling.entity.po.Attachment;
import com.ling.entity.vo.AttachmentVo;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface AttachmentService {
    /**
     * 条件查询
     *
     * @param
     */
    PageBean<Attachment> findByCondition(AttachmentQueryDto attachmentQueryDto);

    /**
     * 条件查询总数
     *
     * @param
     */
    Long findTotalByCondition(AttachmentQueryDto attachmentQueryDto);

    /**
     * 查询所有
     */
    List<Attachment> findAll();

    /**
     * 查询总数
     */
    Long findTotal();

    /**
     * 通过id查询
     *
     * @param
     */
    Attachment findById(String id);

    /**
     * 通过文章id和作者查询
     *
     * @param articleId
     * @param userId
     * @return
     */
    List<AttachmentVo> findByArticleIdAndUserId(String articleId, String userId);

    /**
     * 添加
     *
     * @param
     */
    void add(Attachment attachment);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<Attachment> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(Attachment attachment);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<Attachment> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<String> list);
}
