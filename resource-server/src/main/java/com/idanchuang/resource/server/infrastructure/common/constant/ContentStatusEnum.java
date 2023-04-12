package com.idanchuang.resource.server.infrastructure.common.constant;

import com.idanchuang.resource.server.infrastructure.common.exception.EnumException;
import lombok.Getter;

import java.util.Arrays;

/**
 * Created by develop at 2021/2/4.
 */
@Getter
public enum ContentStatusEnum {

    INIT(0, "初始化"),
    VALID(1, "有效"),
    EXPIRED(2, "过期"),
    ;

    private final int type;

    private final String desc;

    ContentStatusEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ContentStatusEnum getByType(int type) {

        ContentStatusEnum contentStatus = Arrays.stream(values())
                .filter(type1 -> type1.getType() == type)
                .findFirst()
                .orElse(null);

        if (null == contentStatus) {
            throw new EnumException(type, "enum value not exit:" + type);
        }
        return contentStatus;
    }
}
