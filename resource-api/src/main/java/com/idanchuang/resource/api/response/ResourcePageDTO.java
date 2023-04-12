package com.idanchuang.resource.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wengbinbin
 * @date 2021/3/22
 */
@Data
public class ResourcePageDTO {
    @ApiModelProperty(value = "页面id")
    private Integer id;
    @ApiModelProperty(value = "页面名称")
    private String pageName;
    @ApiModelProperty(value = "页面编号")
    private String pageCode;
    @ApiModelProperty(value = "页面路径")
    private String pagePath;
}
