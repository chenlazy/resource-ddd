package com.idanchuang.cms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-03 10:54
 * @Desc: cms页面定义返回类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageSchemaDTO implements Serializable {

    private static final long serialVersionUID = 2826070347851973006L;

    @ApiModelProperty(value = "页面id")
    private Integer id;

    @ApiModelProperty(value = "页面名称")
    private String pageName;

    @ApiModelProperty(value = "页面类型")
    private Integer pageType;

    @ApiModelProperty(value = "操作人id")
    private Integer operatorId;

    @ApiModelProperty(value = "页面状态")
    private Integer status;

    @ApiModelProperty(value = "页面发布版本")
    private String putVersions;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除时间")
    private Integer deleted;
}
