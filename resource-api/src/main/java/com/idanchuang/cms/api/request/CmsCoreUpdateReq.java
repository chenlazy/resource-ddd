package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-08 17:13
 * @Desc: cms核心流程更新请求类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsCoreUpdateReq implements Serializable {

    private static final long serialVersionUID = 8120390533122764350L;

    @ApiModelProperty(value = "专题id", required = true)
    @NotNull(message = "专题id不能为空")
    private Integer id;

    @ApiModelProperty(value = "模版id", required = true)
    private Integer templateId;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

    @ApiModelProperty(value = "平台类型", required = true)
    private String platform;

    @ApiModelProperty(value = "页面标题", required = true)
    @NotNull(message = "页面标题不能为空")
    private String pageTitle;

    @ApiModelProperty(value = "后台标题", required = true)
    @NotNull(message = "后台标题不能为空")
    private String describe;

    @ApiModelProperty(value = "模版名称", required = true)
    private String templateName;

    @ApiModelProperty(value = "前端页面格式")
    private String compData;

    @ApiModelProperty(value = "活动开始时间")
    private String startTime;

    @ApiModelProperty(value = "活动结束时间")
    private String endTime;

    @ApiModelProperty(value = "是否分享 1-是 0-否", required = true)
    @NotNull(message = "是否分享不能为空")
    private Integer shareFlag;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "分享图片id")
    private Long shareImg;

    @ApiModelProperty(value = "分享海报id")
    private Long sharePoster;

    @ApiModelProperty(value = "分享海报url")
    private List<String> sharePosterList;

    @ApiModelProperty(value = "分享范围 0：全部, 1:制定")
    private Integer shareScope;

    @ApiModelProperty(value = "页面分享列表")
    private List<CmsCoreShareReq> shareInfoList;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "资源位id合集")
    private List<Long> nicheIds;

    @ApiModelProperty(value = "圈选信息")
    private List<SelectInfoReq> selectInfoList;

    @Data
    public static class SelectInfoReq {

        @ApiModelProperty(value = "圈选类型 1活动 2优惠券 3商品")
        private Integer selectType;
        @ApiModelProperty(value = "圈选id")
        private Integer selectId;
    }
}
