package com.idanchuang.cms.server.applicationNew.service;

import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPrice;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-29 09:56
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
public class GoodsPriceService {

    @Resource
    private GoodsPriceRepository goodsPriceRepository;

    /**
     * 批量插入删除商品价格
     * @param goodsPrices
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpsertGoodsPrice(List<GoodsPrice> goodsPrices) {

        if (CollectionUtils.isEmpty(goodsPrices)) {
            return false;
        }

        List<ComponentId> componentIds = goodsPrices.stream().map(GoodsPrice::getComponentId).collect(Collectors.toList());

        //批量删除之前的商品价格信息
        Boolean removePrice = goodsPriceRepository.batchRemovePrice(componentIds);

        //批量新增商品价格
        Boolean storePrice = goodsPriceRepository.batchStorePrice(goodsPrices);

        return removePrice && storePrice;
    }
}
