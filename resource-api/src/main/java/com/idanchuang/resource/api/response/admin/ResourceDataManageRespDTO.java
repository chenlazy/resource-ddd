package com.idanchuang.resource.api.response.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 14:10
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class ResourceDataManageRespDTO {

    @ApiModelProperty(value = "数据展示纬度")
    private List<String> pilColumnLegendData;
    @ApiModelProperty(value = "横坐标列表")
    private List<String> pilColumnXAxisData;
    @ApiModelProperty(value = "统计数据集合")
    private List<ResourceDataRespDTO> pilColumnSeriesData;
}
