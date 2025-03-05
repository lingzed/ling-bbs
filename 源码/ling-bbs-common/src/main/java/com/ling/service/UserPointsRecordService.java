package com.ling.service;

//import com.ling.entity.dto.UserPointsRecodeDto;

import com.ling.entity.dto.query.UserPointsRecordQueryDto;
import com.ling.entity.po.UserPointsRecord;
//import com.ling.entity.vo.UserPointsRecodeVo;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface UserPointsRecordService {

    /**
     * 条件查询，返回po集
     *
     * @param userPointsRecordParam 查询条件
     * @return
     */
    PageBean<UserPointsRecord> findByCondition(UserPointsRecordQueryDto userPointsRecordParam);

//    /**
//     * 条件查询，返回vo集
//     *
//     * @param userPointsRecodeParam 查询条件
//     * @return
//     */
//    PageBean<UserPointsRecodeVo> findVoListByCondition(UserPointsRecodeQuery userPointsRecodeParam);

    /**
     * 查询所有
     *
     * @return
     */
    List<UserPointsRecord> findAll();   // 返回实体

//    /**
//     * 通过id查询，返回vo
//     *
//     * @param recodeId 记录id
//     * @return
//     */
//    UserPointsRecodeVo findVoById(Integer recodeId);

    /**
     * 通过id查询，返回po
     *
     * @param recordId 记录id
     * @return
     */
    UserPointsRecord findById(Integer recordId);

    /**
     * 添加
     *
     * @param userPointsRecord po
     */
    void add(UserPointsRecord userPointsRecord);

    /**
     * 批量添加
     *
     * @param list po集合
     */
    void batchAdd(List<UserPointsRecord> list);

//    /**
//     * 添加
//     *
//     * @param userPointsRecodeDto dto
//     */
//    void dto4Add(UserPointsRecodeDto userPointsRecodeDto);

//    /**
//     * 批量添加
//     *
//     * @param list dto集合
//     */
//    void dto4BatchAdd(List<UserPointsRecodeDto> list);

    /**
     * 编辑
     *
     * @param userPointsRecord po
     */
    void edit(UserPointsRecord userPointsRecord);

    /**
     * 批量编辑
     *
     * @param list po集合
     */
    void batchEdit(List<UserPointsRecord> list);

//    /**
//     * 编辑
//     *
//     * @param userPointsRecodeDto dto
//     */
//    void dto4Edit(UserPointsRecodeDto userPointsRecodeDto);
//
//    /**
//     * 批量编辑
//     *
//     * @param list dto集合
//     */
//    void dto4BatchEdit(List<UserPointsRecodeDto> list);

    /**
     * 删除/批量删除
     *
     * @param list 记录id集合
     */
    void delete(List<Integer> list);

    /**
     * 处理积分操作
     * 增减用户积分 & 记录积分增减过程
     *
     * @param userId
     * @param operationType
     * @param points
     */
    void processUserPoints(String userId, Integer operationType, Integer points);
}
