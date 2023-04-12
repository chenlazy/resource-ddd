package com.idanchuang.resource.server.infrastructure.common.constant;

import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.core.IErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhousun
 * @create 2020/12/14
 */
@AllArgsConstructor
@Getter
public class ErrorBuilder implements IErrorEnum {

    private int code;

    private String msg;

    public static IErrorEnum of(int code, String msg) {
        return new ErrorBuilder(code, msg);
    }

    @Override
    public ExType getExType() {
        return ExType.BUSINESS_ERROR;
    }
}
