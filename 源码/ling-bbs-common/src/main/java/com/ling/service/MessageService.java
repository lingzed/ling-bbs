
package com.ling.service;

import com.ling.entity.dto.MessageQueryDto;
import com.ling.entity.po.Message;
import com.ling.entity.vo.PageBean;

import java.util.List;

public interface MessageService {
    /**
     * 条件查询
     *
     * @param
     */
    PageBean<Message> findByCondition(MessageQueryDto messageQueryDto);

    /**
     * 查询所有
     */
    List<Message> findAll();

    /**
     * 通过id查询
     *
     * @param
     */
    Message findById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    Message findById(Integer id);

    /**
     * 添加
     *
     * @param
     */
    void add(Message messageDto);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<Message> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(Message messageDto);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<Message> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);
}
