package com.idanchuang.cms.server.interfaces.adcontentservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: hulk.Wang
 **/
@Data
@Accessors(chain = true)
public class HotAreaDTO implements Serializable {

    private static final long serialVersionUID = 2247407822593800786L;

    /**
     * ID
     */
    @ApiModelProperty("自增ID")
    private Integer id;

    /**
     * 后台展示标题
     */
    @ApiModelProperty("后台展示标题")
    private String title;

    /**
     * 展示图片id
     */
    @ApiModelProperty("展示图片id")
    private Integer image;

    /**
     * 图片数据
     */
    @ApiModelProperty("图片数据")
    private ImageDetailDTO imageDetail;

    /**
     * 跳转方式 0: 不跳转 1:跳商品 2:跳链接
     */
    @ApiModelProperty("跳转方式 0: 不跳转 1:跳商品 2:跳链接")
    private Integer jumpType;

    /**
     * 跳转链接
     */
    @ApiModelProperty("跳转链接")
    private String jumpUrl;

    /**
     * 小程序跳转链接
     */
    @ApiModelProperty("小程序跳转链接")
    private String jumpWx;

    /**
     * 跳转的商品ID
     */
    @ApiModelProperty("跳转的商品ID")
    private Integer goodsId;

    /**
     * 展示的位置 homepage:首页
     */
    @ApiModelProperty("展示的位置 homepage:首页")
    private String displayPosition;

    /**
     * 位置
     */
    @ApiModelProperty("位置")
    private Integer linePosition;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private Integer hotType;

    /**
     * 展示行
     */
    @ApiModelProperty("展示行")
    private Integer displayLine;

    /**
     * 选择的等级
     */
    @ApiModelProperty("选择的等级")
    private String destLevel;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    /**
     * 展示优先级
     */
    @ApiModelProperty("自增ID")
    private Integer displayOrder;

    @ApiModelProperty("字体大小")
    private String fontSize;

    @ApiModelProperty("字体颜色")
    private String fontColor;

    @ApiModelProperty("创建时间")
    private Long currentTime;

    @ApiModelProperty("活动开始时间")
    private Long activityStartAt;

    @ApiModelProperty("活动结束时间")
    private Long activityEndAt;

    /**
     * 分享标题
     */
    @ApiModelProperty("分享标题")
    private String shareTitle;

    /**
     * 分享图片id
     */
    @ApiModelProperty("分享图片id")
    private Integer shareImage;
    /**
     * 分享图片数据
     */
    @ApiModelProperty("分享图片数据")
    private ImageDetailDTO shareImageDetail;

    /**
     * 分享链接
     */
    @ApiModelProperty("分享链接")
    private String shareUrl;

    /**
     * 分享描述
     */
    @ApiModelProperty("分享描述")
    private String shareDesc;

}
