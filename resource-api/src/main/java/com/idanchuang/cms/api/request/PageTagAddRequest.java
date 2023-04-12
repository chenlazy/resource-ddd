package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:06
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class PageTagAddRequest implements Serializable {

    private static final long serialVersionUID = 1683349927627858161L;

    @ApiModelProperty("标签名")
    @NotNull(message = "标签名不能为空")
    private String name;

    @ApiModelProperty("平台类型")
    private String platform;

    @ApiModelProperty("页面code")
    private String pageCode;
}
