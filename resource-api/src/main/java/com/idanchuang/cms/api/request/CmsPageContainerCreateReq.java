package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 16:32
 * @Desc: cms页面容器创建请求类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageContainerCreateReq implements Serializable {

    private static final long serialVersionUID = -8801920235095688423L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "页面id")
    private Long pageId;

    @ApiModelProperty(value = "容器名称")
    private String containerName;

    @ApiModelProperty(value = "容器状态 1 草稿 2 已发布")
    private Integer status;

    @ApiModelProperty(value = "前端页面格式")
    private String pageStyle;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;
}
