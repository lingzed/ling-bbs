package com.ling.service;

import com.ling.entity.dto.session.SessionUserinfo;
import com.ling.entity.po.UserInfo;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface UserInfoService {

    /**
     * 查询
     *
     * @param userInfo
     * @return
     */
    PageBean<UserInfo> find(UserInfo userInfo);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    UserInfo findById(String id);

    /**
     * 根据邮箱查询
     *
     * @param email
     * @return
     */
    UserInfo findByEmail(String email);

    /**
     * 根据昵称查询
     *
     * @param nickname
     * @return
     */
    UserInfo findByNickname(String nickname);

    /**
     * 添加
     *
     * @param userInfo
     */
    void add(UserInfo userInfo);

    /**
     * 批量添加
     *
     * @param userInfos
     */
    void batchAdd(List<UserInfo> userInfos);

    /**
     * 更新
     *
     * @param userInfo
     */
    void edit(UserInfo userInfo);

    /**
     * 批量更新
     *
     * @param userInfos
     */
    void batchEdit(List<UserInfo> userInfos);

    /**
     * 删除/批量删除
     *
     * @param ids
     */
    void delete(List<String> ids);

    /**
     * 注册
     *
     * @param nickname
     * @param password
     * @param mail
     * @param mailCode
     * @param avatar
     */
    void register(String nickname, String password, String mail, String mailCode, String avatar);

    /**
     * 登录
     *
     * @param IP       IP
     * @param mail     邮箱
     * @param password 密码
     * @return
     */
    SessionUserinfo login(String IP, String mail, String password);

    /**
     * 重置密码
     *
     * @param mail     邮箱
     * @param password 密码
     * @param mailCode 邮箱验证码
     */
    void resetPwd(String mail, String password, String mailCode);

    /**
     * 检查邮箱是否存在
     *
     * @param mail
     * @param env
     */
    void checkMailExists(String mail, Integer env);

    /**
     * 处理用户信息更新
     *
     * @param userInfo
     * @param keepAvatar
     * @return
     */
    void processResetUserinfo(UserInfo userInfo, boolean keepAvatar);

    /**
     * 更新用户信息
     *
     * @param userInfo
     * @param keepAvatar
     */
    void editUserinfo(UserInfo userInfo, boolean keepAvatar);
}
