package com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-21 10:18
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum PriceType {

    /**
     * 价格类型
     */
    NORMAL_PRICE_TYPE(1, "日常价");

    private final int val;
    private final String desc;

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static PriceType fromVal(int val) {
        for (PriceType data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
