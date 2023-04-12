package com.idanchuang.resource.api.request.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 16:26
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Accessors(chain = true)
public class ResourceConfigCreateAdminReqDTO implements Serializable {

    private static final long serialVersionUID = -6296575745085325503L;

    @NotNull(message = "资源位业务类型不能为空")
    @ApiModelProperty(value = "业务类型，1 abm，2vtn，3tj")
    private Integer businessType;

    @NotNull(message = "资源位所在页不能为空")
    @ApiModelProperty(value = "资源位所在页面id")
    private String pageCode;

    @NotEmpty(message = "资源位名称不能为空")
    @ApiModelProperty(value = "资源位名称")
    private String resourceName;

    @NotNull(message = "组件样式限制，0未限制，1限制。不能为空")
    @ApiModelProperty(value = "组件样式限制，0未限制，1限制")
    private Integer componentConfined;

    @ApiModelProperty(value = "所选的组件样式，json格式")
    private String componentType;

    @ApiModelProperty(value = "资源位类型1url类型，2数字类型，3图片类型，4对象类型")
    private Integer resourceType;

    @ApiModelProperty(value = "资源位scheme，仅针对资源位类型为4的对象类型时适用，json格式")
    private String resourceScheme;

    @NotNull(message = "资源位序号不能为空")
    @ApiModelProperty(value = "资源位序号，同一个页面不能出现多个序号位置相同的资源位")
    private Integer resourceNumb;
}
