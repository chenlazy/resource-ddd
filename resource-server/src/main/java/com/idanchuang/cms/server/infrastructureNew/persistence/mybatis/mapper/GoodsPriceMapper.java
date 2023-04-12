package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper;

import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.GoodsPriceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-29 13:53
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface GoodsPriceMapper {

    int batchStoreGoodsPirce(@Param("list") List<GoodsPriceDO> goodsPriceDOS);

    int batchRemoveGoodsPrice(@Param("componentIds") List<Long> componentIds);

    List<GoodsPriceDO> queryGoodsPriceList(@Param("goodsIdList") List<Long> goodsIdList, @Param("type") Integer type,
                                           @Param("componentId") Long componentId);
}
