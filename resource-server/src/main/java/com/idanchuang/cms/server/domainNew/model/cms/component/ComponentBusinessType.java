package com.idanchuang.cms.server.domainNew.model.cms.component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.idanchuang.cms.api.common.constants.ModelNameConstant;
import com.idanchuang.cms.api.common.enums.ModelTypeEnum;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 17:47
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum ComponentBusinessType {

    /**
     * 模型枚举
     */
    BUSINESS_TYPE_BASE("", 0, "默认"),

    BUSINESS_TYPE_GOODS(ModelNameConstant.COMP_GOODS_TYPE, 1, "商品模型"),

    BUSINESS_TYPE_GOODS_V2(ModelNameConstant.COMP_GOODS_TYPE_V2, 1, "商品模型"),

    BUSINESS_TYPE_GOODS_ABM(ModelNameConstant.COMP_GOODS_ABM_TYPE, 1, "商品模型"),

    BUSINESS_TYPE_GOODS_INTEGRAL(ModelNameConstant.COMP_INTEGRAL_GOOD, 1, "积分商品"),

    BUSINESS_TYPE_TASK(ModelNameConstant.COMP_TASK_COMPONENT, 2, "组件模型"),

    BUSINESS_TYPE_LUCKY(ModelNameConstant.COMP_LUCKY_COMPONENT, 7, "抽奖模型"),

    BUSINESS_TYPE_GOODS_NEW(ModelNameConstant.NEW_COMP_GOODS_TYPE, 1, "福三新商品组件"),

    BUSINESS_TYPE_GOODS_RECOMMEND(ModelNameConstant.RECOMMEND_COMP_GOODS_TYPE, 1, "福三商品推荐组件"),

    BUSINESS_TYPE_EQUITY_GOODS(ModelNameConstant.EQUITY_GOODS, 1, "权益商品组件"),

    ;

    private final String type;

    private final Integer modelType;

    private final String desc;

    public String getType() {
        return type;
    }

    @JsonValue
    public Integer getModelType() {
        return modelType;
    }

    public String getDesc() {
        return desc;
    }


    @JsonCreator
    public static ComponentBusinessType fromVal(String type) {
        for (ComponentBusinessType data : values()) {
            if (data.type.equals(type)) {
                return data;
            }
        }
        return null;
    }

    @JsonCreator
    public static ComponentBusinessType fromType(Integer modelType) {
        for (ComponentBusinessType data : values()) {
            if (data.modelType.equals(modelType)) {
                return data;
            }
        }
        return null;
    }

    public static ComponentBusinessType getBusinessType(String type) {

        for (ComponentBusinessType typeEnum : values()) {

            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
