package com.ling.service;

import com.ling.entity.po.MailCode;
import com.ling.entity.po.UserInfo;
import com.ling.entity.vo.PageBean;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MailCodeService {

    /**
     * 查询
     *
     * @param mailCode
     * @return
     */
    PageBean<MailCode> find(MailCode mailCode);

    /**
     * 根据id查询
     *
     * @param mail
     * @param code
     * @return
     */
    MailCode findByMailAndCode(String mail, String code);

    /**
     * 添加
     *
     * @param mailCode
     */
    void add(MailCode mailCode);

    /**
     * 批量添加
     *
     * @param mailCodes
     */
    void batchAdd(List<MailCode> mailCodes);

    /**
     * 更新
     *
     * @param mailCode
     */
    void edit(MailCode mailCode);

    /**
     * 批量更新
     *
     * @param mailCodes
     */
    void batchEdit(List<MailCode> mailCodes);

    /**
     * 根据mail和code删除
     *
     * @param mail
     * @param code
     */
    void delete(String mail, String code);

    /**
     * 发送邮箱验证码
     *
     * @param mail
     */
    void sendMailCode(String mail);

    /**
     * 校验邮箱验证码
     *
     * @param mail
     * @param mailCode
     */
    void checkMailCode(String mail, String mailCode);

    /**
     * 根据邮箱修改状态为已使用
     *
     * @param mail
     */
    void editToUsedByMail(String mail);
}
