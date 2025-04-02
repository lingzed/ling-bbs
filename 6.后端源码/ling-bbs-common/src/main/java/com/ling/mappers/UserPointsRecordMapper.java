package com.ling.mappers;

//import com.ling.entity.dto.UserPointsRecodeQuery;

import com.ling.entity.dto.query.UserPointsRecordQuery;
import com.ling.entity.po.UserPointsRecord;
//import com.ling.entity.vo.UserPointsRecodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserPointsRecordMapper {

    /**
     * 条件查询，返回po集
     *
     * @param userPointsRecodeParam 查询条件
     * @return
     */
    List<UserPointsRecord> selectByCondition(UserPointsRecordQuery userPointsRecodeParam);

//    /**
//     * 条件查询，返回vo集
//     *
//     * @param userPointsRecodeParam 查询条件
//     * @return
//     */
//    List<UserPointsRecodeVo> selectVoListByCondition(UserPointsRecodeQuery userPointsRecodeParam);

    /**
     * 条件计数
     *
     * @param userPointsRecordQueryDto
     * @return
     */
    Long countByCondition(UserPointsRecordQuery userPointsRecordQueryDto);

    /**
     * 查询所有
     *
     * @return
     */
    List<UserPointsRecord> selectAll();

    /**
     * 通过id查询
     *
     * @param recodeId 记录id
     * @return
     */
    UserPointsRecord selectById(Integer recodeId);

    /**
     * 添加
     *
     * @param userPointsRecord 实体
     */
    void insert(UserPointsRecord userPointsRecord);

    /**
     * 批量添加
     *
     * @param list 实体集合
     */
    void batchInsert(List<UserPointsRecord> list);

    /**
     * 编辑
     *
     * @param userPointsRecord 实体
     */
    void update(UserPointsRecord userPointsRecord);

    /**
     * 批量编辑
     *
     * @param list 实体集合
     */
    void batchUpdate(List<UserPointsRecord> list);

    /**
     * 删除/批量删除
     *
     * @param list id集合
     */
    void delete(List<Integer> list);

    // 可选部分根据需要取消注释
//    /**
//     * 删除/批量删除
//     *
//     * @param ids id集合
//     */
//    void delete(List<Integer> ids);
}
