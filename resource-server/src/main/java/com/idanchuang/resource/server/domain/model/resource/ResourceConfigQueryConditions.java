package com.idanchuang.resource.server.domain.model.resource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配置的查询条件
 */
@Data
public class ResourceConfigQueryConditions {
    @ApiModelProperty(value = "资源位所在页面id")
    private String pageCode;

    @ApiModelProperty(value = "资源位名称")
    private String resourceName;

    @ApiModelProperty(value = "资源位配置id")
    private Long resourceId;

    @ApiModelProperty(value = "资源位状态")
    private Integer resourceStatus;

    @ApiModelProperty(value = "操作人")
    private String operatorUser;

    private String createUser;

    private Integer resourceNumb;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum = 1;
}
