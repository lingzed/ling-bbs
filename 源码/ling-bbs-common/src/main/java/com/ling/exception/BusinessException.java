package com.ling.exception;

import com.ling.enums.ResponseCodeEnum;

public class BusinessException extends RuntimeException {
    private Integer code;
    private String msg;
    private ResponseCodeEnum codeEnum;

    /**
     * code和msg构建异常
     *
     * @param code
     * @param msg
     */
    public BusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * code和msg构建异常，带异常
     *
     * @param code
     * @param msg
     * @param e
     */
    public BusinessException(Integer code, String msg, Throwable e) {
        super(e);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 业务异常，code用响应状态码枚举业务异常的状态码
     *
     * @param msg
     */
    public BusinessException(String msg) {
        this.code = ResponseCodeEnum.COED_700.getCode();
        this.msg = msg;
    }

    /**
     * 业务异常，code用响应状态码枚举业务异常的状态码，带异常
     *
     * @param msg
     * @param e
     */
    public BusinessException(String msg, Throwable e) {
        super(e);
        this.code = ResponseCodeEnum.COED_700.getCode();
        this.msg = msg;
    }

    /**
     * 响应状态码枚举构建异常
     *
     * @param codeEnum
     */
    public BusinessException(ResponseCodeEnum codeEnum) {
        code = codeEnum.getCode();
        msg = codeEnum.getMsg();
        this.codeEnum = codeEnum;
    }

    /**
     * 响应状态码枚举和异常构建异常，带异常
     *
     * @param codeEnum
     * @param e
     */
    public BusinessException(ResponseCodeEnum codeEnum, Throwable e) {
        super(e);
        code = codeEnum.getCode();
        msg = codeEnum.getMsg();
        this.codeEnum = codeEnum;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseCodeEnum getCodeEnum() {
        return codeEnum;
    }

    /**
     * 业务异常不需要打印堆栈信息，提高效率
     *
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
