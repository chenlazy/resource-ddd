package com.idanchuang.cms.server.domainNew.shard.parse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-21 10:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@NoArgsConstructor
public class PriceData {

    /**
     * 用户等级
     */
    private String type;

    /**
     * 价格
     */
    private String price;

    /**
     * 活动积分
     */
    private BigDecimal pointValue;

    /**
     * 活动积分价格
     */
    private String cashValue;

    /**
     * 商品活动等级价
     */
    private String amountPromotionPrice;

    public PriceData(String type, BigDecimal price, BigDecimal pointValue, BigDecimal cashValue,
                        BigDecimal amountPromotionPrice) {
        this.type = type;
        this.pointValue = pointValue;
        this.cashValue = cashValue != null ? cashValue.toString() : null;
        this.price = price != null ? price.toString() : null;
        this.amountPromotionPrice = amountPromotionPrice != null ? amountPromotionPrice.toString() : null;
    }
}
