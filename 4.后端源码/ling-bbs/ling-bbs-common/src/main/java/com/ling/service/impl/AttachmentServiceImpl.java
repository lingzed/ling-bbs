package com.ling.service.impl;

import com.ling.entity.dto.query.AttachmentQuery;
import com.ling.entity.po.Attachment;
import com.ling.entity.vo.AttachmentVo;
import com.ling.entity.vo.PageBean;
import com.ling.mappers.AttachmentMapper;
import com.ling.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private static final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Resource
    private AttachmentMapper attachmentMapper;

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public PageBean<Attachment> findByCondition(AttachmentQuery attachmentQuery) {
        List<Attachment> list = attachmentMapper.selectByCondition(attachmentQuery);
        Long total = findTotalByCondition(attachmentQuery);
        return PageBean.of(total, attachmentQuery.getPage(), attachmentQuery.getPageSize(), list);
    }

    /**
     * 条件查询总数
     *
     * @param
     */
    @Override
    public Long findTotalByCondition(AttachmentQuery attachmentQuery) {
        return attachmentMapper.selectCountByCondition(attachmentQuery);
    }

    /**
     * 查询所有
     */
    @Override
    public List<Attachment> findAll() {
        return attachmentMapper.selectAll();
    }

    /**
     * 查询总数
     */
    @Override
    public Long findTotal() {
        return attachmentMapper.selectCount();
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public Attachment findById(String id) {
        return attachmentMapper.selectById(id);
    }

    /**
     * 通过文章id和作者查询
     *
     * @param articleId
     * @param userId
     * @return
     */
    @Override
    public List<AttachmentVo> findVoByArticleIdAndUserId(String articleId, String userId) {
        List<Attachment> attachments = attachmentMapper.selectByArticleIdAndUserId(articleId, userId);
        log.info("{}", attachments);
        List<AttachmentVo> attachmentVos = attachments.stream().map(e -> {
            AttachmentVo attachmentVo = new AttachmentVo();
            BeanUtils.copyProperties(e, attachmentVo);
            return attachmentVo;
        }).collect(Collectors.toList());
        return attachmentVos;
    }

    @Override
    public List<Attachment> findByArticleId(String articleId) {
        return attachmentMapper.selectByArticleId(articleId);
    }

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(Attachment attachment) {
        attachmentMapper.insert(attachment);
    }

//    /**
//     * 添加
//     *
//     * @param
//     */
//    @Override
//    public void add(AttachmentDto attachmentDto) {
//        Attachment attachment = new Attachment();
//        Date date = new Date();
//        attachment.setCreateTime(date);
//        attachment.setUpdateTime(date);
//        BeanUtils.copyProperties(attachmentDto, attachment);
//        attachmentMapper.insert(attachment);
//    }

    /**
     * 批量添加
     *
     * @param
     */
    @Override
    public void batchAdd(List<Attachment> list) {
        attachmentMapper.batchInsert(list);
    }

//    /**
//     * 批量添加
//     *
//     * @param
//     */
//    @Override
//    public void batchAdd(List<AttachmentDto> list) {
//        Date date = new Date();
//        List<Attachment> attachments = list.stream().map(e -> {
//            Attachment attachment = new Attachment();
//            attachment.setCreateTime(date);
//            attachment.setUpdateTime(date);
//            BeanUtils.copyProperties(e, attachment);
//            return attachment;
//        }).collect(Collectors.toList());
//        attachmentMapper.batchInsert(attachments);
//    }

    /**
     * 编辑
     *
     * @param
     */
    @Override
    public void edit(Attachment attachment) {
        attachmentMapper.update(attachment);
    }

//    /**
//     * 编辑
//     *
//     * @param
//     */
//    @Override
//    public void edit(AttachmentDto attachmentDto) {
//        Attachment attachment = new Attachment();
//        Date date = new Date();
//        attachment.setUpdateTime(date);
//        BeanUtils.copyProperties(attachmentDto, attachment);
//        attachmentMapper.update(attachment);
//    }

    /**
     * 批量编辑
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<Attachment> list) {
        attachmentMapper.batchUpdate(list);
    }

//    /**
//     * 批量编辑
//     *
//     * @param
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void batchEdit(List<AttachmentDto> list) {
//        Date date = new Date();
//        List<Attachment> attachments = list.stream().map(e -> {
//            Attachment attachment = new Attachment();
//            attachment.setUpdateTime(date);
//            BeanUtils.copyProperties(e, attachment);
//            return attachment;
//        }).collect(Collectors.toList());
//        attachmentMapper.batchUpdate(attachments);
//    }

    /**
     * 删除
     *
     * @param
     */
    @Override
    public void delete(List<String> list) {
        attachmentMapper.delete(list);
    }
}
