package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fym
 * @description :
 * @date 2021/9/7 下午3:25
 */
@Data
public class GoodsJsonModelDTO {

    @ApiModelProperty(value = "商品ID(SPU)")
    private Long goodsId;

    @ApiModelProperty(value = "商品名称取值 1实时 2自定义")
    private Integer goodsNameEnable;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品主图ID取值 1实时 2自定义")
    private Integer goodsImageEnable;

    @ApiModelProperty(value = "商品主图ID")
    private Long goodsImage;

    @ApiModelProperty(value = "商品副标题取值 1实时 2自定义")
    private Integer goodsSubTitleEnable;

    @ApiModelProperty(value = "商品副标题")
    private String goodsSubTitle;

    @ApiModelProperty(value = "商品卖点。格式：['卖点1','卖点2']")
    private String salesPoint;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "赠品图片ID")
    private Long giftImage;

    @ApiModelProperty(value = "专题商品类型")
    private Integer subjectGoodsType;

    @ApiModelProperty(value = "商品限购数量")
    private Integer limitNum;

    @ApiModelProperty(value = "销量膨胀百分比")
    private Integer swellPercentage;

    @ApiModelProperty(value = "文本背景图")
    private Long textBgImage;
}
