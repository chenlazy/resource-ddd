package com.idanchuang.cms.server.domainNew.model.cms.component;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPrice;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-21 11:05
 * @Desc: 组件额外信息
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class ComponentExtra {

    /**
     * 商品id列表
     */
    private List<Long> goodsList;

    /**
     * 价格信息
     */
    private List<GoodsPrice> priceList;

    /**
     * 页面code(page_code)
     */
    private PageCode pageCode;

    /**
     * 模版id（page_id）
     */
    private MasterplateId masterplateId;

    /**
     * 返回详情
     */
    private Object details;

    /**
     * 组件类型
     */
    private String type;


    public List<Long> getGoodsList() {
        if (null == this.goodsList) {
            return Lists.newArrayList();
        }
        return goodsList;
    }

    public List<GoodsPrice> getPriceList() {
        if (null == this.priceList) {
            return Lists.newArrayList();
        }
        return priceList;
    }
}
