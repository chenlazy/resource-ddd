package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 14:02
 * @Desc: cms页面实例创建请求类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageCreateReq implements Serializable {

    private static final long serialVersionUID = -9116554711451540731L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "页面定义id")
    private Long pageSchemaId;

    @ApiModelProperty(value = "页面名称")
    private String pageName;

    @ApiModelProperty(value = "页面类型")
    private Integer pageType;

    @ApiModelProperty(value = "后台标题")
    private String backEndTitle;

    @ApiModelProperty(value = "标签id")
    private Long tagId;

    @ApiModelProperty(value = "平台类型")
    private String platform;

    @ApiModelProperty(value = "0:未发布 1:已发布 2:已失效")
    private Integer status;

    @ApiModelProperty(value = "容器排序 数组结构")
    private String sort;

    @ApiModelProperty(value = "页面别名")
    private String aliasTitle;

    @ApiModelProperty(value = "是否分享 1-是 0-否")
    private Integer shareFlag;

    @ApiModelProperty(value = "分享具体内容")
    private String shareJson;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "商品自动下架标记 0不下架 1下架'")
    private Integer goodsEnable;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;
}
