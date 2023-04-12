package com.idanchuang.cms.server.infrastructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-09 17:47
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CmsPageSchemaListDO {

    @ApiModelProperty(value = "专题ID")
    private Long id;

    @ApiModelProperty(value = "页面code")
    private String pageCode;

    @ApiModelProperty(value = "页面标题")
    private String pageName;

    @ApiModelProperty(value = "后台标题")
    private String backEndTitle;

    @ApiModelProperty(value = "别名")
    private String aliasTitle;

    @ApiModelProperty(value = "平台类型")
    private String platform;

    @ApiModelProperty(value = "活动开始时间")
    private String startTime;

    @ApiModelProperty(value = "活动结束时间")
    private String endTime;

    @ApiModelProperty(value = "操作人ID")
    private Long operatorId;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "分享标记")
    private Integer shareFlag;

    @ApiModelProperty(value = "分享信息")
    private String shareJson;

    @ApiModelProperty(value = "标签id")
    private Integer tagId;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否主专题，0-否；1-是 ")
    private Integer masterFlag;

    @ApiModelProperty(value = "页面发布状态：0:草稿，1:发布")
    private Integer status;

    private String putVersions;

}
