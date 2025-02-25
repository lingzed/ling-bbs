package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.commons.CommonMsg;
import com.ling.config.WebConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.dto.query.CommentQueryDto;
import com.ling.entity.po.Comment;
import com.ling.entity.vo.*;
import com.ling.enums.CommentOrderEnum;
import com.ling.enums.PageSizeEnum;
import com.ling.enums.ResponseCodeEnum;
import com.ling.enums.TargetStatusEnum;
import com.ling.exception.BusinessException;
import com.ling.service.ArticleService;
import com.ling.service.CommentService;
import com.ling.utils.SysCacheUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("comments")
public class CommentController {
    private Logger log = LoggerFactory.getLogger(Comment.class);
    @Resource
    private CommentService commentService;
    @Resource
    private WebConfig webConfig;

    /**
     * 查询1级评论和1级评论下2级评论前10条接口
     *
     * @param session
     * @param articleId
     * @param orderType
     * @param page
     * @return
     */
    @GetMapping("/{articleId}/{orderType}/{page}")
    @AccessControl
    public Result<PageBean<CommentVo>> loadComments(HttpSession session,
                                                    @PathVariable @Validation(max = 15) String articleId,
                                                    @PathVariable @Validation(max = 1) Integer orderType,
                                                    @PathVariable @Validation Integer page) {
        boolean openComment = SysCacheUtil.getSysSettingContainer().getSysSetting4Comment().isOpenComment();
        if (!openComment)   // 评论开启才能访问
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        UserInfoSessionDto userinfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        CommentQueryDto commentQueryDto = new CommentQueryDto();
        commentQueryDto.setpCommentId(Constant.NUM_0);
        commentQueryDto.setArticleId(articleId);
        commentQueryDto.setPage(page == 0 ? Constant.NUM_1 : page);
        commentQueryDto.setPageSize(PageSizeEnum.SIZE_10.getPageSize());    // 固定每页10条目
        commentQueryDto.setOrderBy(CommentOrderEnum.getCommentOrder(orderType).getOrderBy());
        commentQueryDto.setUserId(userinfo == null ? null : userinfo.getUserId());
        commentQueryDto.setAdmin(userinfo != null && userinfo.getIsAdmin());
        PageBean<CommentVo> comments = commentService.findLeve1andLeve2Top10(commentQueryDto);
        return Result.success(comments);
    }

    /**
     * 查询2级评论接口
     *
     * @param session
     * @param page
     * @param pCommentId
     * @return
     */
    @GetMapping("/l2/{articleId}/{pCommentId}/{page}")
    @AccessControl
    public Result<PageBean<CommentVo>> loadL2Comments(HttpSession session,
                                                      @PathVariable @Validation(max = 15) String articleId,
                                                      @PathVariable @Validation(min = 1) Integer pCommentId,
                                                      @PathVariable @Validation Integer page) {
        boolean openComment = SysCacheUtil.getSysSettingContainer().getSysSetting4Comment().isOpenComment();
        if (!openComment)   // 评论开启才能访问
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        UserInfoSessionDto userinfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        CommentQueryDto commentQueryDto = new CommentQueryDto();
        commentQueryDto.setArticleId(articleId);
        commentQueryDto.setpCommentId(pCommentId);
        commentQueryDto.setOrderBy(CommentOrderEnum.HOT.getOrderBy());
        commentQueryDto.setPage(page == 0 ? Constant.NUM_1 : page);
        commentQueryDto.setPageSize(PageSizeEnum.SIZE_10.getPageSize());    // 固定每页10条目
        commentQueryDto.setUserId(userinfo == null ? null : userinfo.getUserId());
        commentQueryDto.setAdmin(userinfo != null && userinfo.getIsAdmin());
        PageBean<CommentVo> level2Comments = commentService.findByCondition(commentQueryDto);
        return Result.success(level2Comments);
    }

    /**
     * 评论点赞接口
     *
     * @param session
     * @param commentId
     * @return
     */
    @PostMapping("/like/{commentId}")
    @AccessControl(loginRequired = true)
    public Result<CommentLikeInfo> commentLikeHandle(HttpSession session, @PathVariable @Validation Integer commentId) {
        UserInfoSessionDto userInfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        commentService.processCommentLike(commentId, userInfo);
        // 返回被点赞的评论最新的点赞量和点赞状态，实时更新，减少不必要的请求
        CommentLikeInfo commentLikeInfo = commentService.findCommentLikeInfo(commentId, userInfo.getUserId());
        return Result.success(commentLikeInfo);
    }

    /**
     * 评论置顶接口
     *
     * @param session
     * @param commentId
     * @param topType
     * @return
     */
    @PostMapping("/top/{commentId}/{topType}")
    @AccessControl(loginRequired = true)
    public Result<Void> commentTopHandle(HttpSession session, @PathVariable @Validation Integer commentId,
                                         @PathVariable @Validation(max = 1) Integer topType) {
        UserInfoSessionDto userInfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        commentService.processCommentTop(userInfo, commentId, topType);
        return Result.success();
    }

    /**
     * 发送1级评论接口
     *
     * @param session
     * @param articleId
     * @param content
     * @param imgContent
     * @return
     */
    @PostMapping("/post/l1")
    @AccessControl(loginRequired = true)
    public Result<CommentVo> postL1CommentHandle(HttpSession session,
                                                 @Validation(max = 15) String articleId,
                                                 @Validation(required = false, max = 200) String content,
                                                 @Validation(required = false, max = 150) String imgContent) {
        preCheck(content, imgContent);
        UserInfoSessionDto userinfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        Comment comment = createComment(userinfo, articleId, Constant.NUM_0, null, content, imgContent);
        CommentVo commentVo = commentService.processPostL1Comment(comment, isAuditComment());
        return Result.success(commentVo);
    }

    /**
     * 发送2级评论接口
     *
     * @param session
     * @param articleId
     * @param pCommentId
     * @param content
     * @param receiverId
     * @param imgContent
     * @return
     */
    @PostMapping("/post/l2")
    @AccessControl(loginRequired = true)
    public Result<CommentVo> postL2CommentHandle(HttpSession session,
                                                 @Validation(max = 15) String articleId,
                                                 @Validation(min = 1) Integer pCommentId,
                                                 @Validation(required = false, max = 200) String content,
                                                 String receiverId,
                                                 @Validation(required = false, max = 150) String imgContent) {
        preCheck(content, imgContent);
        UserInfoSessionDto userinfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        Comment comment = createComment(userinfo, articleId, pCommentId, receiverId, content, imgContent);
        CommentVo commentVo = commentService.processPostL2Comment(comment, isAuditComment());
        return Result.success(commentVo);
    }

    // 是否审核评论
    private boolean isAuditComment() {
        return SysCacheUtil.getSysSettingContainer().getSysSetting4Audit().isCommentAudit();
    }

    // 预检
    private void preCheck(String content, String imgContent) {
        boolean isOpenComment = SysCacheUtil.getSysSettingContainer().getSysSetting4Comment().isOpenComment();
        // 判断评论是否开启
        if (!isOpenComment)
            throw new BusinessException(CommonMsg.UNOPENED_COMMENT);
        // 评论内容或图片至少要有一个
        if (Objects.isNull(content) && Objects.isNull(imgContent))
            throw new BusinessException(ResponseCodeEnum.CODE_600);
    }

    // 构建comment
    private Comment createComment(UserInfoSessionDto userinfo, String articleId, Integer pCommentId, String receiverId,
                                  String content, String imgContent) {
        Comment comment = new Comment();
        comment.setpCommentId(pCommentId);
        comment.setArticleId(articleId);
        comment.setImgPath(imgContent);
        comment.setContent(StringEscapeUtils.escapeHtml4(content)); // 转义html，防止xss攻击
        comment.setSenderAvatar(userinfo.getAvatar());
        comment.setSenderId(userinfo.getUserId());
        comment.setSenderNickname(userinfo.getNickName());
        comment.setSenderIpAddress(userinfo.getProvince());
        comment.setReceiverId(receiverId);
        // 评论默认未审核，因此只当设置不需要审核评论时，将状态设为已审核
        Integer status = isAuditComment() ? TargetStatusEnum.PENDING.getStatus() : TargetStatusEnum.AUDITED.getStatus();
        comment.setStatus(status);
        comment.setLikeCount(Constant.NUM_0);
        comment.setTopType(Constant.NUM_0);
        comment.setPostTime(new Date());
        return comment;
    }
}
