package com.ling.mappers;

import com.ling.entity.dto.query.AttachmentQueryDto;
import com.ling.entity.po.Attachment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachmentMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<Attachment> selectByCondition(AttachmentQueryDto attachmentQueryDto);

    /**
     * 条件查询总数量
     *
     * @param
     */
    Long selectCountByCondition(AttachmentQueryDto attachmentQueryDto);

    /**
     * 查询所有
     */
    List<Attachment> selectAll();

    /**
     * 查询总数量
     */
    Long selectCount();

    /**
     * 通过id查询
     *
     * @param
     */
    Attachment selectById(String id);

    /**
     * 通过文章id和作者查询
     *
     * @param articleId
     * @param userId
     * @return
     */
    List<Attachment> selectByArticleIdAndUserId(String articleId, String userId);

    /**
     * 添加
     *
     * @param
     */
    void insert(Attachment attachment);

    /**
     * 批量添加
     *
     * @param
     */
    void batchInsert(List<Attachment> list);

    /**
     * 编辑
     *
     * @param
     */
    void update(Attachment attachment);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchUpdate(List<Attachment> list);

    /**
     * 更新附件下载量
     *
     * @param articleId
     * @param userId
     */
    void updateAttachmentDownloadCount(String articleId, String userId);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<String> list);
}
