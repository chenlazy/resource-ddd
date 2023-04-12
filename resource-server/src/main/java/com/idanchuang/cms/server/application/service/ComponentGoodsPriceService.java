package com.idanchuang.cms.server.application.service;

import com.idanchuang.cms.server.domain.model.cms.ComponentGoodsPrice;
import com.idanchuang.cms.server.domain.model.cms.ComponentGoodsPriceCondition;
import com.idanchuang.cms.server.domain.repository.ComponentGoodsPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 14:15
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class ComponentGoodsPriceService {

    @Resource
    private ComponentGoodsPriceRepository goodsPriceRepository;

    public boolean insert(ComponentGoodsPrice goodsPrice) {
        return goodsPriceRepository.insert(goodsPrice);
    }

    public boolean batchInsert(List<ComponentGoodsPrice> componentGoodsPrices) {
        return goodsPriceRepository.batchInsert(componentGoodsPrices);
    }

    public boolean insertBatch(List<ComponentGoodsPrice> goodsPrice) {
        return goodsPriceRepository.insert(goodsPrice);
    }

    public boolean updateById(ComponentGoodsPrice price) {
        return goodsPriceRepository.updateById(price);
    }

    public List<ComponentGoodsPrice> selectListByComponentId(Long componentId, Integer type) {
        ComponentGoodsPriceCondition condition = new ComponentGoodsPriceCondition();
        condition.setComponentId(componentId);
        condition.setType(type);
        return goodsPriceRepository.selectByCondition(condition);
    }

    public List<ComponentGoodsPrice> selectList(Long componentGoodsId, Integer type) {
        return selectList(Collections.singletonList(componentGoodsId), type);
    }

    public List<ComponentGoodsPrice> selectList(List<Long> goodsIdList, Integer type) {
        ComponentGoodsPriceCondition condition = new ComponentGoodsPriceCondition();
        condition.setGoodsIdList(goodsIdList);
        condition.setType(type);
        return goodsPriceRepository.selectByCondition(condition);
    }
}
