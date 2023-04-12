package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-07 11:37
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsGoodsAddReq implements Serializable {

    private static final long serialVersionUID = 116030051975801284L;

    @ApiModelProperty(value = "组件ID")
    @NotNull(message = "组件ID不能为空")
    private Long componentId;

    @ApiModelProperty(value = "商品ID(SPU)")
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "商品名称")
    @NotNull(message = "商品名称不能为空")
    private String goodsName;

    @ApiModelProperty(value = "商品主图ID")
    @NotNull(message = "商品主图ID不能为空")
    private Long goodsImage;

    @ApiModelProperty(value = "商品副标题")
    @NotNull(message = "商品副标题不能为空")
    private String goodsSubTitle;

    @ApiModelProperty(value = "商品卖点。格式：['卖点1','卖点2']")
    private String salesPoint;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

    @ApiModelProperty(value = "日常价")
    private String normalPrice;

    @ApiModelProperty(value = "专题商品类型")
    private Integer subjectGoodsType;
}
