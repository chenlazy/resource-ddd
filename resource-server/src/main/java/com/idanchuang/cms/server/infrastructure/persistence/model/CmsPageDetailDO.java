package com.idanchuang.cms.server.infrastructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.idanchuang.cms.api.response.ShareImgDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-10 11:19
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageDetailDO {

    @ApiModelProperty(value = "专题ID")
    private Integer id;

    @ApiModelProperty(value = "标签id")
    private Long tagId;

    @ApiModelProperty(value = "页面名称")
    private String pageName;

    @ApiModelProperty(value = "模版名称")
    private String backEndTitle;

    @ApiModelProperty(value = "app端标题")
    private String pageTitle;

    @ApiModelProperty(value = "别名")
    private String aliasTitle;

    @ApiModelProperty(value = "平台类型")
    private String platform;

    @ApiModelProperty(value = "是否分享 1-是 0-否")
    private Integer shareFlag;

    @ApiModelProperty(value = "分享信息")
    private String shareJson;

    @ApiModelProperty(value = "页面实例id")
    private Integer pageId;

    @ApiModelProperty(value = "排序")
    private List<String> sort;

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

    /**
     * 页面发布版本
     */
    private String putVersions;

    /**
     * 页面pageCode
     */
    private String pageCode;
}
