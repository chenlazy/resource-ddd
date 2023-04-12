package com.idanchuang.cms.server.domainNew.model.cms.container;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 17:15
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum ContainerStatus {


    /**
     * 生效状态
     */
    INVALID(1, "草稿"),

    VALID(2, "发布");

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
    public static ContainerStatus fromVal(int val) {
        for (ContainerStatus data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
