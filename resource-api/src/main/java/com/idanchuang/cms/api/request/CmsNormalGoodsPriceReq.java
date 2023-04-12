package com.idanchuang.cms.api.request;

import com.idanchuang.cms.api.response.PriceDataDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-13 14:38
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsNormalGoodsPriceReq implements Serializable {

    private static final long serialVersionUID = 2495072879506896352L;

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
    private List<PriceDataDTO> priceDataList;
}
