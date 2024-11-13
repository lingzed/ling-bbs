package com.ling.service;

import com.ling.entity.po.UserInfo;
import com.ling.entity.vo.PageBean;
import org.apache.ibatis.annotations.Param;

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
    void batchAdd(@Param("userInfos") List<UserInfo> userInfos);

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
    void batchEdit(@Param("userInfos") List<UserInfo> userInfos);

    /**
     * 删除/批量删除
     *
     * @param ids
     */
    void delete(List<String> ids);
}
