package com.idanchuang.cms.server.infrastructureNew.repository.convertor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPrice;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPriceId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.PriceType;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.parse.PriceData;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.GoodsPriceDO;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-29 13:43
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class RepositoryGoodsPriceConvert {

    private RepositoryGoodsPriceConvert() {

    }

    public static GoodsPriceDO entityToDO(GoodsPrice goodsPrice) {
        if (null == goodsPrice) {
            return null;
        }

        GoodsPriceDO goodsPriceDO = new GoodsPriceDO();
        goodsPriceDO.setComponentId(null != goodsPrice.getComponentId() ? goodsPrice.getComponentId().getValue() : 0);
        goodsPriceDO.setComponentGoodsId(null != goodsPrice.getComponentGoodsId() ? goodsPrice.getComponentGoodsId().getValue() : 0);
        goodsPriceDO.setType(null != goodsPrice.getPriceType() ? goodsPrice.getPriceType().getVal() : 0);

        if (CollectionUtils.isNotEmpty(goodsPrice.getPriceData())) {
            goodsPriceDO.setPriceData(JsonUtil.toJsonString(goodsPrice.getPriceData()));
        }
        goodsPriceDO.setOperatorId(null != goodsPrice.getOperatorId() ? (int)goodsPrice.getOperatorId().getValue() : 0);
        return goodsPriceDO;
    }

    public static GoodsPrice doToDomain(GoodsPriceDO goodsPriceDO) {
        if (null == goodsPriceDO) {
            return null;
        }

        String priceData = goodsPriceDO.getPriceData();

        List<PriceData> priceDataList = Lists.newArrayList();

        if (StringUtils.isNotEmpty(priceData)) {
            priceDataList = JsonUtil.toList(priceData, new TypeReference<List<PriceData>>() {});
        }
        return new GoodsPrice(new GoodsPriceId(goodsPriceDO.getId()),
                new ComponentId(goodsPriceDO.getComponentId()),
                new GoodsId(goodsPriceDO.getComponentGoodsId()),
                PriceType.fromVal(goodsPriceDO.getType()),
                priceDataList, new OperatorId(goodsPriceDO.getOperatorId()));
    }
}
