package com.idanchuang.cms.server.domain.model.cms;

import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-14 15:01
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class GoodsInfoWrapper {

    /**
     * 组件id
     */
    private Long combineGoodsId;

    /**
     * 商品列表
     */
    private List<GoodsInfo> goodsList;
}
