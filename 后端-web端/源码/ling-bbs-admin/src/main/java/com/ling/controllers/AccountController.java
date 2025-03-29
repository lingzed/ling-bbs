package com.ling.controllers;

import com.ling.entity.dto.SessionUserinfo;
import com.ling.entity.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    public Result<SessionUserinfo> loginHandle() {
        return null;
    }
}
