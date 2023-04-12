package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-03 10:53
 * @Desc: cms页面定义请求类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageSchemaCreateReq implements Serializable {

    private static final long serialVersionUID = -8380472450351301742L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "标签id")
    private Long tagId;

    @ApiModelProperty(value = "页面名称")
    private String pageName;

    @ApiModelProperty(value = "页面类型")
    private Integer pageType;

    @ApiModelProperty(value = "页面发布版本")
    private String putVersions;

    @NotNull
    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

}
