package com.idanchuang.cms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-29 20:30
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageTemplateDTO implements Serializable {

    private static final long serialVersionUID = 4442910961193889792L;

    @ApiModelProperty(value = "模版ID")
    private Long id;

    @ApiModelProperty(value = "模版名称")
    private String pageTitle;

    @ApiModelProperty(value = "活动开始时间")
    private String startTime;

    @ApiModelProperty(value = "活动结束时间")
    private String endTime;

    @ApiModelProperty(value = "模版状态")
    private Integer status;

    @ApiModelProperty(value = "app端标题")
    private String describe;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "创建人")
    private String creator;
}
