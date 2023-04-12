package com.idanchuang.resource.server.infrastructure.common.exception;

import lombok.Getter;

/**
 * Created by develop at 2021/2/4.
 */
@Getter
public class ParamException extends RuntimeException {

    private final String param;

    private final String errorMsg;

    public ParamException(Object param, String errorMsg) {
        this.param = String.valueOf(param);
        this.errorMsg = errorMsg;
    }
}
