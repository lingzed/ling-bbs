package com.ling.enums;

import com.ling.commons.CommonMsg;

public enum ResponseCodeEnum {
    CODE_200(200, CommonMsg.REQUEST_SUCCESS),
    CODE_404(404, CommonMsg.REQUEST_NOT_FOUND),
    CODE_600(600, CommonMsg.REQUEST_PARAM_ERROR),
    CODE_601(601, CommonMsg.REQUEST_ALREADY_EXISTS),
    COED_700(700, CommonMsg.BUSINESS_ERROR),
    CODE_500(500, CommonMsg.SERVER_ERROR),
    CODE_900(900, CommonMsg.REQUEST_TIMEOUT);

    private Integer code;

    private String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}