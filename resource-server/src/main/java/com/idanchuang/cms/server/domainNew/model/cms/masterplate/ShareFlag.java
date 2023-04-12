package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 16:49
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum ShareFlag {

    /**
     * 分享类型
     */
    NON_SHARE(0, "不分享"),

    SHARE(1, "分享");

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
    public static ShareFlag fromVal(int val) {
        for (ShareFlag data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
