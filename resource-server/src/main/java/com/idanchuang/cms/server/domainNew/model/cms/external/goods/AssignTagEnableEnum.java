package com.idanchuang.cms.server.domainNew.model.cms.external.goods;

import lombok.Getter;

/**
 * @author fym
 * @description :
 * @date 2022/4/12 下午2:25
 */
@Getter
public enum AssignTagEnableEnum {

    /**
     * 标签指定状态
     */
    ASSIGN(1, "指定"),
    NOT_ASSIGN(0, "不指定"),
    ;

    private Integer modelType;

    private String desc;

    AssignTagEnableEnum(Integer modelType, String desc) {
        this.modelType = modelType;
        this.desc = desc;
    }
}
