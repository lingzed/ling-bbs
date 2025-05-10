package com.ling.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
//import com.ling.entity.dto.UserMessageDto;
import com.ling.config.AdminConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.query.UserMessageQuery;
import com.ling.entity.po.Comment;
import com.ling.entity.po.UserMessage;
//import com.ling.entity.vo.UserMessageVo;
import com.ling.entity.vo.*;
import com.ling.enums.MessageStatusEnum;
import com.ling.enums.MessageTypeEnum;
import com.ling.enums.ResponseCodeEnum;
import com.ling.enums.WSMessageTypeEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.CommentMapper;
import com.ling.mappers.UserMessageMapper;
import com.ling.service.UserMessageService;
import com.ling.service.websocket.WebSocketServer;
import com.ling.utils.OkHttpUtil;
import com.ling.utils.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    private static final Logger log = LoggerFactory.getLogger(UserMessageServiceImpl.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Resource
    private UserMessageMapper userMessageMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private WebSocketServer webSocketServer;
    @Resource
    private AdminConfig adminConfig;

    @Override
    public PageBean<UserMessage> findByCondition(UserMessageQuery userMessageParam) {
        PageHelper.startPage(userMessageParam.getPage(), userMessageParam.getPageSize());
        List<UserMessage> list = userMessageMapper.selectByCondition(userMessageParam);
        Page<UserMessage> p = (Page<UserMessage>) list;
        return PageBean.of(p.getTotal(),
                p.getPageNum(),
                p.getPageSize(),
                p.getPages(),
                p.getResult());
    }

    @Override
    public PageBean<MessageListVo> findVoPageByCondition(UserMessageQuery query) {
        List<MessageListVo> rows = userMessageMapper.selectByCondition(query).stream().map(e -> {
            MessageListVo messageListVo = new MessageListVo();
            BeanUtils.copyProperties(e, messageListVo);
            return messageListVo;
        }).collect(Collectors.toList());
        Long total = countByCondition(query);
        return PageBean.of(total, query.getPage(), query.getPageSize(), rows);
    }

    @Override
    public Long countByCondition(UserMessageQuery userMessageParam) {
        return userMessageMapper.countByCondition(userMessageParam);
    }

    @Override
    public MessageDetailVo loadMessageDetail(Integer messageId, String userId) {
        UserMessage message = findById(messageId);
        if (Objects.isNull(message))
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        if (!Objects.equals(message.getReceivedUserId(), userId))
            throw new BusinessException(CommonMsg.UNAUTHORIZED_ACCESS);

        MessageDetailVo messageDetailVo = new MessageDetailVo();
        BeanUtils.copyProperties(message, messageDetailVo);
        if (Objects.equals(MessageTypeEnum.COMMENT.getType(), message.getMessageType())
                || Objects.equals(MessageTypeEnum.COMMENT_LIKE.getType(), message.getMessageType())) {
            Comment comment = commentMapper.selectById(message.getCommentId());
            messageDetailVo.setCommentContent(comment.getContent());
        }

        // 若未读，则更新为已读
        if (Objects.equals(message.getStatus(), MessageStatusEnum.NO_READ.getStatus())) {
            UserMessage userMessage = new UserMessage();
            userMessage.setMessageId(message.getMessageId());
            userMessage.setStatus(MessageStatusEnum.READ.getStatus());
            edit(userMessage);
            try {
                // 通知前端重新加载未读消息
                String jsonString = OBJECT_MAPPER.writeValueAsString(WSMessage.ofWebServer(WSMessageTypeEnum.LOAD_UNREAD));
                webSocketServer.sendMessageToUser(userId, jsonString);
            } catch (IOException e) {
                throw new BusinessException(CommonMsg.WS_MESSAGE_SEND_FAIL, e);
            }
        }

        return messageDetailVo;
    }

    @Override
    public List<UserMessage> findAll() {
        return userMessageMapper.selectAll();
    }

    @Override
    public UserMessage findById(Integer id) {
        return userMessageMapper.selectById(id);
    }


    @Override
    public void add(UserMessage userMessage) {
        userMessageMapper.insert(repairUserMessage(userMessage));
    }

    @Override
    public void add(String userId, UserMessage userMessage) {
        add(userMessage);
        try {
            // 通知前端重新加载未读消息
            String jsonString = OBJECT_MAPPER.writeValueAsString(WSMessage.ofWebServer(WSMessageTypeEnum.LOAD_UNREAD));
            webSocketServer.sendMessageToUser(userId, jsonString);
        } catch (IOException e) {
            throw new BusinessException(CommonMsg.WS_MESSAGE_SEND_FAIL, e);
        }
    }

    @Override
    public void batchAdd(List<UserMessage> list) {
        List<UserMessage> newList = list.stream().map(this::repairUserMessage).collect(Collectors.toList());
        userMessageMapper.batchInsert(newList);
    }

    private UserMessage repairUserMessage(UserMessage userMessage) {
        Date date = new Date();
        userMessage.setCreateTime(date);
        userMessage.setUpdateTime(date);
        return userMessage;
    }

    @Override
    public void edit(UserMessage userMessage) {
        Date date = new Date();
        userMessage.setUpdateTime(date);
        userMessageMapper.update(userMessage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<UserMessage> list) {
        List<UserMessage> newList = list.stream().peek(e -> {
            Date date = new Date();
            e.setUpdateTime(date);
        }).collect(Collectors.toList());
        userMessageMapper.batchUpdate(newList);
    }

    @Override
    public void delete(List<Integer> list) {
        userMessageMapper.delete(list);
    }

    @Override
    public void delete(String userId, List<Integer> list) {
        Long total = userMessageMapper.countByReceiverAndIds(userId, list);
        if (Objects.equals(total, (long) Constant.NUM_0))
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        delete(list);
    }

    @Override
    public void requestPushNotice(String receiver) {
        long currentTime = System.currentTimeMillis();
        String sign = StrUtil.generateHmacSha256Hex(adminConfig.getSecretKey(), receiver + currentTime);
        String url = adminConfig.getInnerUrl() + "/send-msg?userId=" + receiver + "&timestamp=" + currentTime + "&sign=" + sign;
        String result = OkHttpUtil.getRequest(url);
        log.info("响应结果: {}", result);
    }
}
