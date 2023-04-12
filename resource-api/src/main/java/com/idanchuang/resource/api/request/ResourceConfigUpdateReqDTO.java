package com.idanchuang.resource.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wengbinbin
 * @date 2021/3/12
 */

@Data
@Accessors(chain = true)
public class ResourceConfigUpdateReqDTO implements Serializable {
    @ApiModelProperty(value = "资源位配置id")
    @NotNull(message = "资源位配置id不能为空")
    private Long resourceId;

    @ApiModelProperty(value = "业务类型，1 abm，2vtn，3tj")
    private Integer businessType;

    @ApiModelProperty(value = "资源位所在页面id")
    private String pageCode;

    @ApiModelProperty(value = "资源位名称")
    private String resourceName;

    @ApiModelProperty(value = "组件样式限制，0未限制，1限制")
    private Integer componentConfined;

    @ApiModelProperty(value = "所选的组件样式，json格式")
    private String componentType;

    @ApiModelProperty(value = "资源位类型，1url类型，2数字类型，3图片类型，4对象类型")
    private Integer resourceType;

    @ApiModelProperty(value = "资源位scheme，仅针对资源位类型为4的对象类型时适用，json格式")
    private String resourceScheme;

    @ApiModelProperty(value = "资源位状态，0关闭，1开启")
    private Integer resourceStatus;

    @ApiModelProperty(value = "操作者")
    private String operatorUser;

    @ApiModelProperty(value = "操作者id")
    private Integer operatorId;

    @ApiModelProperty(value = "资源位序号，同一个页面不能出现多个序号位置相同的资源位")
    private Integer resourceNumb;

}
