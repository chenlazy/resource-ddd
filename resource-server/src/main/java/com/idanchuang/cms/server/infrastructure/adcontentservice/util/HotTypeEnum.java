package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

/**
 * @author william
 * @date 2020/10/20
 */
public enum HotTypeEnum {

    BANNER(1, "banner"),
    TIME(2, "倒计时");

    private Integer code;

    private String msg;

    HotTypeEnum(Integer code, String msg) {
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
