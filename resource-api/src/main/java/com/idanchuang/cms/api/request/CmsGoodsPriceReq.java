package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-13 14:33
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsGoodsPriceReq implements Serializable {

    private static final long serialVersionUID = -3915944973626731355L;


    @ApiModelProperty(value = "专题商品ID")
    @NotNull(message = "专题商品ID不能为空")
    private Long subjectGoodsId;
}
