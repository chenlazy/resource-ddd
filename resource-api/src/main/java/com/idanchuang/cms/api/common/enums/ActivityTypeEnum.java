package com.idanchuang.cms.api.common.enums;

import lombok.Getter;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午2:05
 */
@Getter
public enum ActivityTypeEnum {
    /**
     * 圈选类型
     */
    MARKETING_ACTIVITY(1, "营销活动"),

    WELFARE_THREE(2, "福利星期三"),

    DISCOUNT_COUPON(3, "优惠券"),

    ABM_ACTIVITY(4, "abm活动");

    private Integer modelType;

    private String desc;

    ActivityTypeEnum(Integer modelType, String desc) {
        this.modelType = modelType;
        this.desc = desc;
    }

    public static ActivityTypeEnum getModelType(Integer type) {
        if (null == type) {
            return null;
        }
        for (ActivityTypeEnum typeEnum : values()) {
            if (typeEnum.getModelType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

