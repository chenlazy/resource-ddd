package com.idanchuang.resource.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author wengbinbin
 * @date 2021/3/12
 */

@Data
@Accessors(chain = true)
public class ResourceUnitSearchReqDTO {
    @ApiModelProperty(value = "资源位所在页面id")
    private String pageCode;

    @ApiModelProperty(value = "资源位名称")
    private String resourceName;

    @ApiModelProperty(value = "资源位配置id")
    private Long resourceId;

    @ApiModelProperty(value = "投放起始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "投放结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "投放内容名称")
    private String contentTitle;

    @ApiModelProperty(value = "操作人")
    private String operatorUser;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "启用状态，0关闭，1启动")
    private Integer activeStatus;

    @ApiModelProperty(value = "投放状态，0未投放，1投放中，2过期")
    private Integer workStatus;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum = 1;
}
