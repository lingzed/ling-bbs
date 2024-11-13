package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.entity.po.UserInfo;
import com.ling.entity.vo.PageBean;
import com.ling.exception.BusinessException;
import com.ling.mappers.UserInfoMapper;
import com.ling.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public PageBean<UserInfo> find(UserInfo userInfo) {
        try {
            PageHelper.startPage(userInfo.getPage(), userInfo.getPageSize());
            List<UserInfo> userInfos = userInfoMapper.select(userInfo);
            Page<UserInfo> p = (Page<UserInfo>) userInfos;
            return PageBean.of(p.getTotal(), userInfo.getPage(), p.getPageSize(), p.getPages(), p.getResult());
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public UserInfo findById(String id) {
        try {
            return userInfoMapper.selectById(id);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public void add(UserInfo userInfo) {
        try {
            System.out.println(userInfo);
            Date date = new Date();
            userInfo.setCreateTime(date);
            userInfo.setUpdateTime(date);
            userInfoMapper.insert(userInfo);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    @Override
    public void batchAdd(List<UserInfo> userInfos) {
        try {
            Date date = new Date();
            List<UserInfo> newUserInfos = userInfos.stream().map(userInfo -> {
                userInfo.setCreateTime(date);
                userInfo.setUpdateTime(date);
                return userInfo;
            }).collect(Collectors.toList());
            userInfoMapper.batchInsert(newUserInfos);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    @Override
    public void edit(UserInfo userInfo) {
        try {
            Date date = new Date();
            userInfo.setUpdateTime(date);
            userInfoMapper.update(userInfo);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    public void batchEdit(List<UserInfo> UserInfos) {
        try {
            Date date = new Date();
            List<UserInfo> newUserInfos = UserInfos.stream().map(u -> {
                u.setCreateTime(date);
                u.setUpdateTime(date);
                return u;
            }).collect(Collectors.toList());
            userInfoMapper.batchUpdate(newUserInfos);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    public void delete(List<String> ids) {
        try {
            userInfoMapper.delete(ids);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.DELETE_FAIL, e);
        }
    }
}
