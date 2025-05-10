package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.entity.dto.board.AddBoard;
import com.ling.entity.dto.board.EditBoard;
import com.ling.entity.dto.query.ForumBoardQuery;
import com.ling.entity.po.ForumBoard;
import com.ling.entity.vo.BoardVo;
import com.ling.entity.vo.Result;
import com.ling.service.ForumBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 板块接口
 */
@RestController
@RequestMapping("/board")
public class BoardController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    @Resource
    private ForumBoardService forumBoardService;

    /**
     * 条件查询板块接口
     * @param query
     * @return
     */
    @GetMapping
    @AccessControl(loginRequired = true)
    public Result<List<BoardVo>> loadBoards(@RequestBody @Validation ForumBoardQuery query) {
        List<BoardVo> boards = forumBoardService.findVoTreeListByCondition(query);
        return Result.success(boards);
    }

    /**
     * 添加板块接口
     * @param board
     */
    @PostMapping
    @AccessControl(loginRequired = true)
    public Result<Void> addBoard(@RequestBody @Validation AddBoard board) {
        forumBoardService.add(board);
        return Result.success();
    }

    /**
     * 更新板块接口
     * @param board
     */
    @PutMapping
    @AccessControl(loginRequired = true)
    public Result<Void> editBoard(@RequestBody @Validation EditBoard board) {
        forumBoardService.edit(board);
        return Result.success();
    }

    /**
     * 删除板块接口
     * @param boardId
     */
    @DeleteMapping("/{boardId}")
    @AccessControl(loginRequired = true)
    public Result<Void> delBoard(@PathVariable @Validation(min = 1) Integer boardId) {
        forumBoardService.delBoard(boardId);
        return Result.success();
    }
}
