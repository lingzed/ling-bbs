package com.ling.controllers;

import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.vo.Result;
import com.ling.service.UserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    public Result<UserInfoSessionDto> loginHandle() {
        return null;
    }
}
