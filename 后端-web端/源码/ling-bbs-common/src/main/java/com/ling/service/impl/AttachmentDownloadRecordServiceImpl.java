package com.ling.service.impl;

import com.ling.entity.dto.query.AttachmentDownloadRecordQuery;
import com.ling.entity.po.AttachmentDownloadRecord;
import com.ling.entity.vo.PageBean;
import com.ling.mappers.AttachmentDownloadRecordMapper;
import com.ling.service.AttachmentDownloadRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AttachmentDownloadRecordServiceImpl implements AttachmentDownloadRecordService {
    private Logger log = LoggerFactory.getLogger(AttachmentDownloadRecordServiceImpl.class);

    @Resource
    private AttachmentDownloadRecordMapper attachmentDownloadRecordMapper;

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public PageBean<AttachmentDownloadRecord> findByCondition(AttachmentDownloadRecordQuery attachmentDownloadRecordQuery) {
        List<AttachmentDownloadRecord> list = attachmentDownloadRecordMapper.selectByCondition(attachmentDownloadRecordQuery);
        Long total = findTotalByCondition(attachmentDownloadRecordQuery);
        return PageBean.of(total, attachmentDownloadRecordQuery.getPage(), attachmentDownloadRecordQuery.getPageSize(), list);
    }

    /**
     * 条件查询总数
     *
     * @param
     */
    @Override
    public Long findTotalByCondition(AttachmentDownloadRecordQuery attachmentDownloadRecordQuery) {
        return attachmentDownloadRecordMapper.selectCountByCondition(attachmentDownloadRecordQuery);
    }

    /**
     * 查询所有
     */
    @Override
    public List<AttachmentDownloadRecord> findAll() {
        return attachmentDownloadRecordMapper.selectAll();
    }

    /**
     * 查询总数
     */
    @Override
    public Long findTotal() {
        return attachmentDownloadRecordMapper.selectCount();
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public AttachmentDownloadRecord findById(String id) {
        return attachmentDownloadRecordMapper.selectById(id);
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public AttachmentDownloadRecord findById(Integer id) {
        return attachmentDownloadRecordMapper.selectById(id);
    }

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(AttachmentDownloadRecord attachmentDownloadRecord) {
        Date date = new Date();
        attachmentDownloadRecord.setDownloadTime(date);
        attachmentDownloadRecordMapper.insert(attachmentDownloadRecord);
    }

//    /**
//     * 添加
//     *
//     * @param
//     */
//    @Override
//    public void add(AttachmentDownloadRecordDto attachmentDownloadRecordDto) {
//        AttachmentDownloadRecord attachmentDownloadRecord = new AttachmentDownloadRecord();
//        Date date = new Date();
//        attachmentDownloadRecord.setCreateTime(date);
//        attachmentDownloadRecord.setUpdateTime(date);
//        BeanUtils.copyProperties(attachmentDownloadRecordDto, attachmentDownloadRecord);
//        attachmentDownloadRecordMapper.insert(attachmentDownloadRecord);
//    }

    /**
     * 批量添加
     *
     * @param
     */
    @Override
    public void batchAdd(List<AttachmentDownloadRecord> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setDownloadTime(date);
        });
        attachmentDownloadRecordMapper.batchInsert(list);
    }

//    /**
//     * 批量添加
//     *
//     * @param
//     */
//    @Override
//    public void batchAdd(List<AttachmentDownloadRecordDto> list) {
//        Date date = new Date();
//        List<AttachmentDownloadRecord> attachmentDownloadRecords = list.stream().map(e -> {
//            AttachmentDownloadRecord attachmentDownloadRecord = new AttachmentDownloadRecord();
//            attachmentDownloadRecord.setCreateTime(date);
//            attachmentDownloadRecord.setUpdateTime(date);
//            BeanUtils.copyProperties(e, attachmentDownloadRecord);
//            return attachmentDownloadRecord;
//        }).collect(Collectors.toList());
//        attachmentDownloadRecordMapper.batchInsert(attachmentDownloadRecords);
//    }

    /**
     * 编辑
     *
     * @param
     */
    @Override
    public void edit(AttachmentDownloadRecord attachmentDownloadRecord) {
        attachmentDownloadRecordMapper.update(attachmentDownloadRecord);
    }

//    /**
//     * 编辑
//     *
//     * @param
//     */
//    @Override
//    public void edit(AttachmentDownloadRecordDto attachmentDownloadRecordDto) {
//        AttachmentDownloadRecord attachmentDownloadRecord = new AttachmentDownloadRecord();
//        Date date = new Date();
//        attachmentDownloadRecord.setUpdateTime(date);
//        BeanUtils.copyProperties(attachmentDownloadRecordDto, attachmentDownloadRecord);
//        attachmentDownloadRecordMapper.update(attachmentDownloadRecord);
//    }

    /**
     * 批量编辑
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<AttachmentDownloadRecord> list) {
        attachmentDownloadRecordMapper.batchUpdate(list);
    }

//    /**
//     * 批量编辑
//     *
//     * @param
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void batchEdit(List<AttachmentDownloadRecordDto> list) {
//        Date date = new Date();
//        List<AttachmentDownloadRecord> attachmentDownloadRecords = list.stream().map(e -> {
//            AttachmentDownloadRecord attachmentDownloadRecord = new AttachmentDownloadRecord();
//            attachmentDownloadRecord.setUpdateTime(date);
//            BeanUtils.copyProperties(e, attachmentDownloadRecord);
//            return attachmentDownloadRecord;
//        }).collect(Collectors.toList());
//        attachmentDownloadRecordMapper.batchUpdate(attachmentDownloadRecords);
//    }

    /**
     * 删除
     *
     * @param
     */
    @Override
    public void delete(List<Integer> list) {
        attachmentDownloadRecordMapper.delete(list);
    }
}
