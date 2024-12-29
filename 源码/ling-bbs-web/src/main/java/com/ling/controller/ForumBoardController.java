package com.ling.controller;

import com.ling.entity.dto.ForumBoardQueryDto;
import com.ling.entity.po.ForumBoard;
import com.ling.entity.vo.Result;
import com.ling.service.ForumBoardService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/forumBoard")
public class ForumBoardController {
    @Resource
    private ForumBoardService forumBoardService;

    @GetMapping
    public Result<List<ForumBoard>> getForumBoards(Integer boardId, Integer pBoardId, String boardName, Integer postType) {
        ForumBoardQueryDto forumBoardQueryDto = new ForumBoardQueryDto();
        forumBoardQueryDto.setBoardId(boardId);
        forumBoardQueryDto.setBoardName(boardName);
        forumBoardQueryDto.setPBoardId(pBoardId);
        forumBoardQueryDto.setPostType(postType);
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
