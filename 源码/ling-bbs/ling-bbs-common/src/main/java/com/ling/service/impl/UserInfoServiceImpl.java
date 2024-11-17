package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.constant.Constant;
import com.ling.entity.po.MailCode;
import com.ling.entity.po.UserInfo;
import com.ling.entity.vo.PageBean;
import com.ling.enums.MailCodeStatusEnum;
import com.ling.enums.UserStatusEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.MailCodeMapper;
import com.ling.mappers.UserInfoMapper;
import com.ling.service.MailCodeService;
import com.ling.service.UserInfoService;
import com.ling.utils.StringUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Autowired
    private MailCodeMapper mailCodeMapper;

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
    public UserInfo findByEmail(String email) {
        try {
            return userInfoMapper.selectByEmail(email);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public UserInfo findByNickname(String nickname) {
        try {
            return userInfoMapper.selectByNickname(nickname);
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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String nickname, String password, String mail, String checkCode, String sCheckCode, String mailCode) {
        // 校验验证码
        if (!checkCode.equalsIgnoreCase(sCheckCode)) {
            throw new BusinessException(CommonMsg.CHECK_CODE_ERROR);
        }
        // 检查邮箱唯一性
        UserInfo byEmail = findByEmail(mail);
        if (byEmail != null) {
            throw new BusinessException(CommonMsg.MAIL_EXISTS);
        }
        // 检查昵称唯一性
        UserInfo byNickname = findByNickname(nickname);
        if (byNickname != null) {
            throw new BusinessException(CommonMsg.REQUEST_ALREADY_EXISTS);
        }
        // 校验邮箱验证码，根据mailCode和mail未能查到数据 or 过期，则验证失败
        MailCode mailCodeInfo = mailCodeMapper.selectByMailAndCode(mail, mailCode);
        if (mailCodeInfo == null || System.currentTimeMillis() - mailCodeInfo.getCreateTime().getTime() > Constant.MIN_5_TO_MILLIS) {
            throw new BusinessException(CommonMsg.MAIL_CHECK_CODE_ERROR);
        }
        // 创建并插入用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(StringUtil.getRandomNum(Constant.NUM_10));
        userInfo.setNickName(nickname);
        userInfo.setEmail(mail);
        userInfo.setPassword(StringUtil.encodeMD5(password));
        Date date = new Date();
        userInfo.setJoinTime(date);
        // 设置积分 TODO
        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        userInfo.setCreateTime(date);
        userInfo.setUpdateTime(date);
        userInfoMapper.insert(userInfo);
        // 将邮箱验证码置为无效
        mailCodeMapper.updateStatusByMail(mail);
    }
}
