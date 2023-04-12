package com.idanchuang.resource.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author fym
 * @description :
 * @date 2021/3/17 上午11:06
 */
@Data
public class PreviewPutContentReq {

    @NotBlank(message = "资源位所在页面不能为空")
    @ApiModelProperty(value = "资源位所在页面")
    private String pageCode;
    @ApiModelProperty(value = "业务p平台 1 abm 2 vtn")
    private Integer business;
    @ApiModelProperty(value = "用户角色")
    private Integer role;
    @NotNull(message = "预览资源位id不能为空")
    @ApiModelProperty(value = "预览资源位id")
    private Long preResourceId;
    @NotNull(message = "资源位id不能为空")
    @ApiModelProperty(value = "资源位id")
    private Long resourceId;
    @NotNull(message = "内容投放id不能为空")
    @ApiModelProperty(value = "内容投放id")
    private Long unitId;
    @ApiModelProperty(value = "投放平台，默认1、1.客户端、2.H5、3.小程序")
    private String platform;
}
