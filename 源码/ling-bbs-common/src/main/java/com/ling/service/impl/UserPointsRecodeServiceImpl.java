package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.entity.dto.query.UserPointsRecodeQueryDto;
import com.ling.entity.po.UserInfo;
import com.ling.entity.po.UserPointsRecode;
import com.ling.entity.vo.PageBean;
import com.ling.exception.BusinessException;
import com.ling.mappers.UserInfoMapper;
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

    @Resource
    private UserInfoMapper userInfoMapper;

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

    /**
     * 处理积分操作
     * 增减用户积分 & 记录积分增减过程
     *
     * @param userId
     * @param operationType
     * @param points
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processUserPoints(String userId, Integer operationType, Integer points) {
        if (points == 0) return;
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Integer currentPoints = userInfo.getCurrentIntegral();
        Integer version = userInfo.getVersion();
        log.info("当前用户积分版本为: {}", version);
        currentPoints = Math.max(currentPoints + points, 0);    // 积分不能为负数，最小为0
        Integer row = userInfoMapper.updatePoints(userId, currentPoints, points, version);
        if (row == 0) throw new BusinessException(CommonMsg.POINTS_OPERATION_FAIL);
        // 记录积分操作记录
        UserPointsRecode userPointsRecode = new UserPointsRecode();
        userPointsRecode.setUserId(userId);
        userPointsRecode.setOperationType(operationType);
        userPointsRecode.setPoints(points);
        Date date = new Date();
        userPointsRecode.setCreateTime(date);
        userPointsRecode.setUpdateTime(date);
        userPointsRecodeMapper.insert(userPointsRecode);
    }
}
