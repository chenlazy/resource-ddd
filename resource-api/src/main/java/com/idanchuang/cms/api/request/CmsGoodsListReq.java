package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-23 14:48
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsGoodsListReq implements Serializable {

    private static final long serialVersionUID = 1380667463446565903L;


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
