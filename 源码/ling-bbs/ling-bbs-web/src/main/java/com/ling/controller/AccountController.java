package com.ling.controller;

import com.ling.entity.ImageCode;
import com.ling.constant.Constant;
import com.ling.entity.po.UserInfo;
import com.ling.entity.vo.Result;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import com.ling.service.MailCodeService;
import com.ling.service.UserInfoService;
import com.ling.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/account")
public class AccountController {

    private Logger log = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private MailCodeService mailCodeService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 获取验证码
     *
     * @param response
     * @param type     验证码类型
     * @throws IOException
     */
    @GetMapping("/checkCode")
    public void getCheckCode(HttpSession session, HttpServletResponse response, @RequestParam Integer type) throws IOException {
        log.info("验证码类型: {}", type);
        ImageCode imageCode = new ImageCode(130, 38, 5, 10);    // 绘制图片
        String codeKey = type.equals(Constant.NUM_1) ? Constant.CHECK_CODE : Constant.CHECK_CODE_EMAIL; // 验证码类型
        String code = imageCode.getCode();      // 生成图片验证码
        session.setAttribute(codeKey, code);    // 将验证码存入session
        log.info("验证码{}: {}", codeKey, code);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        imageCode.write(response.getOutputStream());    // 将图片验证码写入到响应流
    }

    /**
     * 发送邮箱验证码
     *
     * @param mail      收件人邮箱
     * @param checkCode 用户输入的图片验证码
     * @param session
     * @return
     */
    @GetMapping("/sendmail")
    public Result sendmail(String mail, String checkCode, HttpSession session) {
        try {
            log.info("邮箱: {}, 验证码: {}", mail, checkCode);
            if (StringUtil.isEmpty(mail) || StringUtil.isEmpty(checkCode)) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            // 从session中获取邮箱的图片验证码
            String mailCheckCode = (String) session.getAttribute(Constant.CHECK_CODE_EMAIL);
            log.info("生成的邮箱图片验证码: {}", mailCheckCode);
            mailCodeService.sendMailCode(mail, checkCode, mailCheckCode);
            return Result.success();
        } finally {
            // 验证码校验完成后需要删除
            session.removeAttribute(Constant.CHECK_CODE_EMAIL);
        }
    }

    /**
     * 注册
     *
     * @param nickname
     * @param password
     * @param mail
     * @param checkCode
     * @param mailCode
     * @param session
     * @return
     */
    @GetMapping("/register")
    public Result register(String nickname, String password, String mail, String checkCode, String mailCode, HttpSession session) {
        try {
            log.info("昵称: {}, 密码: {}, 邮箱: {}, 图片验证码: {}, 邮箱验证码: {}", nickname, password, mail, checkCode, mailCode);
            if (StringUtil.isEmpty(nickname) || StringUtil.isEmpty(password) || StringUtil.isEmpty(mail) || StringUtil.isEmpty(checkCode) || StringUtil.isEmpty(mailCode)) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            String sCheckCode = (String) session.getAttribute(Constant.CHECK_CODE);
            log.info("生成的图片验证码: {}", sCheckCode);
            userInfoService.register(nickname, password, mail, checkCode, sCheckCode, mailCode);
            return Result.success();
        } finally {
            session.removeAttribute(Constant.CHECK_CODE);
        }
    }
}
