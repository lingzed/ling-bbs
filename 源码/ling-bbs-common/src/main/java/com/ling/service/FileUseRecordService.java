package com.ling.service;

import com.ling.entity.dto.query.FileUseRecordQueryDto;
import com.ling.entity.po.FileUseRecord;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface FileUseRecordService {
    /**
     * 查询所有
     */
    List<FileUseRecord> findAll();

    /**
     * 查询总数
     */
    Long findTotal();

    /**
     * 通过id查询
     *
     * @param
     */
    FileUseRecord findById(String id);

    /**
     * 通过 记录id && 上传人 查询
     *
     * @param recordId
     * @param uploaderId
     * @return
     */
    List<FileUseRecord> findByIdAndUploaderId(String recordId, String uploaderId);

    /**
     * 查询未使用
     *
     * @return
     */
    List<FileUseRecord> findUnused();

    /**
     * 通过id查询
     *
     * @param
     */
    FileUseRecord findById(Integer id);

    /**
     * 添加
     *
     * @param
     */
    void add(FileUseRecord fileUseRecord);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<FileUseRecord> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(FileUseRecord fileUseRecord);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<FileUseRecord> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<String> list);

    /**
     * 删除未使用
     */
    void deleteUnused();
}
