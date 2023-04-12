package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:07
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class PageTagQueryRequest implements Serializable {

    private static final long serialVersionUID = -1378283083220267558L;

    @ApiModelProperty("当前页 默认值1")
    private int current = 1;
    @ApiModelProperty("每页显示条数 默认值30")
    private int size = 30;

    @ApiModelProperty("标签名")
    private String name;
    @ApiModelProperty("平台类型")
    private String platform;
}
