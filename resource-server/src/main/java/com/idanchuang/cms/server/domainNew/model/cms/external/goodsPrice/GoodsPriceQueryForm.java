package com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice;

import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-22 15:23
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GoodsPriceQueryForm {

    /**
     * 价格类型
     */
    private PriceType type;

    /**
     * 商品id列表
     */
    private List<GoodsId> goodsIdList;

    /**
     * 组件id
     */
    private ComponentId componentId;
}
