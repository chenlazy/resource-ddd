package com.idanchuang.resource.server.infrastructure.common.constant;

import com.idanchuang.resource.server.infrastructure.common.exception.EnumException;
import lombok.Getter;

import java.util.Arrays;

/**
 * Created by develop at 2021/2/4.
 * @author wuai
 */
@Getter
public enum BusinessTypeEnum {

    ABM(1, "abm"),
    VTN(2, "vtn"),
    TJ(3, "腾炬"),
    ;

    private final int type;

    private final String desc;

    BusinessTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static BusinessTypeEnum getByType(int type) {

        BusinessTypeEnum businessType = Arrays.stream(values())
                .filter(type1 -> type1.getType() == type)
                .findFirst()
                .orElse(null);

        if (null == businessType) {
            throw new EnumException(type, "enum value not exit:" + type);
        }
        return businessType;
    }
}
