package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhousun
 * @create 2020/11/23
 */
@AllArgsConstructor
public enum ReleasedEnum {

    UN_RELEASE(0,"未发布"),
    RELEASED(1,"已发布")
    ;

    @Getter
    private Integer status;

    @Getter
    private String desc;

}
