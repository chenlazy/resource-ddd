package com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant;

import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.core.IErrorEnum;

/**
 * 业务异常枚举
 * @author: hulk.Wang
 **/
public enum BusinessErrorCodeEnum implements IErrorEnum {

    ;

    private int code;

    private String message;

    BusinessErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return this.message;
    }

    @Override
    public ExType getExType() {
        return ExType.BUSINESS_ERROR;
    }


    public String getMessage() {
        return message;
    }

    public static String getByMessageByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (BusinessErrorCodeEnum obe : values()) {
            if (code.equals(obe.getCode())) {
                return obe.getMessage ();
            }
        }
        return null;
    }

    public static BusinessErrorCodeEnum getBycode(Integer code) {
        if (code == null) {
            return null;
        }
        for (BusinessErrorCodeEnum obe : values()) {
            if (code.equals(obe.getCode())) {
                return obe;
            }
        }
        return null;
    }
}
