package com.idanchuang.resource.server.infrastructure.common.constant;

import com.idanchuang.resource.server.infrastructure.common.exception.EnumException;
import lombok.Getter;

import java.util.Arrays;

/**
 * Created by develop at 2021/2/4.
 */
@Getter
public enum ResourceTypeEnum {

    URL(1, "url类型"),
    NUMBER(2, "数字类型"),
    IMAGE(3, "图片类型"),
    OBJECT(4, "对象类型，Json对象"),
    ;

    private final int type;

    private final String desc;

    ResourceTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ResourceTypeEnum getByType(int type) {

        ResourceTypeEnum resourceType = Arrays.stream(values())
                .filter(type1 -> type1.getType() == type)
                .findFirst()
                .orElse(null);

        if (null == resourceType) {
            throw new EnumException(type, "enum value not exit:" + type);
        }
        return resourceType;
    }
}
