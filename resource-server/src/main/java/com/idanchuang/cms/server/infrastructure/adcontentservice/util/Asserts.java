package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

import com.idanchuang.component.base.exception.core.IErrorEnum;

/**
 * @author Luo Bao Ding
 * @since 2020/7/14
 */
public class Asserts {

    private Asserts() {
    }

    public static void isTrue(boolean expression, IErrorEnum errorEnum) {
        if (!expression) {
            throw new BusinessException(errorEnum);
        }
    }

    public static void isTrue(boolean expression, IErrorEnum errorEnum, String message) {
        if (!expression) {
            throw new BusinessException(errorEnum, message);
        }
    }

}
