package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <weiwei.ji@beibei.com>
 * @Date: 2020-12-08 15:03
 * @Desc:
 * @Copyright Beicang Limited. All rights reserved.
 */
@Data
public class GoodsComponentQueryReq implements Serializable {

    private static final long serialVersionUID = -7936802128298928448L;

    /**
     * 组件id
     */
    @ApiModelProperty(value = "组件ID")
    @NotNull(message = "组件id不可为空")
    private Long componentId;

    /**
     * 查询数量
     */
    private Integer limit;
}
