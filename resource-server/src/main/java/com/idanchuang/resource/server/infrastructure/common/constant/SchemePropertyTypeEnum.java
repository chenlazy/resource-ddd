package com.idanchuang.resource.server.infrastructure.common.constant;

import com.idanchuang.resource.server.infrastructure.common.exception.EnumException;
import lombok.Getter;

import java.util.Arrays;

/**
 * Created by develop at 2021/2/4.
 */
@Getter
public enum SchemePropertyTypeEnum {

    STRING(1, "文本"),
    NUMBER(2, "数字"),
    ;

    private final int type;

    private final String desc;

    SchemePropertyTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static SchemePropertyTypeEnum getByType(int type) {

        SchemePropertyTypeEnum schemePropertyType = Arrays.stream(values())
                .filter(type1 -> type1.getType() == type)
                .findFirst()
                .orElse(null);

        if (null == schemePropertyType) {
            throw new EnumException(type, "enum value not exit:" + type);
        }
        return schemePropertyType;
    }
}
