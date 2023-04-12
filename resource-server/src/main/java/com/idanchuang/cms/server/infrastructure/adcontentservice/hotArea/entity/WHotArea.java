package com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhousun
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="WHotArea对象", description="")
public class WHotArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer platform;

    @ApiModelProperty(value = "后台展示标题")
    private String title;

    @ApiModelProperty(value = "展示图片id")
    private Integer image;

    @ApiModelProperty(value = "跳转方式 0: 不跳转 1:跳商品 2:跳链接")
    private Integer jumpType;

    @ApiModelProperty(value = "跳转链接")
    private String jumpUrl;

    @ApiModelProperty(value = "小程序跳转地址")
    private String jumpWx;

    @ApiModelProperty(value = "跳转的商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "展示的位置 homepage:首页")
    private String displayPosition;

    @ApiModelProperty(value = "展示行")
    private Integer displayLine;

    @ApiModelProperty(value = "行中位置 默认为1 从坐往右")
    private Integer linePosition;

    @ApiModelProperty(value = "展示类型 1 banner 2 倒计时")
    private Integer hotType;

    @ApiModelProperty(value = "倒计时字体")
    private String fontColor;

    @ApiModelProperty(value = "倒计时字体大小")
    private String fontSize;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime activityStartAt;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime activityEndAt;

    @ApiModelProperty(value = "选择的等级")
    private String destLevel;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startAt;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endAt;

    @ApiModelProperty(value = "展示优先级")
    private Integer displayOrder;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "分享图片id")
    private Integer shareImage;

    @ApiModelProperty(value = "分享链接")
    private String shareUrl;

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic (value = "null", delval = "now()")
    private LocalDateTime deletedAt;


}
