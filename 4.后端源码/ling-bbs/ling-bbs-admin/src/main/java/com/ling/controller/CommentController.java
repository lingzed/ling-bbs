package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.constant.Constant;
import com.ling.entity.dto.query.CommentQuery;
import com.ling.entity.vo.CommentVo;
import com.ling.entity.vo.PageBean;
import com.ling.entity.vo.Result;
import com.ling.enums.CommentOrderEnum;
import com.ling.enums.PageSizeEnum;
import com.ling.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private CommentService commentService;

    /**
     * 加载评论列表接口
     *
     * @param page
     * @param pageSize
     * @param levelType
     * @param status
     * @param startPostTime
     * @param endPostTime
     * @return
     */
    @GetMapping
    @AccessControl(loginRequired = true)
    public Result<PageBean<CommentVo>> loadComments(@Validation(min = 1) Integer page,
                                                    @Validation Integer pageSize,
                                                    @Validation(max = 1, required = false) Integer levelType,
                                                    @Validation(max = 2, required = false) Integer status,
                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startPostTime,
                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endPostTime) {
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setPage(page);
        commentQuery.setPageSize(pageSize);
        commentQuery.setLevelType(levelType);
        commentQuery.setStatus(status);
        commentQuery.setStartPostTime(startPostTime);
        commentQuery.setEndPostTime(endPostTime);
        commentQuery.setOrderBy("post_time desc");

        PageBean<CommentVo> commentVo = commentService.findByCondition(commentQuery);
        return Result.success(commentVo);
    }

    /**
     * 加载1级评论树接口
     *
     * @param articleId
     * @param page
     * @return
     */
    @GetMapping("/{article-id}/l1")
    @AccessControl(loginRequired = true)
    public Result<PageBean<CommentVo>> loadL1Comments(@PathVariable("article-id") @Validation(max = 15) String articleId,
                                                      @Validation(min = 1) Integer page) {
        CommentQuery commentQueryDto = new CommentQuery();
        commentQueryDto.setpCommentId(Constant.NUM_0);
        commentQueryDto.setArticleId(articleId);
        commentQueryDto.setPage(page);
        commentQueryDto.setPageSize(PageSizeEnum.SIZE_10.getPageSize());    // 固定每页10条目
        commentQueryDto.setOrderBy(CommentOrderEnum.NEW.getOrderBy());  // 默认最新排序

        PageBean<CommentVo> commentVo = commentService.findL1AndL2Top10(commentQueryDto);

        return Result.success(commentVo);
    }

    /**
     * 加载2级评论列表接口
     *
     * @param articleId
     * @param pCommentId
     * @param page
     * @return
     */
    @GetMapping("/{article-id}/l2")
    @AccessControl(loginRequired = true)
    public Result<PageBean<CommentVo>> loadL2Comments(@PathVariable("article-id") @Validation(max = 15) String articleId,
                                                      @Validation(min = 1) Integer pCommentId,
                                                      @Validation(min = 1) Integer page) {
        CommentQuery commentQueryDto = new CommentQuery();
        commentQueryDto.setArticleId(articleId);
        commentQueryDto.setpCommentId(pCommentId);
        commentQueryDto.setPage(page);
        commentQueryDto.setOrderBy(CommentOrderEnum.HOT.getOrderBy());      // 默认最热排序
        commentQueryDto.setPageSize(PageSizeEnum.SIZE_10.getPageSize());    // 固定每页10条目

        PageBean<CommentVo> level2Comments = commentService.findL2Comment(commentQueryDto);

        return Result.success(level2Comments);
    }

    /**
     * 批量审核评论接口
     *
     * @param ids
     * @return
     */
    @PutMapping("/{ids}")
    @AccessControl(loginRequired = true)
    public Result<Void> auditComments(@PathVariable @Validation List<Integer> ids) {
        commentService.auditComments(ids);
        return Result.success();
    }

    /**
     * 批量删除评论接口
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @AccessControl(loginRequired = true)
    public Result<Void> delComments(@PathVariable @Validation List<Integer> ids) {
        commentService.delComments(ids);
        return Result.success();
    }
}
