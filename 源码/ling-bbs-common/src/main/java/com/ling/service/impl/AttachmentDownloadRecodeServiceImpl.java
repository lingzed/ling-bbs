package com.ling.service.impl;

import com.ling.commons.CommonMsg;
import com.ling.entity.dto.query.AttachmentDownloadRecodeQueryDto;
import com.ling.entity.po.AttachmentDownloadRecode;
import com.ling.entity.vo.PageBean;
import com.ling.exception.BusinessException;
import com.ling.mappers.AttachmentDownloadRecodeMapper;
import com.ling.service.AttachmentDownloadRecodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentDownloadRecodeServiceImpl implements AttachmentDownloadRecodeService {
    private Logger log = LoggerFactory.getLogger(AttachmentDownloadRecodeServiceImpl.class);

    @Resource
    private AttachmentDownloadRecodeMapper attachmentDownloadRecodeMapper;

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public PageBean<AttachmentDownloadRecode> findByCondition(AttachmentDownloadRecodeQueryDto attachmentDownloadRecodeQueryDto) {
        List<AttachmentDownloadRecode> list = attachmentDownloadRecodeMapper.selectByCondition(attachmentDownloadRecodeQueryDto);
        Long total = findTotalByCondition(attachmentDownloadRecodeQueryDto);
        return PageBean.of(total, attachmentDownloadRecodeQueryDto.getPage(), attachmentDownloadRecodeQueryDto.getPageSize(), list);
    }

    /**
     * 条件查询总数
     *
     * @param
     */
    @Override
    public Long findTotalByCondition(AttachmentDownloadRecodeQueryDto attachmentDownloadRecodeQueryDto) {
        return attachmentDownloadRecodeMapper.selectCountByCondition(attachmentDownloadRecodeQueryDto);
    }

    /**
     * 查询所有
     */
    @Override
    public List<AttachmentDownloadRecode> findAll() {
        return attachmentDownloadRecodeMapper.selectAll();
    }

    /**
     * 查询总数
     */
    @Override
    public Long findTotal() {
        return attachmentDownloadRecodeMapper.selectCount();
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public AttachmentDownloadRecode findById(String id) {
        return attachmentDownloadRecodeMapper.selectById(id);
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public AttachmentDownloadRecode findById(Integer id) {
        return attachmentDownloadRecodeMapper.selectById(id);
    }

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(AttachmentDownloadRecode attachmentDownloadRecode) {
        Date date = new Date();
        attachmentDownloadRecode.setDownloadTime(date);
        attachmentDownloadRecodeMapper.insert(attachmentDownloadRecode);
    }

//    /**
//     * 添加
//     *
//     * @param
//     */
//    @Override
//    public void add(AttachmentDownloadRecodeDto attachmentDownloadRecodeDto) {
//        AttachmentDownloadRecode attachmentDownloadRecode = new AttachmentDownloadRecode();
//        Date date = new Date();
//        attachmentDownloadRecode.setCreateTime(date);
//        attachmentDownloadRecode.setUpdateTime(date);
//        BeanUtils.copyProperties(attachmentDownloadRecodeDto, attachmentDownloadRecode);
//        attachmentDownloadRecodeMapper.insert(attachmentDownloadRecode);
//    }

    /**
     * 批量添加
     *
     * @param
     */
    @Override
    public void batchAdd(List<AttachmentDownloadRecode> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setDownloadTime(date);
        });
        attachmentDownloadRecodeMapper.batchInsert(list);
    }

//    /**
//     * 批量添加
//     *
//     * @param
//     */
//    @Override
//    public void batchAdd(List<AttachmentDownloadRecodeDto> list) {
//        Date date = new Date();
//        List<AttachmentDownloadRecode> attachmentDownloadRecodes = list.stream().map(e -> {
//            AttachmentDownloadRecode attachmentDownloadRecode = new AttachmentDownloadRecode();
//            attachmentDownloadRecode.setCreateTime(date);
//            attachmentDownloadRecode.setUpdateTime(date);
//            BeanUtils.copyProperties(e, attachmentDownloadRecode);
//            return attachmentDownloadRecode;
//        }).collect(Collectors.toList());
//        attachmentDownloadRecodeMapper.batchInsert(attachmentDownloadRecodes);
//    }

    /**
     * 编辑
     *
     * @param
     */
    @Override
    public void edit(AttachmentDownloadRecode attachmentDownloadRecode) {
        attachmentDownloadRecodeMapper.update(attachmentDownloadRecode);
    }

//    /**
//     * 编辑
//     *
//     * @param
//     */
//    @Override
//    public void edit(AttachmentDownloadRecodeDto attachmentDownloadRecodeDto) {
//        AttachmentDownloadRecode attachmentDownloadRecode = new AttachmentDownloadRecode();
//        Date date = new Date();
//        attachmentDownloadRecode.setUpdateTime(date);
//        BeanUtils.copyProperties(attachmentDownloadRecodeDto, attachmentDownloadRecode);
//        attachmentDownloadRecodeMapper.update(attachmentDownloadRecode);
//    }

    /**
     * 批量编辑
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<AttachmentDownloadRecode> list) {
        attachmentDownloadRecodeMapper.batchUpdate(list);
    }

//    /**
//     * 批量编辑
//     *
//     * @param
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void batchEdit(List<AttachmentDownloadRecodeDto> list) {
//        Date date = new Date();
//        List<AttachmentDownloadRecode> attachmentDownloadRecodes = list.stream().map(e -> {
//            AttachmentDownloadRecode attachmentDownloadRecode = new AttachmentDownloadRecode();
//            attachmentDownloadRecode.setUpdateTime(date);
//            BeanUtils.copyProperties(e, attachmentDownloadRecode);
//            return attachmentDownloadRecode;
//        }).collect(Collectors.toList());
//        attachmentDownloadRecodeMapper.batchUpdate(attachmentDownloadRecodes);
//    }

    /**
     * 删除
     *
     * @param
     */
    @Override
    public void delete(List<Integer> list) {
        attachmentDownloadRecodeMapper.delete(list);
    }
}
