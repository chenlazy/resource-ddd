package com.idanchuang.cms.server.domainNew.model.cms.component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 17:40
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum ComponentType {

    /**
     * 组件类型
     */
    BASE_TYPE(1, "基础组件"),

    BUSINESS_TYPE(2, "业务组件");

    private final int val;

    private final String desc;

    @JsonValue
    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static ComponentType fromVal(int val) {
        for (ComponentType data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
