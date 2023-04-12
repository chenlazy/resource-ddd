package com.idanchuang.resource.api.response.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 14:12
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class ResourceDataManagePageRespDTO implements Serializable {

    private static final long serialVersionUID = 835005280199045940L;

    @ApiModelProperty(value = "日期")
    private String dateTime;
    @ApiModelProperty(value = "用户")
    private String level;
    @ApiModelProperty(value = "点击事件")
    private String clickIncident;
    @ApiModelProperty(value = "曝光pv")
    private Integer expPv;
    @ApiModelProperty(value = "曝光uv")
    private Integer expUv;
    @ApiModelProperty(value = "点击pv")
    private Integer clickPv;
    @ApiModelProperty(value = "点击uv")
    private Integer clickUv;
}
