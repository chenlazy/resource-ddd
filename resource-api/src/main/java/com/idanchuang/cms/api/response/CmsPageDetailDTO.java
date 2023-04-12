package com.idanchuang.cms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-10 10:36
 * @Desc: 文章详情页
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageDetailDTO implements Serializable {

    private static final long serialVersionUID = 3374857723131590652L;

    @ApiModelProperty(value = "专题ID")
    private Integer id;

    @ApiModelProperty(value = "标签id")
    private Long tagId;

    @ApiModelProperty(value = "标签名称")
    private String tagName;

    @ApiModelProperty(value = "app端标题")
    private String pageTitle;

    @ApiModelProperty(value = "页面名称")
    private String describe;

    @ApiModelProperty(value = "模版名称")
    private String templateName;

    @ApiModelProperty(value = "别名")
    private String aliasTitle;

    @ApiModelProperty(value = "平台类型")
    private String platform;

    @ApiModelProperty(value = "是否分享 1-是 0-否")
    private Integer shareFlag;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "分享图片")
    private ShareImgDTO shareImg;

    @ApiModelProperty(value = "分享视频")
    private ShareImgDTO sharePoster;

    @ApiModelProperty(value = "分享海报url数组")
    private List<String> sharePosterList;

    @ApiModelProperty(value = "分享范围 0：全部, 1:制定")
    private Integer shareScope;

    @ApiModelProperty(value = "分享信息")
    private List<CmsShareModelDTO> shareInfoList;

    @ApiModelProperty(value = "前端页面格式")
    private String pageStyle;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "页面发布状态：0:草稿，1:发布")
    private Integer status;

    @ApiModelProperty(value = "父专题ID，无父专题则为0")
    private Long parentId;

    @ApiModelProperty(value = "专题商品自动下架状态 0不下架 1下架")
    private Integer goodsEnable;

    @ApiModelProperty(value = "自动下架时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime goodsEnableTime;

    @ApiModelProperty(value = "商品标签")
    private Long goodsTagId;

    @ApiModelProperty(value = "页面pageCode")
    private String pageCode;

    @ApiModelProperty(value = "组件数量")
    private Integer compTotal;

    @ApiModelProperty(value = "模版圈选信息")
    private List<SelectInfoDTO> selectInfoDTOS;
}
