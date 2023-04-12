package com.idanchuang.cms.server.application.enums;

import lombok.Getter;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-22 13:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Getter
public enum GoodsEnableEnum {

    /**
     * 商品自动下架设置
     */
    GOODS_DISABLE(0, "商品不自动下架"),

    GOODS_ENABLE(1, "商品自动下架");



    private Integer enable;

    private String desc;

    GoodsEnableEnum(Integer enable, String desc) {
        this.enable = enable;
        this.desc = desc;
    }
}
