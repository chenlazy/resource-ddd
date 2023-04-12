package com.idanchuang.cms.server.domain.model.cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lei.liu
 * @date 2021/4/21
 */
@Data
public class GoodsComponentSku implements Serializable {
    private static final long serialVersionUID = -4466485530914315106L;

    private Long skuId;

    private Long spuId;

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

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("仓库ID")
    private Integer depotId;

    @ApiModelProperty("0-非套餐, 1-套餐")
    private Integer activity;

    @ApiModelProperty("市场价")
    private BigDecimal marketPrice;

    @ApiModelProperty("含税等级价")
    private BigDecimal amountPrice;

    @ApiModelProperty("原始含税价（不含运费）")
    private BigDecimal originalAmountPrice;

    @ApiModelProperty("含税价 文本")
    private String finalPriceTitle;

    @ApiModelProperty("售价 文本")
    private String originalAmountPriceTitle;

    @ApiModelProperty("0-非预售商品, 1-预售商品")
    private Integer ready;

    @ApiModelProperty(value = "是否禁止加入购物车：0-不禁止，1-禁止")
    private Integer inCart;

    @ApiModelProperty(value = "开售时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime warmUpSaleAt;

    @ApiModelProperty(value = "推荐标签名")
    private String recomLabelName;

    @ApiModelProperty("商品可购买等级，逗号分隔")
    private String activityLevel;

    @ApiModelProperty("货品编码")
    private String goodsCode;
}
