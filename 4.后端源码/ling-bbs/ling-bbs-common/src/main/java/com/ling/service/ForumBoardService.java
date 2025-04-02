package com.ling.service;

import com.ling.entity.dto.query.ForumBoardQuery;
import com.ling.entity.po.ForumBoard;

import java.util.List;

public interface ForumBoardService {
    /**
     * 条件查询
     *
     * @param
     */
    List<ForumBoard> findByCondition(ForumBoardQuery forumBoardQueryDto);

    /**
     * 查询所有
     */
    List<ForumBoard> findAll();

    /**
     * 通过id查询
     *
     * @param
     */
    ForumBoard findById(String id);

    /**
     * 通过id查询
     *
     * @param
     */
    ForumBoard findById(Integer id);

    /**
     * 通过 pid && id查询
     * @param pid
     * @param id
     * @return
     */
    ForumBoard findByPidAndId(Integer pid, Integer id);

    /**
     * 添加
     *
     * @param
     */
    void add(ForumBoard forumBoard);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<ForumBoard> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(ForumBoard forumBoard);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<ForumBoard> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> ids);
}