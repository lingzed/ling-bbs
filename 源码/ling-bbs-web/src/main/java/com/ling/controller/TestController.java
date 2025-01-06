package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.ExcludeParamLog;
import com.ling.annotation.RequestLogRecode;
import com.ling.constant.Constant;
import com.ling.entity.dto.ArticleQueryDto;
import com.ling.entity.dto.UserMessageQueryDto;
import com.ling.entity.po.Person;
import com.ling.entity.po.UserMessage;
import com.ling.entity.vo.Result;
import com.ling.enums.ArticleOrderEnum;
import com.ling.service.UserMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private UserMessageService userMessageService;

    @GetMapping
    // @AccessControl
    // @RequestLogRecode
    public Result test(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        System.out.println(page + "" + pageSize);
        UserMessageQueryDto userMessageQueryDto = new UserMessageQueryDto();
        userMessageQueryDto.setPage(page);
        userMessageQueryDto.setPageSize(pageSize);
        return Result.success(userMessageService.findByCondition(userMessageQueryDto));
    }

    @GetMapping("/test.txt")
    public String test2(HttpServletResponse response) {
        // response.setContentType("image/jpeg");
         response.setHeader("Content-Type", "image/jpeg");
        return "<h1>这是标题</h1>\n" +
                "<p>这是段落</p>\n" +
                "<div>这是div</div>";
    }
}
