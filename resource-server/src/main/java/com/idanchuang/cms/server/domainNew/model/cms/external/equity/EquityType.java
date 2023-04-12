package com.idanchuang.cms.server.domainNew.model.cms.external.equity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-05-20 11:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum EquityType {

    /**
     * 权益类型
     */
    EQUITY_ACTIVITY(1, "权益活动"),

    EQUITY(2, "权益");

    @JsonValue
    public Integer getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    private final Integer val;

    private final String desc;


    @JsonCreator
    public static EquityType fromVal(Integer val) {

        if (val == null) {
            return null;
        }

        for (EquityType data : values()) {
            if (data.val.equals(val)) {
                return data;
            }
        }
        return null;
    }
}
