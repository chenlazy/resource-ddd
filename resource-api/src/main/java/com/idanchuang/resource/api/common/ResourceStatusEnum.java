package com.idanchuang.resource.api.common;

import lombok.Getter;

import java.util.Arrays;

/**
 * Created by develop at 2021/2/4.
 */
@Getter
public enum ResourceStatusEnum {

    INVALID(0, "未生效"),
    VALID(1, "有效")
    ;

    private final int status;

    private final String desc;

    ResourceStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static ResourceStatusEnum getByStatus(int status) {

        ResourceStatusEnum contentStatus = Arrays.stream(values())
                .filter(type1 -> type1.getStatus() == status)
                .findFirst()
                .orElse(null);
        return contentStatus;
    }
}
