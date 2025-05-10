package com.ling.controller;

import com.ling.entity.dto.session.SessionAdminInfo;
import com.ling.entity.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户控制器
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @PostMapping("/login")
    public Result<SessionAdminInfo> login(String account, String password) {
        SessionAdminInfo adminInfo = new SessionAdminInfo();
        adminInfo.setAccount("admin");
        return Result.success(adminInfo);
    }

    @GetMapping("/check-code")
    public Result<String> getCheckCode() {
        return Result.success("验证码");
    }

    @GetMapping
    public Result<String> test() {
        return Result.success("测试成功");
    }
}
