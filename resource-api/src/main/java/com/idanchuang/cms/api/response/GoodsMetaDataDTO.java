package com.idanchuang.cms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 17:04
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class GoodsMetaDataDTO implements Serializable {

    private static final long serialVersionUID = -8775026167650122915L;


    private Long id;

    @ApiModelProperty(value = "组件ID")
    private Long componentId;

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品名称取值 1实时 2自定义")
    private Integer goodsNameEnable;

    @ApiModelProperty(value = "商品主图ID")
    private Long goodsImage;

    @ApiModelProperty(value = "商品主图ID取值 1实时 2自定义")
    private Integer goodsImageEnable;

    @ApiModelProperty(value = "商品主图URL")
    private String goodsImageUrl;

    @ApiModelProperty(value = "商品副标题")
    private String goodsSubTitle;

    @ApiModelProperty(value = "商品副标题取值 1实时 2自定义")
    private Integer goodsSubTitleEnable;

    @ApiModelProperty(value = "商品价")
    private List<PriceDataDTO> price;

    @ApiModelProperty(value = "日常价")
    private List<PriceDataDTO> normalPrice;

    /**
     * 是否开启预热 0-否 1-是
     */
    @ApiModelProperty(value = "是否开启预热 0-否 1-是")
    private Integer enableWarmUpSet;

    @ApiModelProperty(value = "预热开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM月dd日 HH:mm")
    private LocalDateTime warmUpPrepareAt;

    @ApiModelProperty(value = "开售时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM月dd日 HH:mm")
    private LocalDateTime warmUpSaleAt;

    /**
     * 商品状态, 0.草稿, 1.已上架, 2.已下架
     */
    @ApiModelProperty(value = "商品状态, 0.草稿, 1.已上架, 2.已下架")
    private Integer status;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Integer stock;

    /**
     * 商品市场价, 单位元
     */
    @ApiModelProperty(value = "商品市场价, 单位元")
    private BigDecimal marketPrice;

    @JsonIgnore
    private Long skuId;

    @ApiModelProperty("限购开始时间")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime userLimitStartAt;
    @ApiModelProperty("限购结束时间")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime userLimitEndAt;
    @ApiModelProperty("限购上限")
    private Integer userUpperLimit;

    @ApiModelProperty("限购标志，0不限购；1限购")
    private Integer userLimitFlag;

    @ApiModelProperty("积分活动状态,-1已过期,0未开始,1进行中")
    private Integer pointStatus;
    @ApiModelProperty("积分活动开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM月dd日 HH:mm")
    private LocalDateTime pointBeginTime;
    @ApiModelProperty("积分活动结束时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime pointEndTime;

    @ApiModelProperty(value = "积分活动支付类型:1,纯积分兑换;2,积分+现金")
    private Integer payType;

    @ApiModelProperty("商品中心，是否禁止加入购物车：0-不禁止，1-禁止")
    private Integer goodsInCar;

    @ApiModelProperty(value = "专题商品类型。1:普通商品；2:积分商品。默认1")
    private Integer subjectGoodsType;

    @ApiModelProperty("是否包邮，部分地区也算包邮。慎用,true:包邮，false：不包邮")
    private Boolean goodsContainShipping;

    @ApiModelProperty("是否包邮")
    private String shippingText;

    @ApiModelProperty("品牌logo")
    private String brandUrl;

    public Integer getUserLimitFlag() {
        if (this.userUpperLimit != null && this.userUpperLimit > 0) {
            return 1;
        }

        if (this.userLimitStartAt != null || this.userLimitEndAt != null) {
            if (LocalDateTime.now().isBefore(this.userLimitStartAt) && LocalDateTime.now().isAfter(this.userLimitEndAt)) {
                return 0;
            }
            return 1;
        }
        return 0;
    }
}
