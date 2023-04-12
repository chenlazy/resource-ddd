package com.idanchuang.cms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-23 14:37
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsGoodsDTO implements Serializable {

    private static final long serialVersionUID = 4879181183352235373L;

    private Long id;

    @ApiModelProperty(value = "组件ID")
    private Long componentId;

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

    @ApiModelProperty(value = "商品主图URL")
    private String goodsImageUrl;

    @ApiModelProperty(value = "商品副标题取值 1实时 2自定义")
    private Integer goodsSubTitleEnable;

    @ApiModelProperty(value = "商品副标题")
    private String goodsSubTitle;

    @ApiModelProperty(value = "商品卖点。格式：['卖点1','卖点2']")
    private List<String> salesPoint;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "日常价")
    private List<PriceData> normalPrice;

    @ApiModelProperty(value = "商品价")
    private List<PriceData> price;

    @ApiModelProperty(value = "商品市场价, 单位元")
    private String marketPrice;

    @ApiModelProperty(value = "是否开启预热 0-否 1-是")
    private Integer enableWarmUpSet;

    @ApiModelProperty(value = "预热开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM月dd日 HH:mm")
    private LocalDateTime warmUpPrepareAt;

    @ApiModelProperty(value = "开售时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM月dd日 HH:mm")
    private LocalDateTime warmUpSaleAt;

    @ApiModelProperty(value = "商品状态, 0.草稿, 1.已上架, 2.已下架")
    private Integer status;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "是否包邮")
    private String shippingText;

    @ApiModelProperty(value = "是否含税")
    private String taxFeeTitle;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "赠品图地址")
    private String giftImageUrl;

    @ApiModelProperty(value = "赠品图id")
    private Long giftImage;

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

    @ApiModelProperty(value = "专题商品类型")
    private Integer subjectGoodsType;

    @ApiModelProperty("商品中心，是否禁止加入购物车：0-不禁止，1-禁止")
    private Integer goodsInCar;

    @ApiModelProperty("秒杀商品限购数量")
    private Integer limitNum;

    @ApiModelProperty(value = "销量膨胀百分比")
    private Integer swellPercentage;

    @ApiModelProperty(value = "文本背景图")
    private String textBgImage;

    public Integer getUserLimitFlag() {
        if (this.userUpperLimit != null && this.userUpperLimit.intValue() > 0) {
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

    public LocalDateTime getWarmUpPrepareAt() {
        if (this.warmUpPrepareAt == null) {
            return null;
        } else if (this.warmUpSaleAt == null) {
            return this.warmUpPrepareAt;
        } else {
            return LocalDateTime.now().isBefore(this.warmUpSaleAt) ? this.warmUpPrepareAt : null;
        }
    }

    public Integer getGoodsInCar() {

        // 商品库存为0，禁止加购
        if (stock == null || stock.intValue() <= 0) {
            return 1;
        }

        // 商品已下架，禁止加购
        if (status == null || status.intValue() == 2) {
            return 1;
        }
        return goodsInCar;
    }

    @Data
    public static class PriceData implements Serializable {

        private static final long serialVersionUID = -3406653633890749977L;

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

        public PriceData() {}
        public PriceData(String type, BigDecimal price, BigDecimal pointValue, BigDecimal cashValue, BigDecimal amountPromotionPrice) {
            this.type = type;
            this.price = price != null ? price.toString() : null;
            this.pointValue = pointValue;
            this.cashValue = cashValue != null ? cashValue.toString() : null;
            this.amountPromotionPrice = amountPromotionPrice != null ? amountPromotionPrice.toString() : null;
        }
    }

}
