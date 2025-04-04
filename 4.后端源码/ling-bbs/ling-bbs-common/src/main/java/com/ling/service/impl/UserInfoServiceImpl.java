package com.ling.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.constant.Constant;
import com.ling.entity.dto.session.SessionUserinfo;
import com.ling.entity.po.*;
import com.ling.entity.vo.PageBean;
import com.ling.enums.*;
import com.ling.exception.BusinessException;
import com.ling.mappers.*;
import com.ling.service.MailCodeService;
import com.ling.service.UserInfoService;
import com.ling.service.UserPointsRecordService;
import com.ling.utils.OkHttpUtil;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private MailCodeMapper mailCodeMapper;
    @Resource
    private UserMessageMapper userMessageMapper;
    @Resource
    private UserPointsRecordService userPointsRecordService;
    @Resource
    private MailCodeService mailCodeService;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CommentMapper commentMapper;

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
    public void register(String nickname, String password, String mail, String mailCode, String avatar) {
        try {
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
            String uid = StrUtil.getRandomNum(Constant.NUM_10);
            userInfo.setUserId(uid);
            userInfo.setAvatar(avatar);
            userInfo.setNickName(nickname);
            userInfo.setEmail(mail);
            userInfo.setPassword(StrUtil.encodeMD5(password));
            Date date = new Date();
            userInfo.setJoinTime(date);
            userInfo.setTotalIntegral(Constant.NUM_0);      // 初始积分为0，后续从系统设置中更新
            userInfo.setCurrentIntegral(Constant.NUM_0);
            userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
            userInfo.setCreateTime(date);
            userInfo.setUpdateTime(date);
            userInfo.setVersion(Constant.NUM_0);    // 版本号初始为0
            userInfo.setAdmin(false);    // 注册的用户默认非管理员
            userInfoMapper.insert(userInfo);

            // 注册成功更新积分，5积分
            userPointsRecordService.processUserPoints(uid, OperationTypeEnum.REGISTER.getType(), Constant.NUM_5);

            // 注册成功，向用户发送消息欢迎消息，消息内容从系统设置中获取
            UserMessage userMessage = new UserMessage();
            userMessage.setReceivedUserId(uid);     // 接收者，即新用户
            userMessage.setMessageType(MessageTypeEnum.SYS_MESSAGE.getType());   // 消息类型：系统消息
            String welcomeInfo = SysCacheUtil.getSysSettingManager().getSysSetting4Register().getWelcomeInfo(); // 消息内容：从系统设置中获取
            userMessage.setMessageContent(welcomeInfo);
            userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());      // 状态：未读
            userMessage.setCreateTime(date);
            userMessage.setUpdateTime(date);
            userMessageMapper.insert(userMessage);
        } finally {
            // 验证码使用后，标记为已用
            mailCodeService.editToUsedByMail(mail);
        }
    }

    @Override
    public SessionUserinfo login(String IP, String mail, String password) {
        UserInfo userInfo = userInfoMapper.selectByEmail(mail);
        if (userInfo == null || !userInfo.getPassword().equals(password))
            throw new BusinessException(CommonMsg.USERNAME_OR_PASSWORD_ERROR);
        if (Objects.equals(userInfo.getStatus(), UserStatusEnum.DISABLE.getStatus()))
            throw new BusinessException(CommonMsg.USER_DISABLED);

        UserInfo ui = new UserInfo();
        ui.setUserId(userInfo.getUserId());
        String province = getProvince(IP);
        ui.setLastLoginTime(new Date());
        ui.setLastLoginIp(IP);
        ui.setLastLoginIpAddress(province);
        ui.setUpdateTime(new Date());
        userInfoMapper.update(ui);

        SessionUserinfo sessionUserinfo = new SessionUserinfo();
        sessionUserinfo.setUserId(userInfo.getUserId());
        sessionUserinfo.setMail(userInfo.getEmail());
        sessionUserinfo.setAvatar(userInfo.getAvatar());
        sessionUserinfo.setNickName(userInfo.getNickName());
        sessionUserinfo.setProvince(province);
        sessionUserinfo.setIsAdmin(userInfo.isAdmin());
        return sessionUserinfo;
    }

    @Override
    public void resetPwd(String mail, String password, String mailCode) {
        try {
            UserInfo userInfo = userInfoMapper.selectByEmail(mail);
            if (userInfo == null) throw new BusinessException(CommonMsg.MAIL_NOT_EXISTS);
            MailCode mailCodeInfo = mailCodeMapper.selectByMailAndCode(mail, mailCode);
            if (mailCodeInfo == null || System.currentTimeMillis() - mailCodeInfo.getCreateTime().getTime() > Constant.MIN_5_TO_MILLIS) {
                throw new BusinessException(CommonMsg.MAIL_CHECK_CODE_ERROR);
            }
            userInfo.setPassword(StrUtil.encodeMD5(password));
            userInfo.setUpdateTime(new Date());
            edit(userInfo);
        } finally {
            // 无论是否异常，都将邮箱验证码置为无效，所以不加事务会更好
            mailCodeService.editToUsedByMail(mail);
        }
    }

    /**
     * 获取IP归属地
     *
     * @param IP
     * @return
     */
    public String getProvince(String IP) {
        try {
            String url = "https://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + IP;
            String res = OkHttpUtil.getRequest(url);
            Map<String, Object> resMap = JSON.parseObject(res, Map.class);
            String province = (String) resMap.get("pro");
            return province;
        } catch (Exception e) {
            log.error(CommonMsg.FETCH_FAIL, e);
            return Constant.UNKNOWN;
        }
    }

    @Override
    public void checkMailExists(String mail, Integer env) {
        UserInfo userInfo = findByEmail(mail);
        if (Objects.equals(env, MailExistsCheckEnum.REGISTER_CHECK.getEnv()) && userInfo != null) {
            throw new BusinessException(CommonMsg.MAIL_EXISTS);
        }
        if (Objects.equals(env, MailExistsCheckEnum.RESET_PWD_CHECK.getEnv()) && userInfo == null) {
            throw new BusinessException(CommonMsg.MAIL_NOT_EXISTS);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processResetUserinfo(UserInfo userInfo, boolean keepAvatar) {
        editUserinfo(userInfo, keepAvatar);
        // 更新 文章表, 评论表, 消息表的头像
        if (!keepAvatar) {
            articleMapper.updateAvatar(userInfo.getAvatar(), userInfo.getUserId());
            commentMapper.updateAvatar(userInfo.getAvatar(), userInfo.getUserId());
            userMessageMapper.updateAvatar(userInfo.getAvatar(), userInfo.getUserId());
        }
    }

    @Override
    public void editUserinfo(UserInfo userInfo, boolean keepAvatar) {
        userInfoMapper.updateUserinfo(userInfo, keepAvatar);
    }
}
