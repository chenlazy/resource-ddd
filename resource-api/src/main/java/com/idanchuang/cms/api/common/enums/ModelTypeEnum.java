package com.idanchuang.cms.api.common.enums;

import com.idanchuang.cms.api.common.constants.ModelNameConstant;
import lombok.Getter;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-27 13:34
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Getter
public enum ModelTypeEnum {

    /**
     * 模型枚举
     */
    MODEL_TYPE_BASE("", 0, "默认"),

    MODEL_TYPE_GOODS(ModelNameConstant.COMP_GOODS_TYPE, 1, "商品模型"),

    MODEL_TYPE_GOODS_V2(ModelNameConstant.COMP_GOODS_TYPE_V2, 1, "商品模型"),

    MODEL_TYPE_GOODS_ABM(ModelNameConstant.COMP_GOODS_ABM_TYPE, 1, "商品模型"),

    MODEL_TYPE_GOODS_INTEGRAL(ModelNameConstant.COMP_INTEGRAL_GOOD, 1, "积分商品"),

    MODEL_TYPE_TASK(ModelNameConstant.COMP_TASK_COMPONENT, 2, "组件模型"),

    MODEL_TYPE_LUCKY(ModelNameConstant.COMP_LUCKY_COMPONENT, 7, "抽奖模型");

    private String type;

    private Integer modelType;

    private String desc;

    ModelTypeEnum(String type, Integer modelType, String desc) {
        this.type = type;
        this.modelType = modelType;
        this.desc = desc;
    }

    public static Integer getModelType(String type) {

        for (ModelTypeEnum typeEnum : values()) {

            if (typeEnum.getType().equals(type)) {
                return typeEnum.getModelType();
            }
        }
        return 0;
    }
}
