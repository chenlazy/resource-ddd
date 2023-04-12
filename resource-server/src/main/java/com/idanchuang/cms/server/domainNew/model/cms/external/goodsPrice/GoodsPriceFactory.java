package com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice;

import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.parse.PriceData;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-21 16:23
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class GoodsPriceFactory {

    private GoodsPriceFactory() {

    }

    public static GoodsPrice createGoodsPrice(GoodsPriceId id, OperatorId operatorId, GoodsId goodsId,
                                              List<PriceData> priceData, ComponentId componentId) {

        return new GoodsPrice(id, componentId, goodsId, PriceType.NORMAL_PRICE_TYPE, priceData, operatorId);
    }
}
