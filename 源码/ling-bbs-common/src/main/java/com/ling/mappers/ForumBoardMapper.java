package com.ling.mappers;

import com.ling.entity.dto.query.ForumBoardQueryDto;
import com.ling.entity.po.ForumBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ForumBoardMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<ForumBoard> selectByCondition(ForumBoardQueryDto forumBoardQueryDto);

    /**
     * 查询所有
     */
    List<ForumBoard> selectAll();

    /**
     * 通过id查询
     *
     * @param
     */
    ForumBoard selectById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    ForumBoard selectById(Integer id);

    /**
     * 添加
     *
     * @param
     */
    void insert(ForumBoard forumBoard);

    /**
     * 批量添加
     *
     * @param
     */
    void batchInsert(List<ForumBoard> list);

    /**
     * 编辑
     *
     * @param
     */
    void update(ForumBoard forumBoard);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchUpdate(List<ForumBoard> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);
}