package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lei.liu
 * @date 2021/4/20
 */
@Data
public class GoodsComponentSpu implements Serializable {
    private static final long serialVersionUID = 5899159610571508650L;

    @ApiModelProperty("spuID")
    private Long spuId;

    @ApiModelProperty("默认skuId")
    private Long defaultSkuId;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品副标题")
    private String subTitle;

    @ApiModelProperty("商品状态, 0.草稿, 1.已上架, 2.已下架")
    private Integer status;

    @ApiModelProperty("商品状态主图ID")
    private Long image;

    @ApiModelProperty("商品状态主图URL")
    private String imageUrl;

    @ApiModelProperty("是否禁止加入购物车：0-不禁止，1-禁止")
    private Integer inCart;

    @ApiModelProperty("市场价")
    private BigDecimal marketPrice;
    @ApiModelProperty("含税等级价")
    private BigDecimal amountPrice;
    @ApiModelProperty("商品等级价")
    private BigDecimal goodsPrice;


    @ApiModelProperty("商品可购买等级，逗号分隔")
    private String activityLevel;
}
