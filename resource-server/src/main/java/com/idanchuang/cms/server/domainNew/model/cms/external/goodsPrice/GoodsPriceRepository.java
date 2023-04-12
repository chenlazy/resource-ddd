package com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice;

import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-22 15:19
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface GoodsPriceRepository {

    /**
     * 批量保存价格
     * @param goodsPriceList
     * @return
     */
    Boolean batchStorePrice(List<GoodsPrice> goodsPriceList);

    /**
     * 批量删除价格
     * @param componentIds
     * @return
     */
    Boolean batchRemovePrice(List<ComponentId> componentIds);

    /**
     * 查询价格列表
     * @param priceQueryForm
     * @return
     */
    List<GoodsPrice> queryGoodsPriceList(GoodsPriceQueryForm priceQueryForm);
}
