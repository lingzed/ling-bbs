package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
import com.ling.constant.Constant;
import com.ling.entity.dto.SessionUserinfo;
import com.ling.entity.dto.query.UserMessageQuery;
import com.ling.entity.po.UserMessage;
import com.ling.entity.vo.MessageDetailVo;
import com.ling.entity.vo.MessageListVo;
import com.ling.entity.vo.PageBean;
import com.ling.entity.vo.Result;
import com.ling.enums.MessageStatusEnum;
import com.ling.service.UserMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/message")
@RestController
public class MessageController {
    private Logger log = LoggerFactory.getLogger(MessageController.class);
    @Resource
    private UserMessageService userMessageService;

    // 从session中获取用户信息
    private SessionUserinfo getUserinfo(HttpSession session) {
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        return userinfo;
    }

    /**
     * 统计未读消息数量的接口
     *
     * @param session
     * @return
     */
    @GetMapping("/count")
    @AccessControl(loginRequired = true)
    public Result<Map<String, Long>> loadMessageCount(HttpSession session) {
        SessionUserinfo userinfo = getUserinfo(session);
        UserMessageQuery query = new UserMessageQuery();
        query.setReceivedUserId(userinfo.getUserId());
        query.setStatus(MessageStatusEnum.NO_READ.getStatus());
        Long total = userMessageService.countByCondition(query);
        HashMap<String, Long> res = new HashMap<>();
        res.put("msgCount", total);
        return Result.success(res);
    }

    /**
     * 加载消息列表接口
     *
     * @param session
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping
    @AccessControl(loginRequired = true)
    public Result<PageBean<MessageListVo>> loadMessageList(HttpSession session,
                                                           @Validation(min = 1) Integer page,
                                                           @Validation Integer pageSize,
                                                           @Validation(required = false, max = 4) Integer messageType,
                                                           @Validation(max = 1, required = false) Integer status,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                           @Validation(required = false) Date startDate,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                           @Validation(required = false) Date endDate) {
        SessionUserinfo userinfo = getUserinfo(session);
        UserMessageQuery query = new UserMessageQuery();
        query.setReceivedUserId(userinfo.getUserId());
        query.setPage(page);
        query.setPageSize(pageSize);
        query.setMessageType(messageType);
        query.setStatus(status);
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        query.setOrderBy("create_time desc");
        PageBean<MessageListVo> voPageByCondition = userMessageService.findVoPageByCondition(query);
        return Result.success(voPageByCondition);
    }

    /**
     * 加载消息详情接口
     *
     * @param session
     * @param messageId
     * @return
     */
    @GetMapping("/{message-id}")
    @AccessControl(loginRequired = true)
    public Result<MessageDetailVo> loadMessageDetail(HttpSession session,
                                                     @PathVariable("message-id") @Validation Integer messageId) {
        SessionUserinfo userinfo = getUserinfo(session);
        MessageDetailVo messageDetailVo = userMessageService.loadMessageDetail(messageId, userinfo.getUserId());
        return Result.success(messageDetailVo);
    }

    /**
     * 删除消息接口
     *
     * @param session
     * @param messageIds
     * @return
     */
    @DeleteMapping("/{message-ids}")
    @AccessControl(loginRequired = true)
    public Result<Void> delMessage(HttpSession session, @PathVariable("message-ids") List<Integer> messageIds) {
        SessionUserinfo userinfo = getUserinfo(session);
        userMessageService.delete(userinfo.getUserId(), messageIds);
        return Result.success();
    }
}
