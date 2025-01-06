package com.ling.mappers;

import com.ling.entity.dto.MessageQueryDto;
import com.ling.entity.po.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<Message> selectByCondition(MessageQueryDto messageQueryDto);

    /**
     * 查询所有
     */
    List<Message> selectAll();

    /**
     * 通过id查询
     *
     * @param
     */
    Message selectById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    Message selectById(Integer id);

    /**
     * 添加
     *
     * @param
     */
    void insert(Message message);

    /**
     * 批量添加
     *
     * @param
     */
    void batchInsert(List<Message> list);

    /**
     * 编辑
     *
     * @param
     */
    void update(Message message);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchUpdate(List<Message> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);
}
