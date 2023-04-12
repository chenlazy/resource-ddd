package com.idanchuang.resource.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author wengbinbin
 * @date 2021/3/12
 */

@Data
@Accessors(chain = true)
public class ResourceBusinessRespDTO implements Serializable {
    @ApiModelProperty(value = "业务类型，1 abm，2vtn，3tj")
    private Integer id;
    @ApiModelProperty(value = "业务名称")
    private String businessName;
    @ApiModelProperty(value = "业务其对应页面")
    private List<ResourcePageDTO> pages;

}
