package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 17:01
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum GoodsEnable {

    /**
     * 商品是否自动下架
     */
    GOODS_UNABLE(0, "不自动下架"),

    GOODS_ENABLE(1, "自动下架");

    private final int val;

    private final String desc;

    @JsonValue
    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static GoodsEnable fromVal(int val) {
        for (GoodsEnable data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
