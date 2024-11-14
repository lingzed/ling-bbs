package com.ling.controller;

import com.ling.entity.ImageCode;
import com.ling.entity.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/account")
public class AccountController {

    private Logger log = LoggerFactory.getLogger(AccountController.class);

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
        String codeKey = type.equals(0) ? Constant.CHECK_CODE : Constant.CHECK_CODE_EMAIL;
        String code = imageCode.getCode();
        session.setAttribute(codeKey, code);
        log.info("验证码{}: {}", codeKey, code);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        imageCode.write(response.getOutputStream());
    }
}
