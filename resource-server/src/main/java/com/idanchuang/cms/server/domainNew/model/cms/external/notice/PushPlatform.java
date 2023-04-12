package com.idanchuang.cms.server.domainNew.model.cms.external.notice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 14:22
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum PushPlatform {

    //推送平台 1: DC 2: ABM 3: VIP_CLUB"
    DC(1, "单创"),
    ABM(2, "ABM"),
    DT(3, "DT");

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
    public static PushPlatform fromVal(Integer val) {

        if (val == null) {
            return null;
        }

        for (PushPlatform data : values()) {
            if (data.val.equals(val)) {
                return data;
            }
        }
        return null;
    }
}
