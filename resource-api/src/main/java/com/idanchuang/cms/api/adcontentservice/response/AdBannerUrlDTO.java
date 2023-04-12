package com.idanchuang.cms.api.adcontentservice.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: lei.liu
 * @date 2021/1/27 10:28
 **/
@Data
public class AdBannerUrlDTO implements Serializable {
    private static final long serialVersionUID = 7698555141199080819L;

    private Integer bannerId;

    @ApiModelProperty("url标题")
    private String urlTitle;

    @ApiModelProperty("跳转的url地址")
    private String url;

    @ApiModelProperty("小程序跳转url")
    private String wxUrl;

    @ApiModelProperty("会员等级")
    private String level;

    @ApiModelProperty("投放开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    @ApiModelProperty("投放结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    @ApiModelProperty("图片ID")
    private Integer imageId;

    @ApiModelProperty("图片Url")
    private String imageUrl;

    @ApiModelProperty("跳转类型。0：不跳转；1：跳商品；2：跳链接")
    private Integer skipType;
}
