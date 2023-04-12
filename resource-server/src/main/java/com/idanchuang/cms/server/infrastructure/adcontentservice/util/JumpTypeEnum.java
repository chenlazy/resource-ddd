package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author fym
 * @description :
 * @date 2021/3/1 11:00 上午
 */
@AllArgsConstructor
public enum JumpTypeEnum {

    JUMP_NULL(0, "无跳转"),
    JUMP_GOODS(1, "跳商品"),
    JUMP_URL(2, "跳url");

    @Getter
    private Integer status;

    @Getter
    private String desc;
}
