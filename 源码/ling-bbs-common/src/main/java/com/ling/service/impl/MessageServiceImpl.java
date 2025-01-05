package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.entity.dto.MessageDto;
import com.ling.entity.dto.MessageQueryDto;
import com.ling.entity.po.Message;
import com.ling.entity.vo.PageBean;
import com.ling.exception.BusinessException;
import com.ling.mappers.MessageMapper;
import com.ling.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private MessageMapper messageMapper;

    /**
     * 条件查询
     * @param
     * */
    @Override
    public PageBean<Message> findByCondition(MessageQueryDto messageQueryDto) {
        PageHelper.startPage(messageQueryDto.getPage(), messageQueryDto.getPageSize());
        List<Message> list = messageMapper.selectByCondition(messageQueryDto);
        Page<Message> p = (Page<Message>) list;
        return PageBean.of(p.getTotal(), p.getPageNum(), p.getPageSize(), p.getPages(), p.getResult());
    }

    /**
     * 查询所有
     * */
    @Override
    public List<Message> findAll() {
        return messageMapper.selectAll();
    }

    /**
     * 通过id查询
     * @param
     * */
    @Override
    public Message findById(String id) {
        return messageMapper.selectById(id);
    }

    /**
     * 通过id查询
     * @param
     * */
    @Override
    public Message findById(Integer id) {
        return messageMapper.selectById(id);
    }

    /**
     * 添加
     * @param
     * */
    @Override
    public void add(Message message) {
        Date date = new Date();
        message.setCreateTime(date);
        message.setUpdateTime(date);
        messageMapper.insert(message);
    }

    /**
     * 添加
     * @param
     * */
//    @Override
    public void add1(MessageDto messageDto) {
        Message message = new Message();
        Date date = new Date();
        message.setCreateTime(date);
        message.setUpdateTime(date);
        BeanUtils.copyProperties(messageDto, message);
        messageMapper.insert(message);
    }

    /**
     * 批量添加
     * @param
     * */
    @Override
    public void batchAdd(List<Message> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setCreateTime(date);
            e.setUpdateTime(date);
        });
        messageMapper.batchInsert(list);
    }

    /**
     * 批量添加
     * @param
     * */
//    @Override
    public void batchAdd1(List<MessageDto> list) {
        Date date = new Date();
        List<Message> messages = list.stream().map(e -> {
            Message message = new Message();
            message.setCreateTime(date);
            message.setUpdateTime(date);
            BeanUtils.copyProperties(e, message);
            return message;
        }).collect(Collectors.toList());
        messageMapper.batchInsert(messages);
    }

    /**
     * 编辑
     * @param
     * */
    @Override
    public void edit(Message message) {
        Date date = new Date();
        message.setUpdateTime(date);
        messageMapper.update(message);
    }

    /**
     * 编辑
     * @param
     * */
    // @Override
    public void edit1(MessageDto messageDto) {
        Message message = new Message();
        Date date = new Date();
        message.setUpdateTime(date);
        BeanUtils.copyProperties(messageDto, message);
        messageMapper.update(message);
    }

    /**
     * 批量编辑
     * @param
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<Message> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setUpdateTime(date);
        });
        messageMapper.batchUpdate(list);
    }

    /**
     * 批量编辑
     * @param
     * */
    // @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit1(List<MessageDto> list) {
        Date date = new Date();
        List<Message> messages = list.stream().map(e -> {
            Message message = new Message();
            message.setUpdateTime(date);
            BeanUtils.copyProperties(e, message);
            return message;
        }).collect(Collectors.toList());
        messageMapper.batchUpdate(messages);
    }

//    /**
//     * 删除
//     * @param
//     * */
//    @Override
//    public void delete(List<String> list) {
//        messageMapper.delete(list);
//    }

    /**
     * 删除
     * @param
     * */
    @Override
    public void delete(List<Integer> list) {
        messageMapper.delete(list);
    }
}
