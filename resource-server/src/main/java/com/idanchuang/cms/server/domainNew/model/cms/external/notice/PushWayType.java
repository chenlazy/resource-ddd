package com.idanchuang.cms.server.domainNew.model.cms.external.notice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 11:26
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum PushWayType {

    /**
     * 推送渠道
     */
    INSIDE(1, "站内"),

    OUTSIDE(2, "站外");

    @JsonValue
    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    private final Integer val;

    private final String desc;


    @JsonCreator
    public static PushWayType fromVal(Integer val) {

        if (val == null) {
            return null;
        }

        for (PushWayType data : values()) {
            if (data.val.equals(val)) {
                return data;
            }
        }
        return null;
    }
}
