package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.ExcludeParamLog;
import com.ling.annotation.RequestLogRecode;
import com.ling.constant.Constant;
import com.ling.entity.po.Person;
import com.ling.entity.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    @AccessControl
    @RequestLogRecode
    public Result test(@RequestBody Person person) {
        return Result.success();
    }
}
