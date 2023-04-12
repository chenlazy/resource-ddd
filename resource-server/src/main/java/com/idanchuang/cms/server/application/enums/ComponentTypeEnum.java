package com.idanchuang.cms.server.application.enums;

import lombok.Getter;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-22 13:24
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Getter
public enum ComponentTypeEnum {

    /**
     * 组件类型
     */
    COMPONENT_TYPE_BASE(1, "基础组件"),

    COMPONENT_TYPE_BUSINESS(2, "业务组件");

    private Integer type;

    private String desc;

    ComponentTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
