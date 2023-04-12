package com.idanchuang.resource.server.infrastructure.common.exception;

import com.idanchuang.component.base.exception.core.IErrorEnum;
import com.idanchuang.resource.server.infrastructure.common.constant.ErrorBuilder;
import com.idanchuang.resource.server.infrastructure.common.constant.ErrorEnum;
import lombok.Getter;

/**
 * @author Luo Bao Ding
 * @author hulk.Wang
 **/
@Getter
public class BusinessException extends RuntimeException {

    private IErrorEnum errorEnum;

    private Integer code;

    private String[] userMessage;

    public BusinessException(String userMessage){
        this(ErrorEnum.COMMON_REQUEST_ERROR,userMessage);
    }

    public BusinessException(int code, String userMessage) {
        this(new ErrorBuilder(code, "{}"), userMessage);
    }

    public BusinessException(IErrorEnum errorEnum, String... userMessage) {
        super (errorEnum.getMsg());
        this.errorEnum = errorEnum;
        this.code = errorEnum.getCode();

        if(userMessage != null && userMessage.length > 0) {
            this.userMessage = userMessage;
        } else {
            this.userMessage = new String[] {errorEnum.getMsg()};
        }
    }
}

