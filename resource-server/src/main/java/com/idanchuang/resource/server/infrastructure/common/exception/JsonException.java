package com.idanchuang.resource.server.infrastructure.common.exception;

import lombok.Getter;

/**
 * Created by develop at 2021/2/4.
 */
@Getter
public class JsonException extends RuntimeException {

    private final String jsonString;

    private final String errorMsg;

    public JsonException(String jsonString, String errorMsg) {
        this.jsonString = jsonString;
        this.errorMsg = errorMsg;
    }
}
