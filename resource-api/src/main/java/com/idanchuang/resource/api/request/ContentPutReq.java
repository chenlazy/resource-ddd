package com.idanchuang.resource.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/3/9 下午5:15
 */
@Data
public class ContentPutReq {

    @NotEmpty(message = "资源位Ids不能为空")
    @ApiModelProperty(value = "资源位Id")
    private List<Long> resourceIdList;
    @NotBlank(message = "资源位所在页面不能为空")
    @ApiModelProperty(value = "资源位所在页面")
    private String pageCode;
    @ApiModelProperty(value = "业务p平台 1 abm 2 vtn")
    private Integer business;
    @ApiModelProperty(value = "投放平台，默认1、1.客户端、2.H5、3.小程序")
    private String platform;
    @ApiModelProperty(value = "用户角色")
    private Integer role;
}
