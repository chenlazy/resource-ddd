package com.idanchuang.resource.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fym
 * @description :
 * @date 2021/3/16 下午4:07
 */
@Data
public class PutResourcePageListDTO {

    @ApiModelProperty(value = "资源位Id")
    private Long resourceId;
    @ApiModelProperty(value = "启用状态，0关闭，1启动")
    private Integer resourceStatus;
    @ApiModelProperty(value = "资源位序号")
    private Integer resourceNumb;
}
