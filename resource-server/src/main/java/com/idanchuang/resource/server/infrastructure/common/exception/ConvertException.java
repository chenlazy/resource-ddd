package com.idanchuang.resource.server.infrastructure.common.exception;

import lombok.Getter;

/**
 * Created by develop at 2021/2/4.
 */
@Getter
public class ConvertException extends RuntimeException {

    private final String errorMsg;

    public ConvertException(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
