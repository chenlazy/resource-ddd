package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-13 14:34
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class PriceDataDTO implements Serializable {

    private static final long serialVersionUID = -1867735069792544158L;

    @ApiModelProperty(value = "用户等级")
    private String type;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty("活动积分")
    private BigDecimal pointValue;

    @ApiModelProperty("活动积分价格")
    private String cashValue;

    @ApiModelProperty(value = "商品活动等级价")
    private String amountPromotionPrice;

    public PriceDataDTO() {}
    public PriceDataDTO(String type, BigDecimal price, BigDecimal pointValue, BigDecimal cashValue,
                      BigDecimal amountPromotionPrice) {
        this.type = type;
        this.price = price != null ? price.toString() : null;
        this.pointValue = pointValue;
        this.cashValue = cashValue != null ? cashValue.toString() : null;
        this.amountPromotionPrice = amountPromotionPrice != null ? amountPromotionPrice.toString() : null;
    }
}
