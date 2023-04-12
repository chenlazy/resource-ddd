package com.idanchuang.resource.server.infrastructure.common.constant;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 15:52
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public enum DataShowEnum {

    /**
     * 数据曝光
     */
    E_PV("曝光PV", "ePv"),
    E_UV("曝光UV", "eUv"),
    C_PV("点击PV", "cPv"),
    C_UV("点击UV", "cUv"),
    ;

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    DataShowEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        for (DataShowEnum obe : values()) {
            if (key.equals(obe.getKey())) {
                return obe.getValue();
            }
        }
        return "";
    }

    public static List<String> getKeyList() {
        List<String> keyList = Lists.newArrayList();
        for (DataShowEnum showEnum : values()) {
            keyList.add(showEnum.getKey());
        }
        return keyList;
    }
}
