package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.entity.dto.query.UserPointsRecordQueryDto;
import com.ling.entity.po.UserInfo;
import com.ling.entity.po.UserPointsRecord;
import com.ling.entity.vo.PageBean;
import com.ling.exception.BusinessException;
import com.ling.mappers.UserInfoMapper;
import com.ling.mappers.UserPointsRecordMapper;
import com.ling.service.UserPointsRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPointsRecordServiceImpl implements UserPointsRecordService {
    private Logger log = LoggerFactory.getLogger(UserPointsRecordServiceImpl.class);

    @Resource
    private UserPointsRecordMapper userPointsRecordMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public PageBean<UserPointsRecord> findByCondition(UserPointsRecordQueryDto userPointsRecordParam) {
        try {
            PageHelper.startPage(userPointsRecordParam.getPage(), userPointsRecordParam.getPageSize());
            List<UserPointsRecord> list = userPointsRecordMapper.selectByCondition(userPointsRecordParam);
            Page<UserPointsRecord> p = (Page<UserPointsRecord>) list;
            return PageBean.of(p.getTotal(),
                    userPointsRecordParam.getPage(),
                    userPointsRecordParam.getPageSize(),
                    p.getPages(),
                    p.getResult());
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public List<UserPointsRecord> findAll() {
        try {
            return userPointsRecordMapper.selectAll();
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public UserPointsRecord findById(Integer id) {
        try {
            return userPointsRecordMapper.selectById(id);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public void add(UserPointsRecord userPointsRecord) {
        try {
            userPointsRecordMapper.insert(repairUserPointsRecord(userPointsRecord));
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    @Override
    public void batchAdd(List<UserPointsRecord> list) {
        try {
            List<UserPointsRecord> newList = list.stream().map(this::repairUserPointsRecord).collect(Collectors.toList());
            userPointsRecordMapper.batchInsert(newList);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    private UserPointsRecord repairUserPointsRecord(UserPointsRecord userPointsRecord) {
        Date date = new Date();
        userPointsRecord.setCreateTime(date);
        userPointsRecord.setUpdateTime(date);
        return userPointsRecord;
    }

    @Override
    public void edit(UserPointsRecord userPointsRecord) {
        try {
            Date date = new Date();
            userPointsRecord.setUpdateTime(date);
            userPointsRecordMapper.update(userPointsRecord);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<UserPointsRecord> list) {
        try {
            List<UserPointsRecord> newList = list.stream().map(e -> {
                Date date = new Date();
                e.setUpdateTime(date);
                return e;
            }).collect(Collectors.toList());
            userPointsRecordMapper.batchUpdate(newList);
        } catch (Exception e) {
            throw new RuntimeException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    public void delete(List<Integer> list) {
        try {
            userPointsRecordMapper.delete(list);
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
        UserPointsRecord userPointsRecord = new UserPointsRecord();
        userPointsRecord.setUserId(userId);
        userPointsRecord.setOperationType(operationType);
        userPointsRecord.setPoints(points);
        Date date = new Date();
        userPointsRecord.setCreateTime(date);
        userPointsRecord.setUpdateTime(date);
        userPointsRecordMapper.insert(userPointsRecord);
    }
}
