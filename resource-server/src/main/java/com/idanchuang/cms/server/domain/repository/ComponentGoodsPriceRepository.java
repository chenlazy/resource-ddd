package com.idanchuang.cms.server.domain.repository;

import com.idanchuang.cms.server.domain.model.cms.ComponentGoodsPrice;
import com.idanchuang.cms.server.domain.model.cms.ComponentGoodsPriceCondition;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:30
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface ComponentGoodsPriceRepository {

    boolean insert(ComponentGoodsPrice price);

    boolean batchInsert(List<ComponentGoodsPrice> componentGoodsPrices);

    boolean insert(List<ComponentGoodsPrice> price);

    boolean deleteById(Integer id, Integer operatorId, LocalDateTime updateTime);

    boolean updateById(ComponentGoodsPrice price);

    List<ComponentGoodsPrice> selectByCondition(ComponentGoodsPriceCondition condition);

}
