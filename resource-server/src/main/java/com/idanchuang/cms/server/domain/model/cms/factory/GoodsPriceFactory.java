package com.idanchuang.cms.server.domain.model.cms.factory;

import com.idanchuang.cms.server.domain.model.cms.ComponentGoodsPrice;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 13:54
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class GoodsPriceFactory {

    private GoodsPriceFactory() {

    }

    public static ComponentGoodsPrice createGoodsPrice(Integer id, Integer operatorId, Long goodsId, Integer type,
                                                String priceData, Long componentId, LocalDateTime createTime,
                                                LocalDateTime updateTime) {

        return new ComponentGoodsPrice(id, componentId, goodsId, type, priceData, operatorId, createTime, updateTime);
    }
}
