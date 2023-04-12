package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-10-09 11:39
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageTemplateList {

    @ApiModelProperty(value = "页面ID")
    private Integer pageId;

    @ApiModelProperty(value = "模版ID")
    private Integer id;

    @ApiModelProperty(value = "模版名称")
    private String pageTitle;

    @ApiModelProperty(value = "模版状态")
    private Integer pageStatus;

    @ApiModelProperty("当前页 默认值1")
    private Long current;
    @ApiModelProperty("每页显示条数 默认值30")
    private Long size;
}
