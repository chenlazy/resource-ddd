package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-23 14:59
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsGoodsList {

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
