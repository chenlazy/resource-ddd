package com.idanchuang.resource.server.infrastructure.common.exception;

import lombok.Getter;

/**
 * Created by develop at 2021/2/4.
 */
@Getter
public class EnumException extends RuntimeException {

    private final int enumValue;

    private final String errorMsg;

    public EnumException(int enumValue, String errorMsg) {
        this.enumValue = enumValue;
        this.errorMsg = errorMsg;
    }
}
