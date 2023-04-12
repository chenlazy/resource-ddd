package com.idanchuang.cms.server.domainNew.model.cms.catalogue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 16:00
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum CatalogueType {

    /**
     * 页面类型
     */
    CLIENT_PAGE(1, "原生页面"),

    SUBJECT(2, "h5专题");

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
    public static CatalogueType fromVal(int val) {
        for (CatalogueType data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
