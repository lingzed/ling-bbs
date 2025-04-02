package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.constant.Constant;
import com.ling.entity.dto.session.SessionUserinfo;
import com.ling.entity.dto.query.CommentQuery;
import com.ling.entity.dto.query.LikeRecordQuery;
import com.ling.entity.dto.query.UserPointsRecordQuery;
import com.ling.entity.po.UserInfo;
import com.ling.entity.vo.*;
import com.ling.enums.RegexEnum;
import com.ling.enums.ResponseCodeEnum;
import com.ling.enums.UserStatusEnum;
import com.ling.exception.BusinessException;
import com.ling.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 用户中心控制器
 */
@RestController
@RequestMapping("/user-center")
public class UserCenterController {
    private static final Logger log = LoggerFactory.getLogger(UserCenterController.class);
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ArticleService articleService;
    @Resource
    private LikeRecordService likeRecordService;
    @Resource
    private CommentService commentService;
    @Resource
    private UserPointsRecordService userPointsRecordService;
    @Resource
    private MailCodeService mailCodeService;

    // 从session中获取用户信息
    private SessionUserinfo getUserinfo(HttpSession session) {
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        return userinfo;
    }

    // 校验userId
    private UserInfo checkUserId(String userId) {
        UserInfo userinfo = userInfoService.findById(userId);
        if (Objects.isNull(userinfo) || Objects.equals(UserStatusEnum.DISABLE.getStatus(), userinfo.getStatus()))
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        return userinfo;
    }

    /**
     * 加载个人中心个人信息接口
     *
     * @param userId
     * @return
     */
    @GetMapping("/{user-id}")
    @AccessControl
    public Result<UserinfoVO> loadUserinfo(@PathVariable("user-id") @Validation(max = 10) String userId) {
        UserInfo userinfo = checkUserId(userId);

        UserinfoVO userinfoVO = new UserinfoVO();
        BeanUtils.copyProperties(userinfo, userinfoVO);
        userinfoVO.setCurrentPoints(userinfo.getCurrentIntegral());
        LikeRecordQuery likeRecordQuery = new LikeRecordQuery();
        likeRecordQuery.setTargetAuthorId(userId);
        Long receiveLikeTotal = likeRecordService.findTotalByCondition(likeRecordQuery); // 接受点赞的数量
        Long postTotal = articleService.countByUserAndAudit(userId);  // 发文数量, 仅统计已审核文章
        userinfoVO.setPostCount(postTotal);
        userinfoVO.setReceiveLikeCount(receiveLikeTotal);

        return Result.success(userinfoVO);
    }

    /**
     * 加载个人中心发文列表接口
     *
     * @param userId
     * @return
     */
    @GetMapping("/{user-id}/posts")
    @AccessControl
    public Result<List<ArticleVo>> loadUserPostList(HttpSession session,
                                                    @PathVariable("user-id") @Validation(max = 10) String userId) {
        checkUserId(userId);

        SessionUserinfo userinfo = getUserinfo(session);
        // 作者 && 管理员 能看到待审核文章，其用户 || 未登录 只能看到已审核文章
        boolean showPadding = !Objects.isNull(userinfo)
                && (userinfo.getIsAdmin() || Objects.equals(userId, userinfo.getUserId()));
        List<ArticleVo> articleVos = articleService.findListByUserAndStatus(userId, showPadding);
        return Result.success(articleVos);
    }

    /**
     * 加载个人中心点赞记录列表接口
     *
     * @param userId
     * @return
     */
    @GetMapping("/{user-id}/like-record")
    @AccessControl
    public Result<List<LikeRecordVo>> loadUserLikeRecords(@PathVariable("user-id") @Validation(max = 10) String userId) {
        checkUserId(userId);
        List<LikeRecordVo> likeRecordVos = likeRecordService.findLikeHistory(userId);
        return Result.success(likeRecordVos);
    }

    /**
     * 加载个人中心发评列表接口
     *
     * @param userId
     * @return
     */
    @GetMapping("/{user-id}/comment-record")
    public Result<List<CommentVo>> loadCommentRecords(@PathVariable("user-id") @Validation(max = 10) String userId) {
        checkUserId(userId);

        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setSenderId(userId);
        commentQuery.setOrderBy("post_time desc");
        List<CommentVo> commentVos = commentService.findVoListByCondition(commentQuery);

        return Result.success(commentVos);
    }

    /**
     * 加载用户积分记录接口
     *
     * @param session
     * @param page
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/points-record")
    @AccessControl(loginRequired = true)
    public Result<PageBean<PointsRecordVo>> loadPointsRecordsHandle(HttpSession session,
                                                                    @Validation(min = 1) Integer page,
                                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        page = Objects.isNull(page) ? 1 : page;
        SessionUserinfo sUserinfo = getUserinfo(session);
        UserPointsRecordQuery query = new UserPointsRecordQuery();
        query.setUserId(sUserinfo.getUserId());
        query.setPage(page);
        query.setPageSize(Constant.NUM_10);
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        query.setOrderBy("create_time desc");

        PageBean<PointsRecordVo> resultPage = userPointsRecordService.findVoPageByCondition(query);

        return Result.success(resultPage);
    }

    /**
     * 更新用户信息接口
     *
     * @param session
     * @param nickname
     * @param avatar
     * @param gender
     * @param description
     * @return
     */
    @PutMapping
    @AccessControl(loginRequired = true)
    public Result<Map<String, Object>> resetUserinfoHandle(HttpSession session,
                                                           @Validation String nickname,
                                                           String avatar,
                                                           @Validation(max = 2) Integer gender,
                                                           @Validation(required = false, max = 100) String description) {
        SessionUserinfo sUserinfo = getUserinfo(session);
        UserInfo ui = new UserInfo();
        ui.setUserId(sUserinfo.getUserId());
        ui.setNickName(nickname);
        String resAvatar = sUserinfo.getAvatar();
        boolean keepAvatar = Objects.equals(resAvatar, avatar);
        ui.setAvatar(avatar);
        ui.setGender(gender);
        ui.setDescription(description);
        ui.setAdmin(sUserinfo.getIsAdmin());

        userInfoService.processResetUserinfo(ui, keepAvatar);   // 更新用户信息

        // 更新会话信息
        if (!keepAvatar) sUserinfo.setAvatar(avatar);
        sUserinfo.setNickName(ui.getNickName());

        Map<String, Object> result = new HashMap<>();
        result.put("userId", sUserinfo.getUserId());
        result.put("nickname", nickname);
        result.put("avatar", keepAvatar ? resAvatar : avatar);
        result.put("gender", gender);
        result.put("description", description);
        return Result.success(result);
    }

    /**
     * 发送邮箱验证码接口
     *
     * @param session
     * @return
     */
    @GetMapping("/sendmail")
    @AccessControl(loginRequired = true)
    public Result<Void> sendMail(HttpSession session) {
        SessionUserinfo userinfo = getUserinfo(session);
        String mail = userinfo.getMail();
        mailCodeService.sendMailCode(mail);
        return Result.success();
    }

    /**
     * 重置密码接口
     *
     * @param session
     * @param mailCode
     * @param password
     * @return
     */
    @PutMapping("/reset-pwd")
    @AccessControl(loginRequired = true)
    public Result<Void> resetPassword(HttpSession session,
                                      @Validation String mailCode,
                                      @Validation(regex = RegexEnum.PWD_MEDIUM) String password) {
        SessionUserinfo userinfo = getUserinfo(session);
        userInfoService.resetPwd(userinfo.getMail(), password, mailCode);
        return Result.success();
    }
}
