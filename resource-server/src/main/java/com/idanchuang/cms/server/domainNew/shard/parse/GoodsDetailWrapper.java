package com.idanchuang.cms.server.domainNew.shard.parse;

import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-21 10:57
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class GoodsDetailWrapper {

    /**
     * 组件id
     */
    private Long combineGoodsId;

    /**
     * 商品列表
     */
    private List<GoodsDetail> goodsList;

    /**
     * 组件关联商品id
     */
    private List<Long> spuIds;

    /**
     * 圈品id
     */
    private Long bizId;
}
