package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-13 14:48
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsNormalGoodsPrice {

    @ApiModelProperty(value = "专题商品ID")
    @NotNull(message = "专题商品ID不能为空")
    private Long subjectGoodsId;

    private Integer type = 1;

    /**
     * 操作人id
     */
    private Long operatorId;

    /**
     * 价格列表
     */
    @ApiModelProperty(value = "专题商品日常价")
    private List<PriceData> priceDataList;
}
