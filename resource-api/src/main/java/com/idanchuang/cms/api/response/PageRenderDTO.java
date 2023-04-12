package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/13
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageRenderDTO implements Serializable {
    private static final long serialVersionUID = 279814226252369467L;

    @ApiModelProperty(value = "页面定义ID")
    private Integer id;

    @ApiModelProperty(value = "页面实例ID")
    private Integer pageId;

    @ApiModelProperty(value = "标签id")
    private Integer tagId;

    @ApiModelProperty(value = "页面标题")
    private String pageTitle;

    @ApiModelProperty(value = "后台标题")
    private String backEndTitle;

    @ApiModelProperty(value = "别名")
    private String aliasTitle;

    @ApiModelProperty(value = "平台类型")
    private String platform;

    @ApiModelProperty(value = "是否分享 1-是 0-否")
    private Integer shareFlag;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "专题商品自动下架状态 0不下架 1下架")
    private Integer goodsEnable;

    @ApiModelProperty(value = "商品标签")
    private Long goodsTagId;

    private List<ShareJsonModelDTO> shareList;

    /**
     * 容器列表
     */
    private List<ContainerRenderDTO> containerList;
}
