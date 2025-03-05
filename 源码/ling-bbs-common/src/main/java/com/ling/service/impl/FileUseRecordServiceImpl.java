package com.ling.service.impl;

import com.ling.entity.dto.query.FileUseRecordQueryDto;
import com.ling.entity.po.FileUseRecord;
import com.ling.entity.vo.PageBean;
import com.ling.mappers.FileUseRecordMapper;
import com.ling.service.FileUseRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileUseRecordServiceImpl implements FileUseRecordService {
    @Resource
    private FileUseRecordMapper fileUseRecordMapper;

    @Override
    public List<FileUseRecord> findAll() {
        return null;
    }

    @Override
    public Long findTotal() {
        return null;
    }

    @Override
    public FileUseRecord findById(String id) {
        return null;
    }

    @Override
    public List<FileUseRecord> findByIdAndUploaderId(String recordId, String uploaderId) {
        return fileUseRecordMapper.selectByIdAndUploaderId(recordId, uploaderId);
    }

    @Override
    public List<FileUseRecord> findUnused() {
        return fileUseRecordMapper.selectUnused();
    }

    @Override
    public FileUseRecord findById(Integer id) {
        return null;
    }

    @Override
    public void add(FileUseRecord fileUseRecord) {
        fileUseRecordMapper.insert(fileUseRecord);
    }

    @Override
    public void batchAdd(List<FileUseRecord> list) {

    }

    @Override
    public void edit(FileUseRecord fileUseRecord) {
        fileUseRecordMapper.update(fileUseRecord);
    }

    @Override
    public void batchEdit(List<FileUseRecord> list) {

    }

    @Override
    public void delete(List<String> list) {

    }

    @Override
    public void deleteUnused() {
        fileUseRecordMapper.deleteUnused();
    }
}
