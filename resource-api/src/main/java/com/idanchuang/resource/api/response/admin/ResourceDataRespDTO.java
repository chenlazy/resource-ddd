package com.idanchuang.resource.api.response.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 14:11
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class ResourceDataRespDTO {

    @ApiModelProperty(value = "折线名")
    private String name;
    @ApiModelProperty(value = "code")
    private Integer code;
    @ApiModelProperty(value = "横坐标数据")
    private List<Integer> data;
}
