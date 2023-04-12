package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 上午11:11
 */
@Data
public class CreateNumberReq {

    /**
     * 号码类型 1组件 2容器 3模版 4页面
     * ConstructionTypeEnum
     */
    @NotNull
    @ApiModelProperty(value = "号码类型 1组件 2容器 3模版 4页面")
    private Integer numberType;

    @NotNull
    @ApiModelProperty(value = "发号数量")
    private Integer num;

}
