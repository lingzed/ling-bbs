package com.ling.service;

import com.ling.entity.dto.UserInfoSessionDto;
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
     */
    void register(String nickname, String password, String mail, String mailCode);

    /**
     * 积分操作
     *
     * @param userId        用户id
     * @param operationType 操作类型
     * @param points        积分
     */
    void operationPoints(String userId, Integer operationType, Integer points);

    /**
     * 登录
     *
     * @param IP       IP
     * @param mail     邮箱
     * @param password 密码
     * @return
     */
    UserInfoSessionDto login(String IP, String mail, String password);

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
}
