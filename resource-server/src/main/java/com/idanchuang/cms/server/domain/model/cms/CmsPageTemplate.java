package com.idanchuang.cms.server.domain.model.cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-10-09 13:39
 * @Desc: 页面实例返回类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageTemplate {

    @ApiModelProperty(value = "模版ID")
    private Integer id;

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

    @ApiModelProperty(value = "操作人id")
    private Integer operatorId;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
