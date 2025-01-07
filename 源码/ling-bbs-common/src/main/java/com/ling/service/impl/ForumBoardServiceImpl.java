package com.ling.service.impl;

import com.ling.entity.dto.ForumBoardQueryDto;
import com.ling.entity.po.ForumBoard;
import com.ling.mappers.ForumBoardMapper;
import com.ling.service.ForumBoardService;
import com.ling.utils.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumBoardServiceImpl implements ForumBoardService {
    private Logger log = LoggerFactory.getLogger(ForumBoardServiceImpl.class);

    @Resource
    private ForumBoardMapper forumBoardMapper;

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public List<ForumBoard> findByCondition(ForumBoardQueryDto forumBoardQueryDto) {
        List<ForumBoard> list = forumBoardMapper.selectByCondition(forumBoardQueryDto);
        return TreeUtil.listToTree("0", list, true);
    }

    /**
     * 查询所有
     */
    @Override
    public List<ForumBoard> findAll() {
        List<ForumBoard> list = forumBoardMapper.selectAll();
        return list.isEmpty() ? list : TreeUtil.listToTree("0", list);
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

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(ForumBoard forumBoard) {
        Date date = new Date();
        forumBoard.setCreateTime(date);
        forumBoard.setUpdateTime(date);
        forumBoardMapper.insert(forumBoard);
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
    public void edit(ForumBoard forumBoard) {
        Date date = new Date();
        forumBoard.setUpdateTime(date);
        forumBoardMapper.update(forumBoard);
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
}