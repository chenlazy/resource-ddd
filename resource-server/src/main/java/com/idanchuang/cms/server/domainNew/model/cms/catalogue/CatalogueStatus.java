package com.idanchuang.cms.server.domainNew.model.cms.catalogue;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-17 17:56
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum CatalogueStatus {


    /**
     * 目录状态
     */
    CATALOGUE_STATUS_DRAFT(0, "草稿"),

    CATALOGUE_STATUS_PUBLISH(1, "发布");

    private final int val;

    private final String desc;

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static CatalogueStatus fromVal(int val) {
        for (CatalogueStatus data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
