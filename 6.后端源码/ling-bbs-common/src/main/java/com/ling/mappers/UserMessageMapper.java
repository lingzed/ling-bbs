package com.ling.mappers;

import com.ling.entity.dto.query.UserMessageQuery;
import com.ling.entity.po.UserMessage;
//import com.ling.entity.vo.UserMessageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMessageMapper {

    /**
     * 条件查询，返回po集
     *
     * @param userMessageParam 查询条件
     * @return
     */
    List<UserMessage> selectByCondition(UserMessageQuery userMessageParam);

//    /**
//     * 条件查询，返回vo集
//     *
//     * @param userMessageParam 查询条件
//     * @return
//     */
//    List<UserMessageVo> selectVoListByCondition(UserMessageQuery userMessageParam);

    /**
     * 条件计数
     *
     * @param userMessageParam
     * @return
     */
    Long countByCondition(UserMessageQuery userMessageParam);

    /**
     * 查询所有
     *
     * @return
     */
    List<UserMessage> selectAll();

    /**
     * 通过id查询
     *
     * @param messageId 消息id
     * @return
     */
    UserMessage selectById(Integer messageId);

    /**
     * 计数 通过 接收人 && id集
     *
     * @param userId
     * @param list
     * @return
     */
    Long countByReceiverAndIds(String userId, List<Integer> list);

    /**
     * 添加
     *
     * @param userMessage 实体
     */
    void insert(UserMessage userMessage);

    /**
     * 批量添加
     *
     * @param list 实体集合
     */
    void batchInsert(List<UserMessage> list);

    /**
     * 编辑
     *
     * @param userMessage 实体
     */
    void update(UserMessage userMessage);

    /**
     * 批量编辑
     *
     * @param list 实体集合
     */
    void batchUpdate(List<UserMessage> list);

    /**
     * 更新头像
     *
     * @param senderAvatar
     * @param sendUserId
     */
    void updateAvatar(String senderAvatar, String sendUserId);

    /**
     * 删除/批量删除
     *
     * @param list id集合
     */
    void delete(List<Integer> list);

    // 可选部分根据需要取消注释
//    /**
//     * 删除/批量删除
//     *
//     * @param ids id集合
//     */
//    void delete(List<Integer> ids);

//    /**
//     * 通过 xxx 查询
//     *
//     * @param xxx xxx
//     * @return
//     */
//    UserMessage selectByxxx(String xxx);
}
