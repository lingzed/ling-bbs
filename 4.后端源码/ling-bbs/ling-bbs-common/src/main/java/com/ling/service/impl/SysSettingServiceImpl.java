package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.entity.dto.SysSettingManager;
import com.ling.entity.po.SysSetting;
import com.ling.entity.vo.PageBean;
import com.ling.exception.BusinessException;
import com.ling.mappers.SysSettingMapper;
import com.ling.service.SysSettingService;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysSettingServiceImpl implements SysSettingService {
    private static final Logger log = LoggerFactory.getLogger(SysSettingServiceImpl.class);
    @Resource
    private SysSettingMapper sysSettingMapper;

    /**
     * 分页查询系统设置
     *
     * @param sysSetting 查询条件
     * @return
     */
    @Override

    public PageBean<com.ling.entity.po.SysSetting> find(com.ling.entity.po.SysSetting sysSetting) {
        try {
            PageHelper.startPage(sysSetting.getPage(), sysSetting.getPageSize());
            List<com.ling.entity.po.SysSetting> sysSettings = sysSettingMapper.select(sysSetting);
            Page<com.ling.entity.po.SysSetting> p = (Page<com.ling.entity.po.SysSetting>) sysSettings;
            return PageBean.of(p.getTotal(), sysSetting.getPage(), p.getPageSize(), p.getPages(), p.getResult());
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    /**
     * 查询所有系统设置
     *
     * @return
     */
    @Override
    public List<com.ling.entity.po.SysSetting> findAll() {
        try {
            return sysSettingMapper.selectAll();
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    /**
     * 根据编码查询系统设置
     *
     * @param code 编码
     * @return
     */
    @Override
    public com.ling.entity.po.SysSetting findByCode(String code) {
        try {
            return sysSettingMapper.selectByCode(code);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    /**
     * 新增系统设置
     *
     * @param sysSetting 系统设置信息
     */
    @Override
    public void add(com.ling.entity.po.SysSetting sysSetting) {
        try {
            Date date = new Date();
            sysSetting.setCreateTime(date);
            sysSetting.setUpdateTime(date);
            sysSettingMapper.insert(sysSetting);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    /**
     * 批量新增系统设置
     *
     * @param sysSettings 系统设置信息列表
     */
    @Override
    public void batchAdd(List<com.ling.entity.po.SysSetting> sysSettings) {
        try {
            Date date = new Date();
            List<com.ling.entity.po.SysSetting> newSysSettings = sysSettings.stream().map(sysSetting -> {
                sysSetting.setCreateTime(date);
                sysSetting.setUpdateTime(date);
                return sysSetting;
            }).collect(Collectors.toList());
            sysSettingMapper.batchInsert(newSysSettings);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    /**
     * 编辑系统设置
     *
     * @param sysSetting 系统设置信息
     */
    @Override
    public void edit(com.ling.entity.po.SysSetting sysSetting) {
        try {
            Date date = new Date();
            sysSetting.setUpdateTime(date);
            sysSettingMapper.update(sysSetting);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    /**
     * 批量编辑系统设置
     *
     * @param sysSettings 系统设置信息列表
     */
    @Override
    public void batchEdit(List<com.ling.entity.po.SysSetting> sysSettings) {
        try {
            Date date = new Date();
            List<com.ling.entity.po.SysSetting> newSysSettings = sysSettings.stream().map(sysSetting -> {
                sysSetting.setUpdateTime(date);
                return sysSetting;
            }).collect(Collectors.toList());
            sysSettingMapper.batchUpdate(newSysSettings);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    /**
     * 删除系统设置
     *
     * @param codes 编码列表
     */
    @Override
    public void delete(List<String> codes) {
        try {
            sysSettingMapper.delete(codes);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.DELETE_FAIL, e);
        }
    }

    /**
     * 刷新系统设置的缓存
     */
    @Override
    public void refreshCache() {
        try {
            long start = System.currentTimeMillis();
            SysSettingManager sysSettingManager = SysCacheUtil.getSysSettingManager();
            List<SysSetting> sysSettings = findAll();   // 查询所有系统设置
            for (SysSetting sysSetting : sysSettings) {
                String jsonContent = sysSetting.getJsonContent();   // 获取系统设置的JSON字符串内容
                if (StrUtil.isEmpty(jsonContent)) continue;
                String code = sysSetting.getCode();
                String cacheJsonContent = (String) SysCacheUtil.getSysCache(code);  // 获取缓存的json字符串
                if (Objects.equals(cacheJsonContent, jsonContent)) continue;
                SysCacheUtil.setterInvoke(code, sysSettingManager, jsonContent);
                SysCacheUtil.setSysCache(code, jsonContent);   // 缓存json字符串
            }
            long end = System.currentTimeMillis();
            log.info("更新系统设置耗时: {}ms", end - start);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.REFRESH_CACHE_FAIL, e);
        }
    }
}
