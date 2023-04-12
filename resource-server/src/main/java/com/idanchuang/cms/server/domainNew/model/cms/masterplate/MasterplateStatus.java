package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 16:45
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum MasterplateStatus {

    /**
     * 生效状态
     */
    INVALID(0, "未生效"),

    VALID(1, "生效中"),

    OVERDUE(2,"已过期");


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
    public static MasterplateStatus fromVal(Integer val) {

        if (val == null) {
            return null;
        }

        for (MasterplateStatus data : values()) {
            if (data.val.equals(val)) {
                return data;
            }
        }
        return null;
    }
}
