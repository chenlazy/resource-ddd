package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-13 14:41
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class PriceData {

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
}
