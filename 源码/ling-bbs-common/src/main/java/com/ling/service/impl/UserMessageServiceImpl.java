package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
//import com.ling.entity.dto.UserMessageDto;
import com.ling.entity.dto.UserMessageQueryDto;
import com.ling.entity.po.UserMessage;
//import com.ling.entity.vo.UserMessageVo;
import com.ling.entity.vo.PageBean;
import com.ling.exception.BusinessException;
import com.ling.mappers.UserMessageMapper;
import com.ling.service.UserMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    private Logger log = LoggerFactory.getLogger(UserMessageServiceImpl.class);

    @Resource
    private UserMessageMapper userMessageMapper;

    @Override
    public PageBean<UserMessage> findByCondition(UserMessageQueryDto userMessageParam) {
        try {
            PageHelper.startPage(userMessageParam.getPage(), userMessageParam.getPageSize());
            List<UserMessage> list = userMessageMapper.selectByCondition(userMessageParam);
            Page<UserMessage> p = (Page<UserMessage>) list;
            return PageBean.of(p.getTotal(),
                    p.getPageNum(),
                    p.getPageSize(),
                    p.getPages(),
                    p.getResult());
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

//    @Override
//    public PageBean<UserMessageVo> findVoListByCondition(UserMessageQuery userMessageParam) {
//        try {
//            PageHelper.startPage(userMessageParam.getPage(), userMessageParam.getPageSize());
//            List<UserMessageVo> list = userMessageMapper.selectVoListByCondition(userMessageParam);
//            Page<UserMessageVo> p = (Page<UserMessageVo>) list;
//            return PageBean.of(p.getTotal(),
//                    userMessageParam.getPage(),
//                    userMessageParam.getPageSize(),
//                    p.getPages(),
//                    p.getResult());
//        } catch (Exception e) {
//            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
//        }
//    }

    @Override
    public List<UserMessage> findAll() {
        try {
            return userMessageMapper.selectAll();
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

//    @Override
//    public UserMessageVo findVoById(String id) {
//        try {
//            UserMessage userMessage = userMessageMapper.selectById(id);
//            return userMessage2Vo(userMessage);
//        } catch (Exception e) {
//            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
//        }
//    }

    @Override
    public UserMessage findById(Integer id) {
        try {
            return userMessageMapper.selectById(id);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

//    private UserMessageVo userMessage2Vo(UserMessage userMessage) {
//        UserMessageVo userMessageVo = new UserMessageVo();
//        BeanUtils.copyProperties(userMessage, userMessageVo);
//        return userMessageVo;
//    }

    @Override
    public void add(UserMessage userMessage) {
        try {
            userMessageMapper.insert(repairUserMessage(userMessage));
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    @Override
    public void batchAdd(List<UserMessage> list) {
        try {
            List<UserMessage> newList = list.stream().map(this::repairUserMessage).collect(Collectors.toList());
            userMessageMapper.batchInsert(newList);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

//    @Override
//    public void dto4Add(UserMessageDto userMessageDto) {
//        try {
//            UserMessage userMessage = new UserMessage();
//            BeanUtils.copyProperties(userMessageDto, userMessage);
//            add(userMessage);
//        } catch (Exception e) {
//            throw new BusinessException(CommonMsg.ADD_FAIL, e);
//        }
//    }

//    @Override
//    public void dto4BatchAdd(List<UserMessageDto> list) {
//        try {
//            List<UserMessage> newList = list.stream().map(e -> {
//                UserMessage userMessage = new UserMessage();
//                BeanUtils.copyProperties(e, userMessage);
//                return repairUserMessage(userMessage);
//            }).collect(Collectors.toList());
//            userMessageMapper.batchInsert(newList);
//        } catch (Exception e) {
//            throw new BusinessException(CommonMsg.ADD_FAIL, e);
//        }
//    }

    private UserMessage repairUserMessage(UserMessage userMessage) {
        Date date = new Date();
        userMessage.setCreateTime(date);
        userMessage.setUpdateTime(date);
        return userMessage;
    }

    @Override
    public void edit(UserMessage userMessage) {
        try {
            Date date = new Date();
            userMessage.setUpdateTime(date);
            userMessageMapper.update(userMessage);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<UserMessage> list) {
        try {
            List<UserMessage> newList = list.stream().map(e -> {
                Date date = new Date();
                e.setUpdateTime(date);
                return e;
            }).collect(Collectors.toList());
            userMessageMapper.batchUpdate(newList);
        } catch (Exception e) {
            throw new RuntimeException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    public void delete(List<Integer> list) {
        try {
            userMessageMapper.delete(list);
        } catch (Exception e) {
            throw new RuntimeException(CommonMsg.DELETE_FAIL, e);
        }
    }
}
