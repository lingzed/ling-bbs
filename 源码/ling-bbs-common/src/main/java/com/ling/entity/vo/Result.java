package com.ling.entity.vo;

import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;

public class Result<T> {
    private String status;
    private Integer code;
    private String msg;
    private T data;

    public Result(String status, ResponseCodeEnum codeEnum, T data) {
        this.status = status;
        code = codeEnum.getCode();
        msg = codeEnum.getMsg();
        this.data = data;
    }

    public Result(String status, Integer code, String msg, T data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功结果，返回200，带数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>("success", ResponseCodeEnum.CODE_200, data);
    }

    /**
     * 成功结果，返回200，不带数据
     *
     * @return
     */
    public static Result success() {
        return new Result<>("success", ResponseCodeEnum.CODE_200, null);
    }

    /**
     * 失败结果，通过响应状态码枚举构建
     *
     * @param codeEnum
     * @return
     */
    public static Result error(ResponseCodeEnum codeEnum) {
        return new Result<>("error", codeEnum, null);
    }

    /**
     * 失败结果，通过code和msg构建
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg) {
        return new Result<>("error", code, msg, null);
    }

    /**
     * 失败结果，通过业务异常构建
     *
     * @param e
     * @return
     */
    public static Result error(BusinessException e) {
        return error(e.getCode(), e.getMsg());
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
