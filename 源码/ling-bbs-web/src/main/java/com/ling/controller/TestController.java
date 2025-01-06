package com.ling.controller;

import com.ling.entity.dto.UserMessageQueryDto;
import com.ling.entity.vo.Result;
import com.ling.service.UserMessageService;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @GetMapping("/test3")
    public void test3(HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        Path filePath = Paths.get("C:\\Users\\ling\\Pictures\\Camera Roll")
                .resolve("v2-a08308e2a86b5053dc000e152081a12c.png")
                .normalize();
        org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
        try (InputStream inputStream = resource.getInputStream();
             ServletOutputStream outputStream = response.getOutputStream()) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        }
    }
}
