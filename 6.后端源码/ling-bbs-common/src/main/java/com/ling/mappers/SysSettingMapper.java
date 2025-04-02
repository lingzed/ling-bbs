package com.ling.mappers;

import com.ling.entity.po.SysSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysSettingMapper {

    /**
     * 查询
     *
     * @param sysSetting
     * @return
     */
    List<SysSetting> select(SysSetting sysSetting);

    /**
     * 查询所有
     *
     * @return
     */
    List<SysSetting> selectAll();

    /**
     * 根据code查询
     *
     * @param code
     * @return
     */
    SysSetting selectByCode(String code);

    /**
     * 添加
     *
     * @param sysSetting
     */
    void insert(SysSetting sysSetting);

    /**
     * 批量添加
     *
     * @param sysSettings
     */
    void batchInsert(@Param("sysSettings") List<SysSetting> sysSettings);

    /**
     * 更新
     *
     * @param sysSetting
     */
    void update(SysSetting sysSetting);

    /**
     * 批量更新
     *
     * @param sysSettings
     */
    void batchUpdate(@Param("sysSettings") List<SysSetting> sysSettings);

    /**
     * 删除/批量删除
     *
     * @param ids
     */
    void delete(List<String> ids);
}
