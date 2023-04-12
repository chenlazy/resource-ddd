package com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo;

import lombok.Getter;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午2:05
 */
@Getter
public enum SelectTypeEnum {
    /**
     * 圈选类型
     */
    MARKETING_ACTIVITY(1, "营销活动"),
    WELFARE_THREE(2, "福利星期三"),
    DISCOUNT_COUPON(3, "优惠券"),
    ABM_ACTIVITY(4, "abm活动"),
    ;

    private Integer modelType;

    private String desc;

    SelectTypeEnum(Integer modelType, String desc) {
        this.modelType = modelType;
        this.desc = desc;
    }

    public static SelectTypeEnum getModelType(Integer type) {
        if (null == type) {
            return null;
        }
        for (SelectTypeEnum typeEnum : values()) {
            if (typeEnum.getModelType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}

