package com.ling.service;

import com.ling.entity.po.SysSetting;
import com.ling.entity.vo.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysSettingService {

    /**
     * 查询系统设置信息
     *
     * @param sysSetting 查询条件
     * @return 系统设置信息的分页结果
     */
    PageBean<SysSetting> find(SysSetting sysSetting);

    /**
     * 根据编码查询系统设置信息
     *
     * @param code 编码
     * @return 系统设置信息
     */
    SysSetting findByCode(String code);

    /**
     * 添加系统设置信息
     *
     * @param sysSetting 系统设置信息
     */
    void add(SysSetting sysSetting);

    /**
     * 批量添加系统设置信息
     *
     * @param sysSettings 系统设置信息列表
     */
    void batchAdd(List<SysSetting> sysSettings);

    /**
     * 更新系统设置信息
     *
     * @param sysSetting 系统设置信息
     */
    void edit(SysSetting sysSetting);

    /**
     * 批量更新系统设置信息
     *
     * @param sysSettings 系统设置信息列表
     */
    void batchEdit(List<SysSetting> sysSettings);

    /**
     * 删除/批量删除系统设置信息
     *
     * @param codes 编码列表
     */
    void delete(List<String> codes);
}
