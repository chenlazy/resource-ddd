package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 16:49
 * @Desc: 页面容器返回类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageContainerDTO implements Serializable {

    private static final long serialVersionUID = 418409214444626530L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "页面实例id")
    private Integer pageId;

    @ApiModelProperty(value = "容器名称")
    private String containerName;

    @ApiModelProperty(value = "容器状态 1 草稿 2 已发布")
    private Integer status;

    @ApiModelProperty(value = "前端页面格式")
    private String pageStyle;

    @ApiModelProperty(value = "操作人id")
    private Integer operatorId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deletedTime;
}
