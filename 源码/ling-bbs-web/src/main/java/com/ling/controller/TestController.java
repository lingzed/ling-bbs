package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.RecodeParam;
import com.ling.annotation.RequestLogRecode;
import com.ling.entity.po.Person;
import com.ling.entity.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    @AccessControl()
    @RequestLogRecode(enable = true)
    public Result test(@RequestBody @RecodeParam Person person) {
        return Result.success();
    }
}
