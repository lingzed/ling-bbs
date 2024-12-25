package com.ling.service;

//import com.ling.entity.dto.UserPointsRecodeDto;
import com.ling.entity.dto.UserPointsRecodeQuery;
import com.ling.entity.po.UserPointsRecode;
//import com.ling.entity.vo.UserPointsRecodeVo;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface UserPointsRecodeService {

    /**
     * 条件查询，返回po集
     *
     * @param userPointsRecodeParam 查询条件
     * @return
     */
    PageBean<UserPointsRecode> findByCondition(UserPointsRecodeQuery userPointsRecodeParam);

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
    List<UserPointsRecode> findAll();   // 返回实体

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
     * @param recodeId 记录id
     * @return
     */
    UserPointsRecode findById(Integer recodeId);

    /**
     * 添加
     *
     * @param userPointsRecode po
     */
    void add(UserPointsRecode userPointsRecode);

    /**
     * 批量添加
     *
     * @param list po集合
     */
    void batchAdd(List<UserPointsRecode> list);

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
     * @param userPointsRecode po
     */
    void edit(UserPointsRecode userPointsRecode);

    /**
     * 批量编辑
     *
     * @param list po集合
     */
    void batchEdit(List<UserPointsRecode> list);

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
}
