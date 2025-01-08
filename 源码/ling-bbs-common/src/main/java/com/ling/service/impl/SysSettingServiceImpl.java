package com.ling.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.config.CommonConfig;
import com.ling.entity.dto.SysSettingContainer;
import com.ling.entity.po.SysSetting;
import com.ling.entity.vo.PageBean;
import com.ling.enums.SysSettingItemEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.SysSettingMapper;
import com.ling.service.SysSettingService;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysSettingServiceImpl implements SysSettingService {
    @Resource
    private SysSettingMapper sysSettingMapper;
    @Qualifier("commonConfig")
    @Autowired
    private CommonConfig commonConfig;

    /**
     * 分页查询系统设置
     *
     * @param sysSetting 查询条件
     * @return
     */
    @Override
    public PageBean<SysSetting> find(SysSetting sysSetting) {
        try {
            PageHelper.startPage(sysSetting.getPage(), sysSetting.getPageSize());
            List<SysSetting> sysSettings = sysSettingMapper.select(sysSetting);
            Page<SysSetting> p = (Page<SysSetting>) sysSettings;
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
    public List<SysSetting> findAll() {
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
    public SysSetting findByCode(String code) {
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
    public void add(SysSetting sysSetting) {
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
    public void batchAdd(List<SysSetting> sysSettings) {
        try {
            Date date = new Date();
            List<SysSetting> newSysSettings = sysSettings.stream().map(sysSetting -> {
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
    public void batchEdit(List<SysSetting> sysSettings) {
        try {
            Date date = new Date();
            List<SysSetting> newSysSettings = sysSettings.stream().map(sysSetting -> {
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
            SysSettingContainer sysSettingContainer = new SysSettingContainer();
            List<SysSetting> sysSettings = findAll();   // 查询所有系统设置
            for (SysSetting sysSetting : sysSettings) {
                String jsonContent = sysSetting.getJsonContent();   // 获取系统设置的JSON字符串内容
                if (StrUtil.isEmpty(jsonContent)) continue;
                // 获取系统设置项枚举
                SysSettingItemEnum settingItem = SysSettingItemEnum.getItemByCode(sysSetting.getCode());
                // 反射拿到SysSettingContainer对应属性的setter
                PropertyDescriptor pd = new PropertyDescriptor(settingItem.getField(), SysSettingContainer.class);
                Method writeMethod = pd.getWriteMethod();  // 获取写方法
                Class<?> aClass = Class.forName(settingItem.getClassname());
                // 调用setter方法，将系统设置的JSON字符串内容转换为对应的Java对象
                writeMethod.invoke(sysSettingContainer, JSON.parseObject(jsonContent, aClass));
            }
            // 将系统设置容器放入缓存
            SysCacheUtil.setSysCache(sysSettingContainer);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.REFRESH_CACHE_FAIL, e);
        }
    }
}
