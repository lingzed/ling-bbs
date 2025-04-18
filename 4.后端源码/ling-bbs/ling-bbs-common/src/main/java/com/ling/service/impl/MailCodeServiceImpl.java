package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.commons.CommonMsg;
import com.ling.config.WebConfig;
//import com.ling.config.ABConfig;
import com.ling.constant.Constant;
import com.ling.entity.dto.SysSetting4Mail;
import com.ling.entity.po.MailCode;
import com.ling.entity.vo.PageBean;
import com.ling.enums.UseStatusEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.MailCodeMapper;
import com.ling.service.MailCodeService;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailCodeServiceImpl implements MailCodeService {
    private static final Logger log = LoggerFactory.getLogger(MailCodeServiceImpl.class);
    @Resource
    private MailCodeMapper mailCodeMapper;
    @Resource
    private WebConfig webConfig;
    @Resource
    private JavaMailSender javaMailSender;

//    private
//    private
    @Override
    public PageBean<MailCode> find(MailCode mailCode) {
        try {
            PageHelper.startPage(mailCode.getPage(), mailCode.getPageSize());
            List<MailCode> mailCodes = mailCodeMapper.select(mailCode);
            Page<MailCode> p = (Page<MailCode>) mailCodes;
            return PageBean.of(p.getTotal(), mailCode.getPage(), p.getPageSize(), p.getPages(), p.getResult());
        } catch (Exception e) {
            log.error(CommonMsg.QUERY_FAIL, e);
            throw new BusinessException(CommonMsg.QUERY_FAIL, e);
        }
    }

    @Override
    public MailCode findByMailAndCode(String mail, String code) {
        try {
            return mailCodeMapper.selectByMailAndCode(mail, code);
        } catch (Exception e) {
            log.error(CommonMsg.QUERY_FAIL, e);
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
            log.error(CommonMsg.ADD_FAIL, e);
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
            log.error(CommonMsg.ADD_FAIL, e);
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
            log.error(CommonMsg.EDIT_FAIL, e);
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
            log.error(CommonMsg.EDIT_FAIL, e);
            throw new BusinessException(CommonMsg.EDIT_FAIL, e);
        }
    }

    @Override
    public void delete(String mail, String code) {
        try {
            mailCodeMapper.delete(mail, code);
        } catch (Exception e) {
            log.error(CommonMsg.DELETE_FAIL, e);
            throw new BusinessException(CommonMsg.DELETE_FAIL, e);
        }
    }

    /**
     * 发送邮件的业务
     *
     * @param mail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMailCode(String mail) {
        // 先将这个mail的status改为失效
        mailCodeMapper.updateStatusByMail(mail, new Date());

        // 生成随机code
        String mCode = StrUtil.getRandomStr(Constant.NUM_5);
        send(mail, mCode);

        // 再将这个邮箱验证码数据插入mail_code表
        MailCode mailCode = new MailCode();
        mailCode.setMail(mail);
        mailCode.setCode(mCode);
        Date date = new Date();
        mailCode.setCreateTime(date);
        mailCode.setUpdateTime(date);
        mailCode.setStatus(UseStatusEnum.NOT_USE.getStatus());

        mailCodeMapper.insert(mailCode);
    }

    /**
     * 校验邮箱验证码是否正确
     *
     * @param mail
     * @param mailCode
     */
    @Override
    public void checkMailCode(String mail, String mailCode) {
        MailCode mailCodeInfo = mailCodeMapper.selectByMailAndCode(mail, mailCode);
        if (mailCodeInfo == null || System.currentTimeMillis() - mailCodeInfo.getCreateTime().getTime() > Constant.MIN_5_TO_MILLIS) {
            throw new BusinessException(CommonMsg.MAIL_CHECK_CODE_ERROR);
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
            SysSetting4Mail sysSetting4Mail = SysCacheUtil.getSysSettingManager().getSysSetting4Mail();
            mimeMessageHelper.setSubject(sysSetting4Mail.getMailTitle());   // 设置主题
            mimeMessageHelper.setText(String.format(sysSetting4Mail.getMailContent(), mCode));      // 设置内容
            mimeMessageHelper.setSentDate(new Date());              // 设置发送时间
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new BusinessException(CommonMsg.MAIL_SEND_FAIL, e);
        }
    }

    /**
     * 将这个邮箱验证码数据改为已使用
     *
     * @param mail
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void editToUsedByMail(String mail) {
        mailCodeMapper.updateStatusByMail(mail, new Date());
    }
}

