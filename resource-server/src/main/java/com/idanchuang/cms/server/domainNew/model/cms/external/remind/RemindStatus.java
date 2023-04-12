package com.idanchuang.cms.server.domainNew.model.cms.external.remind;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-28 17:02
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum RemindStatus {

    /**
     * 提醒状态
     */
    REMIND_STATUS_DELETE(-1, "已取消"),

    REMIND_STATUS_WAIT(1, "待提醒"),

    REMIND_STATUS_SEND(2, "已提醒");

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
    public static RemindStatus fromVal(Integer val) {

        if (val == null) {
            return null;
        }

        for (RemindStatus data : values()) {
            if (data.val.equals(val)) {
                return data;
            }
        }
        return null;
    }
}
