package com.idanchuang.cms.server.interfaces.adcontentservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhousun
 * @create 2020/11/30
 */
@Data
public class HotAreaVO {

    @ApiModelProperty("自增ID")
    private Integer id;

    @ApiModelProperty("投放平台（1-VTN， 2-ABM单创）")
    private Integer platform;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("位置（bannerTop:轮播上方， homepage:宫格导航下banner）")
    private String displayPosition;

    @ApiModelProperty("所在行")
    private Integer displayLine;

    @ApiModelProperty("热区类型（1-banner 2-倒计时）")
    private Integer hotType;

    @ApiModelProperty(value = "投放开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    @ApiModelProperty(value = "投放结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    @ApiModelProperty("行中位置")
    private Integer linePosition;

    @ApiModelProperty("等级定向（1-注册会员、2-粉卡、3-白金卡、4-黑钻卡、5-黑钻PLUS）")
    private String destLevel;

    @ApiModelProperty("状态（0-未发布 1-已发布）")
    private Integer status;

    // - - - -

    @ApiModelProperty("图片")
    private ImageVO image;

    // - Banner

    @ApiModelProperty(value = "跳转方式 0: 不跳转 1:跳商品 2:跳链接")
    private Integer jumpType;

    // - 跳商品

    @ApiModelProperty("跳转的商品ID")
    private Integer goodsId;

    // - 跳链接

    @ApiModelProperty("跳转链接")
    private String jumpUrl;

    @ApiModelProperty("小程序跳转地址")
    private String jumpWx;

    @ApiModelProperty("分享链接")
    private String shareUrl;

    @ApiModelProperty("分享标题")
    private String shareTitle;

    @ApiModelProperty("分享描述")
    private String shareDesc;

    @ApiModelProperty("分享图")
    private ImageVO shareImage;

    // - 倒计时

    @ApiModelProperty("活动开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityStartAt;

    @ApiModelProperty("活动结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityEndAt;

    @ApiModelProperty("字体大小")
    private String fontSize;

    @ApiModelProperty("字体颜色")
    private String fontColor;

    @Data
    public static class ImageVO {
        @ApiModelProperty("图片ID")
        private Integer id;

        @ApiModelProperty("图片地址")
        private String fileUrl;
    }

}
