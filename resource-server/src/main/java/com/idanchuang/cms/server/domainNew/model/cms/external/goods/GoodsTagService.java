package com.idanchuang.cms.server.domainNew.model.cms.external.goods;

import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityKey;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;

import java.util.List;
import java.util.Map;


/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-22 16:04
 * @Desc: 商品标签服务
 * @Copyright VTN Limited. All rights reserved.
 */
public interface GoodsTagService {

    /**
     * 创建标签（指定标签类型时会返回标签id）
     * @param goodsTagMessage
     * @return
     */
    Long createGoodsTag(GoodsTagMessage goodsTagMessage);

    /**
     * 失效标签
     * @param masterplateId
     */
    void expireOldTag(MasterplateId masterplateId);

    /**
     * 页面失效和创建标签
     * @param masterplate
     * @param goodsIds
     * @param pageId
     * @param equityIdMap
     */
    Map<EquityKey, Long> pageExpireAndCreateTag(Masterplate masterplate, List<GoodsId> goodsIds, ClientPageId pageId,
                                           Map<EquityKey, List<GoodsId>> equityIdMap);
}
