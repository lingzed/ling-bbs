package com.ling.controller;

import com.ling.entity.vo.Result;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionController {
    private Logger log = LoggerFactory.getLogger(GlobalExceptionController.class);

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result businessExceptionHandler(BusinessException e) {
        log.error("发生异常: {}", e.getMsg(), e);
        return Result.error(e);
    }

    /**
     * 404异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result exceptionHandler404(NoHandlerFoundException e) {
        log.error("异常信息: {}", e.getMessage(), e);
        return Result.error(ResponseCodeEnum.CODE_404);
    }

    /**
     * 请求参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class,
            BindException.class})
    public Result exceptionHandler600(Exception e) {
        log.error("异常信息: {}", e.getMessage(), e);
        return Result.error(ResponseCodeEnum.CODE_600);
    }

    /**
     * 主键冲突异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result exceptionHandler601(DuplicateKeyException e) {
        log.error("异常信息: {}", e.getMessage(), e);
        return Result.error(ResponseCodeEnum.CODE_601);
    }

    /**
     * 其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler500(Exception e) {
        log.error("异常信息: {}", e.getMessage(), e);
        log.error("全局异常处理器捕获到了Exception");
        return Result.error(ResponseCodeEnum.CODE_500);
    }
}
