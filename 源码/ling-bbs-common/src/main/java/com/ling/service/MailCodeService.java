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
     * @param checkCode 用户输入的验证码
     * @param mailCheckCode 生成的图片验证码
     */
    void sendMailCode(String mail, String checkCode, String mailCheckCode);
}
