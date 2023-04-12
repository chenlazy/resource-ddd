package com.idanchuang.cms.server.domainNew.model.cms.external.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-22 16:27
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum MMessageSendStatus {

    /**
     * 发送状态
     */
    NOT_SEND(0, "未发送"),

    SEND(1, "已发送"),

    PART_SEND(2, "发送中");

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
    public static MMessageSendStatus fromVal(Integer val) {

        if (val == null) {
            return null;
        }

        for (MMessageSendStatus data : values()) {
            if (data.val.equals(val)) {
                return data;
            }
        }
        return null;
    }
}
