package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:09
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class PageTagUpdateRequest implements Serializable {

    private static final long serialVersionUID = -7415976225261128138L;

    @ApiModelProperty("标签ID")
    @NotNull(message = "标签ID不能为空")
    private Integer id;

    @ApiModelProperty("标签名")
    @NotNull(message = "标签名不能为空")
    private String name;
}
