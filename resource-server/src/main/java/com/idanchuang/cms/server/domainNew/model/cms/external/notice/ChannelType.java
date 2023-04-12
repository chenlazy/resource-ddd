package com.idanchuang.cms.server.domainNew.model.cms.external.notice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 10:28
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum ChannelType {

    /**
     * 渠道类型
     */
    CHANNEL_TYPE_ALL(0, "全部渠道"),

    CHANNEL_TYPE_PUSH(1, "push渠道"),

    CHANNEL_TYPE_SEND(2, "站内信渠道");

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
    public static ChannelType fromVal(Integer val) {

        if (val == null) {
            return null;
        }

        for (ChannelType data : values()) {
            if (data.val.equals(val)) {
                return data;
            }
        }
        return null;
    }
}
