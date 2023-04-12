package com.idanchuang.cms.server.interfaces.adcontentservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 首页热区添加或编辑
 * @author zhousun
 * @create 2020/11/24
 */
@Data
public class HotAreaRequest {

    @ApiModelProperty("投放平台（1-VTN， 2-ABM单创）")
    @NotNull(message = "请选择投放平台")
    private Integer platform;

    @ApiModelProperty("位置（bannerTop:轮播上方， homepage:宫格导航下banner）")
    @NotBlank(message = "请选择位置")
    private String displayPosition;

    @ApiModelProperty("图片ID")
    @NotNull(message = "请上传图片")
    private Integer image;

    @ApiModelProperty("名称")
    @NotBlank(message = "请填写名称")
    private String title;

    @ApiModelProperty("所在行")
    @NotNull(message = "请选择所在行")
    private Integer displayLine;

    @ApiModelProperty("投放时间")
    @NotNull(message = "请设置投放时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime[] sendAt;

    @ApiModelProperty("行中位置")
    @NotNull(message = "请选择行中位置")
    private Integer linePosition;

    @ApiModelProperty("等级定向（1-注册会员、2-粉卡、3-白金卡、4-黑钻卡、5-黑钻PLUS）")
    @NotNull(message = "请选择等级定向")
    private String[] destLevel;

    @ApiModelProperty("热区类型（1-banner 2-倒计时）")
    @NotNull(message = "请选择热区类型")
    private Integer hotType;

    @ApiModelProperty("跳转方式 0: 不跳转 1:跳商品 2:跳链接")
    @NotNull(message = "请选择跳转方式")
    private Integer jumpType;

    @ApiModelProperty("跳转的商品ID")
    private Integer goodsId;

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

    @ApiModelProperty("分享图片ID")
    private Integer shareImage;

    @ApiModelProperty("活动开始时间")
    private LocalDateTime activityStartAt;

    @ApiModelProperty("活动结束时间")
    private LocalDateTime activityEndAt;

    @ApiModelProperty("字体大小")
    private String fontSize;

    @ApiModelProperty("字体颜色")
    private String fontColor;

}
