package com.ling.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.entity.dto.SysSettingContainer;
import com.ling.entity.po.SysSetting;
import com.ling.entity.vo.PageBean;
import com.ling.enums.SysSettingItemEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.SysSettingMapper;
import com.ling.service.SysSettingService;
import com.ling.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysSettingServiceImpl implements SysSettingService {
    @Resource
    private SysSettingMapper sysSettingMapper;

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

    @Override
    public SysSetting findByCode(String code) {
        try {
            return sysSettingMapper.selectByCode(code);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

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

    @Override
    public void delete(List<String> codes) {
        try {
            sysSettingMapper.delete(codes);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.DELETE_FAIL, e);
        }
    }

    public void refreshCache() {
        try {
            SysSettingContainer sysSettingContainer = new SysSettingContainer();
            List<SysSetting> SysSettings = sysSettingMapper.selectAll();
            for (SysSetting ss : SysSettings) {
                String jsonContent = ss.getJsonContent();
                if (StringUtil.isEmpty(jsonContent)) {
                    continue;
                }
                // 通过code获取设置类枚举项
                SysSettingItemEnum ssItem = SysSettingItemEnum.getItemByCode(ss.getCode());
                PropertyDescriptor pd = new PropertyDescriptor(ssItem.getField(), SysSettingContainer.class);
                Method setter = pd.getWriteMethod();   // 获取setter
                Class<?> aClass = Class.forName(ssItem.getClassname());
                // 执行setter
                setter.invoke(sysSettingContainer, JSON.parseObject(jsonContent, aClass));
            }
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.REFRESH_CACHE_FAIL);
        }
    }
}
