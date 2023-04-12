package com.idanchuang.cms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-09 15:06
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageListDTO implements Serializable {

    private static final long serialVersionUID = -3187967723290933873L;

    @ApiModelProperty(value = "专题ID")
    private Long id;

    @ApiModelProperty(value = "页面标题")
    private String pageTitle;

    @ApiModelProperty(value = "后台标题")
    private String describe;

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

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "是否主专题，0-否；1-是 ")
    private Integer masterFlag;

    @ApiModelProperty(value = "页面发布状态：0:草稿，1:发布")
    private Integer status;

    @ApiModelProperty("版本")
    private String version;

    @ApiModelProperty(value = "复制页面使用的模版id")
    private Integer templateId;

    @ApiModelProperty(value = "当前生效模板页面ID")
    private Integer currentPageId;

    @ApiModelProperty(value = "当前生效模板名称")
    private String currentPageTitle;

    @ApiModelProperty(value = "当前生效模板生效时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime currentStartTime;

    @ApiModelProperty(value = "待生效模板数")
    private int pageVersionCount;

    @ApiModelProperty(value = "页面转换按钮文案")
    private String changeButton;
}
