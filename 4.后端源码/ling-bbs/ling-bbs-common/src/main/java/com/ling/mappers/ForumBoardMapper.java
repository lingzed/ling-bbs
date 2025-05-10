package com.ling.mappers;

import com.ling.entity.dto.board.AddBoard;
import com.ling.entity.dto.board.EditBoard;
import com.ling.entity.dto.query.ForumBoardQuery;
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
    List<ForumBoard> selectByCondition(ForumBoardQuery forumBoardQueryDto);

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
     * 通过 pid && id查询
     * @param pid
     * @param id
     * @return
     */
    ForumBoard selectByPidAndId(Integer pid, Integer id);

    /**
     * 添加
     *
     * @param
     */
    void insert(AddBoard addBoard);

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
    void update(EditBoard editBoard);

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

    /**
     * 删除板块
     * @param id
     */
    void deleteBoard(Integer bId, Boolean isParent);
}