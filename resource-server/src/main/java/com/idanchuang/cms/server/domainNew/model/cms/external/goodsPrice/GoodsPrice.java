package com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice;

import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.parse.PriceData;
import lombok.Value;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-21 10:08
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class GoodsPrice {

    /**
     * 主键id
     */
    private GoodsPriceId id;

    /**
     * 组件id
     */
    private ComponentId componentId;

    /**
     * 商品id
     */
    private GoodsId componentGoodsId;

    /**
     * 价格类型
     */
    private PriceType priceType;

    /**
     * 价格数据，格式：[{type:"1",price:"0.01"}]
     */
    private List<PriceData> priceData;

    /**
     * 操作人id
     */
    private OperatorId operatorId;
}
