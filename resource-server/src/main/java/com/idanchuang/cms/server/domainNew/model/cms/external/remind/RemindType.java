package com.idanchuang.cms.server.domainNew.model.cms.external.remind;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-28 17:08
 * @Desc: 提醒类型枚举类型
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum RemindType {

    /**
     * 提醒类型
     */
    REMIND_WITH_NOTIFY_IMG(1, "带提醒的图"),

    REMIND_WITH_HOVER_BUTTON(2, "悬浮按钮提醒"),

    REMIND_WELFARE_START_SALE(3, "福三开售提醒"),

    REMIND_COUPON_SUBSCRIBE(4, "优惠券预约提醒");

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
    public static RemindType fromVal(Integer val) {

        if (val == null) {
            return null;
        }

        for (RemindType data : values()) {
            if (data.val.equals(val)) {
                return data;
            }
        }
        return null;
    }

    private static List<RemindType> REMIND_COMPONENT_LIST = Lists.newArrayList(REMIND_WITH_NOTIFY_IMG,
            REMIND_WITH_HOVER_BUTTON, REMIND_COUPON_SUBSCRIBE);

    public static List<RemindType> getRemindComponentList() {
        return REMIND_COMPONENT_LIST;
    }
}
