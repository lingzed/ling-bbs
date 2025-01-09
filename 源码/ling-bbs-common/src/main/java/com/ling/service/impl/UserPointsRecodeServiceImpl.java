package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.entity.dto.query.UserPointsRecodeQueryDto;
import com.ling.entity.po.UserPointsRecode;
import com.ling.entity.vo.PageBean;
import com.ling.exception.BusinessException;
import com.ling.mappers.UserPointsRecodeMapper;
import com.ling.service.UserPointsRecodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPointsRecodeServiceImpl implements UserPointsRecodeService {
    private Logger log = LoggerFactory.getLogger(UserPointsRecodeServiceImpl.class);

    @Resource
    private UserPointsRecodeMapper userPointsRecodeMapper;

    @Override
    public PageBean<UserPointsRecode> findByCondition(UserPointsRecodeQueryDto userPointsRecodeParam) {
        try {
            PageHelper.startPage(userPointsRecodeParam.getPage(), userPointsRecodeParam.getPageSize());
            List<UserPointsRecode> list = userPointsRecodeMapper.selectByCondition(userPointsRecodeParam);
            Page<UserPointsRecode> p = (Page<UserPointsRecode>) list;
            return PageBean.of(p.getTotal(),
                    userPointsRecodeParam.getPage(),
                    userPointsRecodeParam.getPageSize(),
                    p.getPages(),
                    p.getResult());
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public List<UserPointsRecode> findAll() {
        try {
            return userPointsRecodeMapper.selectAll();
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public UserPointsRecode findById(Integer id) {
        try {
            return userPointsRecodeMapper.selectById(id);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public void add(UserPointsRecode userPointsRecode) {
        try {
            userPointsRecodeMapper.insert(repairUserPointsRecode(userPointsRecode));
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    @Override
    public void batchAdd(List<UserPointsRecode> list) {
        try {
            List<UserPointsRecode> newList = list.stream().map(this::repairUserPointsRecode).collect(Collectors.toList());
            userPointsRecodeMapper.batchInsert(newList);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    private UserPointsRecode repairUserPointsRecode(UserPointsRecode userPointsRecode) {
        Date date = new Date();
        userPointsRecode.setCreateTime(date);
        userPointsRecode.setUpdateTime(date);
        return userPointsRecode;
    }

    @Override
    public void edit(UserPointsRecode userPointsRecode) {
        try {
            Date date = new Date();
            userPointsRecode.setUpdateTime(date);
            userPointsRecodeMapper.update(userPointsRecode);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<UserPointsRecode> list) {
        try {
            List<UserPointsRecode> newList = list.stream().map(e -> {
                Date date = new Date();
                e.setUpdateTime(date);
                return e;
            }).collect(Collectors.toList());
            userPointsRecodeMapper.batchUpdate(newList);
        } catch (Exception e) {
            throw new RuntimeException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    public void delete(List<Integer> list) {
        try {
            userPointsRecodeMapper.delete(list);
        } catch (Exception e) {
            throw new RuntimeException(CommonMsg.DELETE_FAIL, e);
        }
    }
}
