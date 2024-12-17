package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.Validation;
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
    public Result test(@RequestBody @Validation Person person) {
        return Result.success();
    }
}
