package com.idanchuang.cms.api.adcontentservice.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 首页热区信息
 * @author zhousun
 * @create 2020/11/24
 */
@Data
public class HotAreaAdminDTO {

    @ApiModelProperty("自增ID")
    private Integer id;

    @ApiModelProperty("后台展示标题")
    private String title;

    @ApiModelProperty("展示行")
    private Integer displayLine;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startAt;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endAt;

    @ApiModelProperty("展示的位置 homepage:首页")
    private String displayPosition;

    @ApiModelProperty("选择的等级")
    private String destLevel;

    @ApiModelProperty("行中位置")
    private Integer linePosition;

    @ApiModelProperty("跳转的商品ID")
    private Integer goodsId;

    @ApiModelProperty("分享链接")
    private String shareUrl;

    @ApiModelProperty("分享标题")
    private String shareTitle;

    @ApiModelProperty("分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "跳转方式 0: 不跳转 1:跳商品 2:跳链接")
    private Integer jumpType;

    @ApiModelProperty("跳转链接")
    private String jumpUrl;

    @ApiModelProperty("小程序跳转地址")
    private String jumpWx;

    @ApiModelProperty("发布状态")
    private Integer status;

    @ApiModelProperty("展示类别")
    private Integer hotType;

    @ApiModelProperty("字体颜色")
    private String fontColor;

    @ApiModelProperty("字体大小")
    private String fontSize;

    @ApiModelProperty("活动开始时间")
    private LocalDateTime activityStartAt;

    @ApiModelProperty("活动结束时间")
    private LocalDateTime activityEndAt;

    @ApiModelProperty("平台类别")
    private Integer platform;

    @ApiModelProperty(value = "展示图片id")
    private Integer image;

    @ApiModelProperty(value = "分享图片id")
    private Integer shareImage;

    @ApiModelProperty(value = "展示优先级")
    private Integer displayOrder;

}
