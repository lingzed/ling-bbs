package com.ling.controller;

import com.ling.entity.ImageCode;
import com.ling.entity.constant.Constant;
import com.ling.entity.vo.Result;
import com.ling.service.MailCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/account")
public class AccountController {

    private Logger log = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private MailCodeService mailCodeService;

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     * @param type
     * @throws IOException
     */
    @GetMapping("/checkCode")
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer type) throws IOException {
        log.info("验证码类型: {}", type);
        HttpSession session = request.getSession();
        ImageCode imageCode = new ImageCode(130, 38, 5, 10);
        String codeKey = type.equals(Constant.NUM_1) ? Constant.CHECK_CODE : Constant.CHECK_CODE_EMAIL;
        String code = imageCode.getCode();
        session.setAttribute(codeKey, code);    // 将验证码存入session
        log.info("验证码{}: {}", codeKey, code);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        imageCode.write(response.getOutputStream());
    }

    @GetMapping("/sendmail")
    public Result sendmail(String mail, String checkCode, HttpSession session) {
        try {
            log.info("邮箱: {}, 验证码: {}", mail, checkCode);
            // 从session中获取邮箱的图片验证码
            String mailCheckCode = (String) session.getAttribute(Constant.CHECK_CODE_EMAIL);
            mailCodeService.sendMailCode(mail, checkCode, mailCheckCode);
            return Result.success();
        } finally {
            // 验证码校验完成后需要删除
            session.removeAttribute(Constant.CHECK_CODE_EMAIL);
        }
    }
}
