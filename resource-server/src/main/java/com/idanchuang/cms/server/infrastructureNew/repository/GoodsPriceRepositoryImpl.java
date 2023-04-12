package com.idanchuang.cms.server.infrastructureNew.repository;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domain.shard.IdObject;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPrice;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPriceQueryForm;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPriceRepository;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.GoodsPriceDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper.GoodsPriceMapper;
import com.idanchuang.cms.server.infrastructureNew.repository.convertor.RepositoryGoodsPriceConvert;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-29 13:42
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class GoodsPriceRepositoryImpl implements GoodsPriceRepository {

    @Resource
    private GoodsPriceMapper goodsPriceMapper;

    @Override
    public Boolean batchStorePrice(List<GoodsPrice> goodsPriceList) {
        if (CollectionUtils.isEmpty(goodsPriceList)) {
            return false;
        }
        List<GoodsPriceDO> priceDOS = goodsPriceList.stream().map(RepositoryGoodsPriceConvert::entityToDO).collect(Collectors.toList());
        int store = goodsPriceMapper.batchStoreGoodsPirce(priceDOS);
        return store > 0;
    }

    @Override
    public Boolean batchRemovePrice(List<ComponentId> componentIds) {
        if (CollectionUtils.isEmpty(componentIds)) {
            return false;
        }
        List<Long> componentIdList = componentIds.stream().map(IdObject::getValue).collect(Collectors.toList());

        int remove = goodsPriceMapper.batchRemoveGoodsPrice(componentIdList);
        return remove > 0;
    }

    @Override
    public List<GoodsPrice> queryGoodsPriceList(GoodsPriceQueryForm priceQueryForm) {

        if (CollectionUtils.isEmpty(priceQueryForm.getGoodsIdList()) || priceQueryForm.getComponentId() == null) {
            return Lists.newArrayList();
        }

        List<GoodsId> goodsIdList = priceQueryForm.getGoodsIdList();
        List<Long> goodsIds = CollectionUtils.isNotEmpty(goodsIdList) ? goodsIdList.stream().map(IdObject::getValue).collect(Collectors.toList()) : null;
        Integer type = priceQueryForm.getType() != null ? priceQueryForm.getType().getVal() : null;
        Long componentId = priceQueryForm.getComponentId() != null ? priceQueryForm.getComponentId().getValue() : null;

        List<GoodsPriceDO> goodsPriceDOS = goodsPriceMapper.queryGoodsPriceList(goodsIds, type, componentId);

        if (CollectionUtils.isEmpty(goodsPriceDOS)) {
            return Lists.newArrayList();
        }

        return goodsPriceDOS.stream().map(RepositoryGoodsPriceConvert::doToDomain).collect(Collectors.toList());
    }
}
