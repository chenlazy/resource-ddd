package com.idanchuang.resource.api.response.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 14:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class ResourceClickIncidentRespDTO {

    @ApiModelProperty(value = "投放内容id")
    private Long unitId;
    @ApiModelProperty(value = "组件名")
    private String componentName;
    @ApiModelProperty(value = "点击时间集合")
    private List<String> clickIncidentList;
}
