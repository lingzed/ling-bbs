package com.ling.controller;

import com.ling.annotation.ExcludeParamLog;
import com.ling.annotation.RequestLogRecode;
import com.ling.config.WebConfig;
import com.ling.entity.dto.query.UserMessageQueryDto;
import com.ling.entity.po.Test;
import com.ling.entity.vo.Result;
import com.ling.enums.OperationTypeEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.TestMapper;
import com.ling.service.UserMessageService;
import com.ling.service.UserPointsRecodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    private Logger log = LoggerFactory.getLogger(TestController.class);
    @Resource
    private UserMessageService userMessageService;

    @Resource
    private WebConfig webConfig;

    @Resource
    private TestMapper testMapper;
    @Resource
    private UserPointsRecodeService userPointsRecodeService;

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
        Path filePath = Paths.get("C:\\Users\\asus\\Desktop")
                .resolve("无标题-2024-11-26-0959.png")
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

    @GetMapping("/image/{fileName}")
    @RequestLogRecode
    public void test4(@ExcludeParamLog HttpServletRequest request,
                      @ExcludeParamLog HttpServletResponse response,
                      @PathVariable String fileName) throws IOException {
        log.info("没有走缓存");
        String projectFolder = webConfig.getProjectFolder();
        Path path = Paths.get(projectFolder + File.separator + "image").resolve(fileName).normalize();
        org.springframework.core.io.Resource resource = new UrlResource(path.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 返回404状态码
            response.getWriter().write("Image not found or not readable."); // 返回错误信息
            return;
        }
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "public, max-age=10");
        try (InputStream is = resource.getInputStream();
             OutputStream os = response.getOutputStream()) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
        }
    }

    @GetMapping("/test4")
    public Result<Void> test4() {
        for (int i = 1; i <= 30; i++) {
            int finalI = i;
            new Thread(() -> {
                testMapper.update("1111", finalI);
            }).start();
        }
        return Result.success();
    }

    @PostMapping("/test5")
    public Result<Void> test5() {
        return Result.success();
    }

    @GetMapping("/test6")
    public Result<List<Test>> test6(String id) {
        if (id == null) {
            log.info("id为null");
        } else if (id == "") {
            log.info("id为空字符串");
        }
        log.info("id: {}", id);
        List<Test> select = testMapper.select(id);
        return Result.success(select);
    }
}
