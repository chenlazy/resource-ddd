package com.idanchuang.cms.server.domainNew.shard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 15:43
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum PlatformCode {

    /**
     * 平台类型
     */
    VTN_OLD(-1, "VTN_OLD"),

    VTN(0, "VTN"),

    ABM(1, "ABM"),

    VTN_NEW(2, "VTN_NEW"),

    ABM_NEW(3, "ABM_NEW");


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
    public static PlatformCode fromVal(int val) {
        for (PlatformCode data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }

    @JsonCreator
    public static PlatformCode fromDesc(String desc) {

        if (StringUtils.isNotEmpty(desc)) {
            for (PlatformCode data : values()) {
                if (data.desc.equals(desc) || data.desc.equals(desc.toUpperCase())) {
                    return data;
                }
            }
        }
        return null;
    }

    /**
     * 简化平台信息
     * @param platformCode
     * @return
     */
    public static String simplyPlatform(PlatformCode platformCode) {

        if (null == platformCode) {
            return "";
        }

        String platformDesc = platformCode.getDesc();

        if (platformDesc.contains("_")) {
            return platformDesc.substring(0, platformDesc.indexOf("_"));
        }
        return platformDesc;
    }

    public static String getVersionByPlatform(PlatformCode platformCode) {

        if (null == platformCode) {
            return "2";
        }
        String version = "2";
        switch (platformCode) {
            case VTN:
            case ABM:
                version = "3";
                break;
            case VTN_NEW:
            case ABM_NEW:
                version = "4";
                break;
                default:
        }
        return version;
    }
}
