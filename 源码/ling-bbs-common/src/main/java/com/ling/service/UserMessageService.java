package com.ling.service;

//import com.ling.entity.dto.UserMessageDto;
import com.ling.entity.dto.query.UserMessageQueryDto;
import com.ling.entity.po.UserMessage;
//import com.ling.entity.vo.UserMessageVo;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface UserMessageService {

    /**
     * 条件查询，返回po集
     *
     * @param userMessageParam 查询条件
     * @return
     */
    PageBean<UserMessage> findByCondition(UserMessageQueryDto userMessageParam);

//    /**
//     * 条件查询，返回vo集
//     *
//     * @param userMessageParam 查询条件
//     * @return
//     */
//    PageBean<UserMessageVo> findVoListByCondition(UserMessageQuery userMessageParam);

    /**
     * 查询所有
     *
     * @return
     */
    List<UserMessage> findAll();   // 返回实体

//    /**
//     * 通过id查询，返回vo
//     *
//     * @param messageId 消息id
//     * @return
//     */
//    UserMessageVo findVoById(Integer messageId);

    /**
     * 通过id查询，返回po
     *
     * @param messageId 消息id
     * @return
     */
    UserMessage findById(Integer messageId);

    /**
     * 添加
     *
     * @param userMessage po
     */
    void add(UserMessage userMessage);

    /**
     * 批量添加
     *
     * @param list po集合
     */
    void batchAdd(List<UserMessage> list);

//    /**
//     * 添加
//     *
//     * @param userMessageDto dto
//     */
//    void dto4Add(UserMessageDto userMessageDto);

//    /**
//     * 批量添加
//     *
//     * @param list dto集合
//     */
//    void dto4BatchAdd(List<UserMessageDto> list);

    /**
     * 编辑
     *
     * @param userMessage po
     */
    void edit(UserMessage userMessage);

    /**
     * 批量编辑
     *
     * @param list po集合
     */
    void batchEdit(List<UserMessage> list);

//    /**
//     * 编辑
//     *
//     * @param userMessageDto dto
//     */
//    void dto4Edit(UserMessageDto userMessageDto);
//
//    /**
//     * 批量编辑
//     *
//     * @param list dto集合
//     */
//    void dto4BatchEdit(List<UserMessageDto> list);

    /**
     * 删除/批量删除
     *
     * @param list 消息id集合
     */
    void delete(List<Integer> list);
}
