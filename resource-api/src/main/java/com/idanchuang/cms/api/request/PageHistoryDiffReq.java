package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-07 10:09
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class PageHistoryDiffReq {

    @ApiModelProperty(value = "页面编码")
    private String pageCode;

    @ApiModelProperty(value = "页面id")
    private Integer pageId;

    @ApiModelProperty(value = "模版id")
    private Integer masterplateId;
}
