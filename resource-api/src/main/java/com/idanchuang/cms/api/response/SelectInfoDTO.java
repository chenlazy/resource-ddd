package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fym
 * @description :
 * @date 2022/2/23 下午12:22
 */
@Data
public class SelectInfoDTO implements Serializable {

    @ApiModelProperty(value = "圈选类型 1活动 2优惠券 3商品")
    private Integer selectType;
    @ApiModelProperty(value = "圈选id")
    private Integer selectId;

}
