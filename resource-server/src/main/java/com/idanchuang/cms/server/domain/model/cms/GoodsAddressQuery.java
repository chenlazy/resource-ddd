package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lei.liu
 * @date 2021/4/20
 */
@Data
public class GoodsAddressQuery implements Serializable {
    private static final long serialVersionUID = -2305169262579401411L;

    @ApiModelProperty(
            value = "地址省份",
            required = true
    )
    @NotNull(
            message = "地址省份不能为空"
    )
    private int province;
    @ApiModelProperty(
            value = "地址城市",
            required = true
    )
    @NotNull(
            message = "地址城市不能为空"
    )
    private int city;
    @ApiModelProperty(
            value = "国家",
            required = true
    )
    @NotNull(
            message = "国家不能为空"
    )
    private int country;
    @ApiModelProperty(
            value = "地址区",
            required = true
    )
    @NotNull(
            message = "地址区不能为空"
    )
    private int district;

}
