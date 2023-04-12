package com.idanchuang.cms.api.request;

import com.idanchuang.component.base.page.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-29 20:37
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageTemplateReq extends PageDTO implements Serializable {

    private static final long serialVersionUID = 2358210314945217260L;

    @ApiModelProperty(value = "页面ID")
    private Integer pageId;

    @ApiModelProperty(value = "模版ID")
    private Integer id;

    @ApiModelProperty(value = "模版名称")
    private String pageTitle;

    @ApiModelProperty(value = "模版状态")
    private Integer pageStatus;
}
