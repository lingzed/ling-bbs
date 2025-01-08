package com.ling.mappers;

//import com.ling.entity.dto.UserPointsRecodeQuery;
import com.ling.entity.dto.query.UserPointsRecodeQueryDto;
import com.ling.entity.po.UserPointsRecode;
//import com.ling.entity.vo.UserPointsRecodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserPointsRecodeMapper {

    /**
     * 条件查询，返回po集
     *
     * @param userPointsRecodeParam 查询条件
     * @return
     */
    List<UserPointsRecode> selectByCondition(UserPointsRecodeQueryDto userPointsRecodeParam);

//    /**
//     * 条件查询，返回vo集
//     *
//     * @param userPointsRecodeParam 查询条件
//     * @return
//     */
//    List<UserPointsRecodeVo> selectVoListByCondition(UserPointsRecodeQuery userPointsRecodeParam);

    /**
     * 查询所有
     *
     * @return
     */
    List<UserPointsRecode> selectAll();

    /**
     * 通过id查询
     *
     * @param recodeId 记录id
     * @return
     */
    UserPointsRecode selectById(Integer recodeId);

    /**
     * 添加
     *
     * @param userPointsRecode 实体
     */
    void insert(UserPointsRecode userPointsRecode);

    /**
     * 批量添加
     *
     * @param list 实体集合
     */
    void batchInsert(List<UserPointsRecode> list);

    /**
     * 编辑
     *
     * @param userPointsRecode 实体
     */
    void update(UserPointsRecode userPointsRecode);

    /**
     * 批量编辑
     *
     * @param list 实体集合
     */
    void batchUpdate(List<UserPointsRecode> list);

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
