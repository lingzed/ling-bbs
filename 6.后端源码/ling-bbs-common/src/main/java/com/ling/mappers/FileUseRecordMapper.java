package com.ling.mappers;

import com.ling.entity.po.FileUseRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileUseRecordMapper {
    /**
     * 通过 记录id && 上传人 查询
     *
     * @param recordId
     * @param uploaderId
     * @return
     */
    List<FileUseRecord> selectByIdAndUploaderId(String recordId, String uploaderId);

    /**
     * 查询未使用
     *
     * @return
     */
    List<FileUseRecord> selectUnused();

    /**
     * 插入
     *
     * @param fileUseRecord
     */
    void insert(FileUseRecord fileUseRecord);

    /**
     * 更新
     *
     * @param fileUseRecord
     */
    void update(FileUseRecord fileUseRecord);

    /**
     * 删除未使用
     */
    void deleteUnused();
}
