package com.ling.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.entity.dto.SysSettingManager;
import com.ling.entity.po.SysSetting;
import com.ling.entity.vo.PageBean;
import com.ling.enums.SysSettingItemEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.SysSettingMapper;
import com.ling.service.SysSettingService;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import com.ling.utils.SysSettingManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysSettingServiceImpl implements SysSettingService {
    private static final Logger log = LoggerFactory.getLogger(SysSettingServiceImpl.class);
    private static final ObjectMapper objMapper = new ObjectMapper();
    @Resource
    private SysSettingMapper sysSettingMapper;

    /**
     * 分页查询系统设置
     *
     * @param sysSetting 查询条件
     * @return
     */
    @Override

    public PageBean<com.ling.entity.po.SysSetting> find(SysSetting sysSetting) {

        List<com.ling.entity.po.SysSetting> sysSettings = sysSettingMapper.select(sysSetting);
        Page<com.ling.entity.po.SysSetting> p = (Page<com.ling.entity.po.SysSetting>) sysSettings;
        return PageBean.of(p.getTotal(), sysSetting.getPage(), p.getPageSize(), p.getPages(), p.getResult());
    }

    /**
     * 查询所有系统设置
     *
     * @return
     */
    @Override
    public List<com.ling.entity.po.SysSetting> findAll() {
        return sysSettingMapper.selectAll();
    }

    /**
     * 根据编码查询系统设置
     *
     * @param code 编码
     * @return
     */
    @Override
    public com.ling.entity.po.SysSetting findByCode(String code) {
        return sysSettingMapper.selectByCode(code);
    }

    /**
     * 新增系统设置
     *
     * @param sysSetting 系统设置信息
     */
    @Override
    public void add(SysSetting sysSetting) {
        Date date = new Date();
        sysSetting.setCreateTime(date);
        sysSetting.setUpdateTime(date);
        sysSettingMapper.insert(sysSetting);
    }

    /**
     * 批量新增系统设置
     *
     * @param sysSettings 系统设置信息列表
     */
    @Override
    public void batchAdd(List<com.ling.entity.po.SysSetting> sysSettings) {
        Date date = new Date();
        List<com.ling.entity.po.SysSetting> newSysSettings = sysSettings.stream().map(sysSetting -> {
            sysSetting.setCreateTime(date);
            sysSetting.setUpdateTime(date);
            return sysSetting;
        }).collect(Collectors.toList());
        sysSettingMapper.batchInsert(newSysSettings);
    }

    /**
     * 编辑系统设置
     *
     * @param sysSetting 系统设置信息
     */
    @Override
    public void edit(SysSetting sysSetting) {
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
        Date date = new Date();
        List<com.ling.entity.po.SysSetting> newSysSettings = sysSettings.stream().map(sysSetting -> {
            sysSetting.setUpdateTime(date);
            return sysSetting;
        }).collect(Collectors.toList());
        sysSettingMapper.batchUpdate(newSysSettings);
    }

    /**
     * 删除系统设置
     *
     * @param codes 编码列表
     */
    @Override
    public void delete(List<String> codes) {
        sysSettingMapper.delete(codes);
    }

    /**
     * 初始化系统设置
     */
    @Override
    public void initSysSetting() {
        try {
            long start = System.currentTimeMillis();

            refresh();  // 刷新

            long end = System.currentTimeMillis();
            log.info("初始化系统设置完成，耗时: {}ms", end - start);
        } catch (Exception e) {
            log.info("初始化系统设置失败", e);
        }
    }

    /**
     * 刷新系统设置的缓存
     */
    @Override
    public void refreshCache() {
        try {
            long start = System.currentTimeMillis();

            refresh();  // 刷新

            long end = System.currentTimeMillis();
            log.info("刷新系统设置成功: 耗时{}ms", end - start);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.REFRESH_CACHE_FAIL, e);
        }
    }

    /**
     * 刷新系统设置
     * @throws ClassNotFoundException
     * @throws JsonProcessingException
     */
    private void refresh() throws ClassNotFoundException, JsonProcessingException {
        SysSettingManager ssm = SysCacheUtil.getSysSettingManager();    // 获取系统设置

        List<SysSetting> sysSettings = findAll();   // 查询所有系统设置

        for (SysSetting sysSetting : sysSettings) {
            String jsonStr = sysSetting.getJsonContent();   // 获取设置的json字符串
            if (StrUtil.isEmpty(jsonStr)) continue;

            String code = sysSetting.getCode();
            String oldJsonStr = (String) SysCacheUtil.getSysCache(code);  // 获取缓存的json字符串
            if (Objects.equals(oldJsonStr, jsonStr)) continue;

            SysSettingItemEnum ssItem = SysSettingItemEnum.getItemByCode(code);

            // 更新设置
            SysSettingManagerUtil.setterInvoke(ssm, code, jsonStr, Class.forName(ssItem.getClassname()));

            // 更新缓存json字符串
            SysCacheUtil.setSysCache(code, jsonStr);
        }
    }

    /**
     * 更新系统设置
     * @param sysSettingManager
     */
    @Transactional
    public void editSysSetting(SysSettingManager sysSettingManager) {
        try {
            long start = System.currentTimeMillis();

            // 获取缓存中旧的系统设置
            SysSettingManager ssm = SysCacheUtil.getSysSettingManager();

            for (SysSettingItemEnum value : SysSettingItemEnum.values()) {
                String code = value.getCode();

                Object val = SysSettingManagerUtil.getterInvoke(sysSettingManager, code); // 获取新值
                if (val == null) continue;

                String newJsonStr = objMapper.writeValueAsString(val);   // 序列化为json字符串
                String oldJsonStr = (String) SysCacheUtil.getSysCache(code);

                // 比较，若新旧设置相同，不更新
                if (!StrUtil.isEmpty(oldJsonStr) && Objects.equals(oldJsonStr, newJsonStr)) continue;

                // 更新数据库
                SysSetting sysSetting = new SysSetting();
                sysSetting.setCode(code);
                sysSetting.setJsonContent(newJsonStr);
                edit(sysSetting);

                // 更新缓存中的系统设置
                SysSettingManagerUtil.setterInvoke(ssm, code, val);

                // 更新缓存的json字符串
                SysCacheUtil.setSysCache(code, newJsonStr);
            }

            long end = System.currentTimeMillis();
            log.info("更新系统设置成功: 耗时{}ms", end - start);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.UPDATE_CACHE_FAIL, e);
        }

    }

}
