package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 11:40
 * @Desc: 页面定义表更新请求类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageSchemaUpdateReq implements Serializable {

    private static final long serialVersionUID = -5034740082750237759L;

    @ApiModelProperty(value = "页面id")
    private Integer id;

    @ApiModelProperty(value = "页面名称")
    private String pageName;

    @ApiModelProperty(value = "页面类型")
    private Integer pageType;

    @ApiModelProperty(value = "页面发布版本")
    private String putVersions;

    @NotNull
    @ApiModelProperty(value = "操作人id")
    private Integer operatorId;
}
