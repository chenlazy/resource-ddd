package com.idanchuang.cms.server.domainNew.model.cms.number;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author fym
 * @description :
 * @date 2021/12/24 上午11:12
 */
@AllArgsConstructor
public enum ConstructionTypeEnum {

    /**
     * 号码类型
     */
    COMPONENT(1, "组件"),

    CONTAINER(2, "容器"),

    MASTERPLATE(3, "模版"),

    CATALOGUE(4, "模版目录");


    @JsonValue
    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    private final int val;

    private final String desc;


    @JsonCreator
    public static ConstructionTypeEnum fromVal(int val) {
        for (ConstructionTypeEnum data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
