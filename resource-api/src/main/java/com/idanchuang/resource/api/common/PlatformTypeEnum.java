package com.idanchuang.resource.api.common;

import lombok.Getter;

import java.util.Arrays;

/**
 * Created by develop at 2021/2/4.
 */
@Getter
public enum PlatformTypeEnum {

    APP(1, "客户端"),
    H5(2, "H5"),
    WECHAT(3, "小程序"),
    ;

    private final int type;

    private final String desc;

    PlatformTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static PlatformTypeEnum getByType(int type) {

        PlatformTypeEnum platformType = Arrays.stream(values())
                .filter(type1 -> type1.getType() == type)
                .findFirst()
                .orElse(null);
        return platformType;
    }
}
