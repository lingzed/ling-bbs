package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.constant.Constant;
import com.ling.entity.dto.session.SessionUserinfo;
import com.ling.entity.dto.query.ForumBoardQuery;
import com.ling.entity.po.ForumBoard;
import com.ling.entity.vo.Result;
import com.ling.service.ForumBoardService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 板块控制器
 */
@RestController
@RequestMapping("/board")
public class ForumBoardController {
    @Resource
    private ForumBoardService forumBoardService;

    /**
     * 获取板块接口
     *
     * @param boardId
     * @param pBoardId
     * @param boardName
     * @param postType
     * @return
     */
    @GetMapping
    public Result<List<ForumBoard>> getForumBoards(Integer boardId, Integer pBoardId, String boardName, Integer postType) {
        ForumBoardQuery forumBoardQueryDto = new ForumBoardQuery();
        forumBoardQueryDto.setBoardId(boardId);
        forumBoardQueryDto.setBoardName(boardName);
        forumBoardQueryDto.setPBoardId(pBoardId);
        forumBoardQueryDto.setPostType(postType);
        List<ForumBoard> list = forumBoardService.findByCondition(forumBoardQueryDto);
        return Result.success(list);
    }

    /**
     * 发布文章获取板块接口
     *
     * @param session
     * @return
     */
    @GetMapping("/select")
    @AccessControl(loginRequired = true)
    public Result<List<ForumBoard>> selectBoards(HttpSession session) {
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        ForumBoardQuery forumBoardQueryDto = new ForumBoardQuery();
        forumBoardQueryDto.setPostType(userinfo.getIsAdmin() ? null : Constant.NUM_1);
        List<ForumBoard> list = forumBoardService.findByCondition(forumBoardQueryDto);
        return Result.success(list);
    }

    @GetMapping("/{id}")
    public Result<ForumBoard> getForumBoardById(@PathVariable Integer id) {
        ForumBoard forumBoard = forumBoardService.findById(id);
        return Result.success(forumBoard);
    }

    @GetMapping("/all")
    public Result<List<ForumBoard>> getAll() {
        List<ForumBoard> list = forumBoardService.findAll();
        return Result.success(list);
    }

    @PostMapping
    public Result addForumBoard(@RequestBody ForumBoard forumBoard) {
        forumBoardService.add(forumBoard);
        return Result.success();
    }

    @PostMapping("/batch")
    public Result batchAddForumBoard(@RequestBody List<ForumBoard> list) {
        forumBoardService.batchAdd(list);
        return Result.success();
    }

    @PutMapping
    public Result editForumBoard(@RequestBody ForumBoard forumBoard) {
        forumBoardService.edit(forumBoard);
        return Result.success();
    }

    @PutMapping("/batch")
    public Result batchEditForumBard(@RequestBody List<ForumBoard> list) {
        forumBoardService.batchEdit(list);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result deleteForumBards(@PathVariable List<Integer> ids) {
        forumBoardService.delete(ids);
        return Result.success();
    }


}
