package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

import com.idanchuang.component.base.exception.core.IErrorEnum;
import lombok.Getter;

/**
 * @author Luo Bao Ding
 * @author hulk.Wang
 **/
@Getter
public class BusinessException extends RuntimeException {

    private IErrorEnum errorEnum;

    private Integer code;

    private String userMessage;

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
            this.userMessage = userMessage[0];
        } else {
            this.userMessage = errorEnum.getMsg();
        }
    }

}

