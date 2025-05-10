package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.constant.Constant;
import com.ling.entity.dto.session.SessionUserinfo;
import com.ling.entity.dto.query.ForumBoardQuery;
import com.ling.entity.po.ForumBoard;
import com.ling.entity.vo.BoardVo;
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
     * 加载板块接口
     * @return
     */
    @GetMapping
    public Result<List<BoardVo>> loadBoards() {
        return Result.success(forumBoardService.findVoTreeList());
    }

    /**
     * 发布文章获取板块接口
     *
     * @param session
     * @return
     */
    @GetMapping("/select")
    @AccessControl(loginRequired = true)
    public Result<List<BoardVo>> selectBoards(HttpSession session) {
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        ForumBoardQuery forumBoardQuery = new ForumBoardQuery();
        forumBoardQuery.setPostType(userinfo.getIsAdmin() ? null : Constant.NUM_1); // 根据用户过滤出发帖类型
        List<BoardVo> list = forumBoardService.findVoTreeListByCondition(forumBoardQuery);
        return Result.success(list);
    }

    /**
     * 通过id获取板块接口
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @AccessControl(loginRequired = true)
    public Result<ForumBoard> getForumBoardById(@PathVariable Integer id) {
        ForumBoard forumBoard = forumBoardService.findById(id);
        return Result.success(forumBoard);
    }
}
