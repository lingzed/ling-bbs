package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ling.commons.CommonMsg;
import com.ling.config.WebConfig;
import com.ling.entity.constant.Constant;
import com.ling.entity.po.MailCode;
import com.ling.entity.po.UserInfo;
import com.ling.entity.vo.PageBean;
import com.ling.exception.BusinessException;
import com.ling.mappers.MailCodeMapper;
import com.ling.mappers.UserInfoMapper;
import com.ling.service.MailCodeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailCodeServiceImpl implements MailCodeService {

    private Logger log = LoggerFactory.getLogger(MailCodeServiceImpl.class);

    @Resource
    private MailCodeMapper mailCodeMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private WebConfig webConfig;

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public PageBean<MailCode> find(MailCode mailCode) {
        try {
            PageHelper.startPage(mailCode.getPage(), mailCode.getPageSize());
            List<MailCode> mailCodes = mailCodeMapper.select(mailCode);
            Page<MailCode> p = (Page<MailCode>) mailCodes;
            return PageBean.of(p.getTotal(), mailCode.getPage(), p.getPageSize(), p.getPages(), p.getResult());
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public MailCode findByMailAndCode(String mail, String code) {
        try {
            return mailCodeMapper.selectByMailAndCode(mail, code);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public void add(MailCode mailCode) {
        try {
            Date date = new Date();
            mailCode.setCreateTime(date);
            mailCode.setUpdateTime(date);
            mailCodeMapper.insert(mailCode);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    @Override
    @Transactional
    public void batchAdd(List<MailCode> mailCodes) {
        try {
            Date date = new Date();
            List<MailCode> newMailCodes = mailCodes.stream().map(mailCode -> {
                mailCode.setCreateTime(date);
                mailCode.setUpdateTime(date);
                return mailCode;
            }).collect(Collectors.toList());
            mailCodeMapper.batchInsert(newMailCodes);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.ADD_FAIL, e);
        }
    }

    @Override
    public void edit(MailCode mailCode) {
        try {
            Date date = new Date();
            mailCode.setUpdateTime(date);
            mailCodeMapper.update(mailCode);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    @Transactional
    public void batchEdit(List<MailCode> mailCodes) {
        try {
            Date date = new Date();
            List<MailCode> newMailCodes = mailCodes.stream().map(mailCode -> {
                mailCode.setUpdateTime(date);
                return mailCode;
            }).collect(Collectors.toList());
            mailCodeMapper.batchUpdate(newMailCodes);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    public void delete(String mail, String code) {
        try {
            mailCodeMapper.delete(mail, code);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.DELETE_FAIL, e);
        }
    }


    @Override
    @Transactional
    public void sendMailCode(String mail, String checkCode, String mailCheckCode) {
        try {
            UserInfo userInfo = userInfoMapper.selectByEmail(mail);
            // 判断邮箱是否已存在
            if (userInfo != null) {
                throw new BusinessException(CommonMsg.MAIL_EXISTS);
            }
            // 判断验证码是否正确
            if (checkCode == null || mailCheckCode == null || !mailCheckCode.equalsIgnoreCase(checkCode)) {
                throw new BusinessException(CommonMsg.CHECK_CODE_ERROR);
            }

            // 先将这个mail的status改为失效
            mailCodeMapper.updateStatusByMail(mail);

            // 生成随机code
            String mCode = null;
            send(mail, mCode);

            // 在将这个邮箱验证码数据插入mail_code表
            MailCode mailCode = new MailCode();
            mailCode.setMail(mail);
            mailCode.setCode(mCode);
            Date date = new Date();
            mailCode.setCreateTime(date);
            mailCode.setUpdateTime(date);
            mailCode.setStatus(Constant.NUM_0);
            mailCodeMapper.insert(mailCode);
        } catch (Exception e) {
            log.error("邮件发送失败");
            throw new BusinessException(CommonMsg.MAIL_SEND_FAIL, e);
        }

    }

    /**
     * 发送邮件
     *
     * @param mail
     * @param mCode
     */
    private void send(String mail, String mCode) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(webConfig.getMailSender());   // 设置发件人
            mimeMessageHelper.setTo(mail);                          // 设置收件人
            mimeMessageHelper.setSubject("邮箱验证码");              // 设置主题
            mimeMessageHelper.setText("您的验证码是: " + mCode);      // 设置内容
            mimeMessageHelper.setSentDate(new Date());              // 设置发送时间
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

