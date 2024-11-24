package com.ling.mappers;

import com.ling.entity.po.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    /**
     * 查询
     *
     * @param userInfo
     * @return
     */
    List<UserInfo> select(UserInfo userInfo);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    UserInfo selectById(String id);

    /**
     * 根据邮箱查询
     *
     * @param email
     * @return
     */
    UserInfo selectByEmail(String email);

    /**
     * 根据昵称查询
     *
     * @param nickname
     * @return
     */
    UserInfo selectByNickname(String nickname);

    /**
     * 添加
     *
     * @param userInfo
     */
    void insert(UserInfo userInfo);

    /**
     * 批量添加
     *
     * @param userInfos
     */
    void batchInsert(@Param("userInfos") List<UserInfo> userInfos);

    /**
     * 更新
     *
     * @param userInfo
     */
    void update(UserInfo userInfo);

    /**
     * 批量更新
     *
     * @param userInfos
     */
    void batchUpdate(@Param("userInfos") List<UserInfo> userInfos);

    /**
     * 删除/批量删除
     *
     * @param ids
     */
    void delete(List<String> ids);
}
