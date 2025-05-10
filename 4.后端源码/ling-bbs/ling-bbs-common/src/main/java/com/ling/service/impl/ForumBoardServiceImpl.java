package com.ling.service.impl;

import com.ling.commons.CommonMsg;
import com.ling.entity.dto.board.AddBoard;
import com.ling.entity.dto.board.EditBoard;
import com.ling.entity.dto.query.ForumBoardQuery;
import com.ling.entity.po.ForumBoard;
import com.ling.entity.vo.BoardVo;
import com.ling.exception.BusinessException;
import com.ling.mappers.ArticleMapper;
import com.ling.mappers.ForumBoardMapper;
import com.ling.service.ForumBoardService;
import com.ling.utils.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumBoardServiceImpl implements ForumBoardService {
    private static final Logger log = LoggerFactory.getLogger(ForumBoardServiceImpl.class);

    @Resource
    private ForumBoardMapper forumBoardMapper;
    @Resource
    private ArticleMapper articleMapper;

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public List<ForumBoard> findByCondition(ForumBoardQuery query) {
        return forumBoardMapper.selectByCondition(query);
    }

    /**
     * 条件查询，返回板块树形列表
     * @param query
     * @return
     */
    @Override
    public List<ForumBoard> findTreeListByCondition(ForumBoardQuery query) {
        return TreeUtil.listToTree(0, findByCondition(query));
    }

    /**
     * 条件查询，返回vo板块树形列表
     * @param query
     * @return
     */
    @Override
    public List<BoardVo> findVoTreeListByCondition(ForumBoardQuery query) {
        List<BoardVo> list = findByCondition(query).stream().map(b -> {
            BoardVo vo = new BoardVo();
            BeanUtils.copyProperties(b, vo);
            return vo;
        }).collect(Collectors.toList());
        return TreeUtil.listToTree(0, list);
    }

    /**
     * 返回板块树形列表
     * @return
     */
    @Override
    public List<ForumBoard> findTreeList() {
        return TreeUtil.listToTree(0, findAll());
    }

    /**
     * 返回vo板块树形列表
     * @return
     */
    @Override
    public List<BoardVo> findVoTreeList() {
        return findTreeList().stream().map(b -> {
            BoardVo vo = new BoardVo();
            BeanUtils.copyProperties(b, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询所有
     */
    @Override
    public List<ForumBoard> findAll() {
        return forumBoardMapper.selectAll();
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public ForumBoard findById(String id) {
        return forumBoardMapper.selectById(id);
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public ForumBoard findById(Integer id) {
        return forumBoardMapper.selectById(id);
    }

    @Override
    public ForumBoard findByPidAndId(Integer pid, Integer id) {
        return forumBoardMapper.selectByPidAndId(pid, id);
    }

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(AddBoard addBoard) {
        ForumBoard board = findById(addBoard.getPBoardId());
        if (board == null) {
            throw new BusinessException(CommonMsg.P_BOARD_NOT_EXISTS);
        }
        forumBoardMapper.insert(addBoard);
    }

    /**
     * 批量添加
     *
     * @param
     */
    @Override
    public void batchAdd(List<ForumBoard> list) {
        List<ForumBoard> forumBoards = list.stream().peek(e -> {
            Date date = new Date();
            e.setCreateTime(date);
            e.setUpdateTime(date);
        }).collect(Collectors.toList());
        forumBoardMapper.batchInsert(forumBoards);
    }

    /**
     * 编辑
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(EditBoard editBoard) {
        ForumBoard board = findById(editBoard.getBoardId());
        if (board == null) {
            throw new BusinessException(CommonMsg.BOARD_NOT_EXISTS);
        }

        forumBoardMapper.update(editBoard);     // 更新板块

        // 更新文章板块名称
        if (!board.getBoardName().equals(editBoard.getBoardName())) {
            articleMapper.updateBoardName(editBoard.getBoardId(), editBoard.getBoardName());

            // 若为父级板块，则同时更新子级板块的父级板块名称
            boolean isParent = board.getPBoardId().equals(0);
            if (isParent) {
                articleMapper.updateSubPBoardName(editBoard.getBoardId(), editBoard.getBoardName());
            }
        }
    }


    /**
     * 批量编辑
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<ForumBoard> list) {
        List<ForumBoard> forumBoards = list.stream().peek(e -> {
            Date date = new Date();
            e.setUpdateTime(date);
        }).collect(Collectors.toList());
        forumBoardMapper.batchUpdate(forumBoards);
    }

    /**
     * 删除
     *
     * @param
     */
    public void delete(List<Integer> ids) {
        forumBoardMapper.delete(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delBoard(Integer id) {
        ForumBoard board = findById(id);
        if (board == null) return;

        boolean isParent = board.getPBoardId().equals(0);

        // 删除板块
        forumBoardMapper.deleteBoard(id, isParent);

        // 同时删除板块下的文章
        articleMapper.updateToDeleteByBoard(id, isParent);
    }
}