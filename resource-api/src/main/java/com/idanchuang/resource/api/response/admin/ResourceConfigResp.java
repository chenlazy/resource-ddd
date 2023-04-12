package com.idanchuang.resource.api.response.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-15 18:16
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Accessors(chain = true)
public class ResourceConfigResp {
    @NotNull(message = "资源位配置id")
    @ApiModelProperty(value = "资源位配置id")
    private Long id;

    @ApiModelProperty(value = "业务类型，1 abm，2vtn，3tj")
    private Integer businessType;

    @ApiModelProperty(value = "资源位所在页面id")
    private String pageCode;

    @ApiModelProperty(value = "资源位所在页面名称")
    private String pageName;

    @ApiModelProperty(value = "资源位名称")
    private String resourceName;

    @ApiModelProperty(value = "资源位状态,1开启")
    private Integer resourceStatus;

    @ApiModelProperty(value = "操作者")
    private String operatorUser;

    @ApiModelProperty(value = "资源位序号，同一个页面不能出现多个序号位置相同的资源位")
    private Integer resourceNumb;

    @ApiModelProperty(value = "组件样式限制，0未限制，1限制")
    private Integer componentConfined;

    @ApiModelProperty(value = "所选的组件样式，json格式")
    private String componentType;

    @ApiModelProperty(value = "创建者")
    private String createUser;
    @ApiModelProperty("资源位类型1url类型，2数字类型，3图片类型，4对象类型，5数据模型")
    private Integer resourceType;
    @ApiModelProperty("资源位scheme，仅针对资源位类型为4的对象类型时适用，json格式")
    private String resourceScheme;
}
