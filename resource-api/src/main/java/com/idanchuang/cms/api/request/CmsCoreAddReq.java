package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-07 11:35
 * @Desc: cms核心流程新增请求类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsCoreAddReq implements Serializable {

    private static final long serialVersionUID = -5170280986460476489L;

    @ApiModelProperty(value = "标签id", required = true)
    @NotNull(message = "标签id不能为空")
    private Long tagId;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

    @ApiModelProperty(value = "页面标题", required = true)
    @NotNull(message = "页面标题不能为空")
    private String pageTitle;

    @ApiModelProperty(value = "后台标题", required = true)
    @NotNull(message = "后台标题不能为空")
    private String describe;

    @ApiModelProperty(value = "别名")
    private String aliasTitle;

    @ApiModelProperty(value = "平台类型", required = true)
    @NotNull(message = "平台类型不能为空")
    private String platform;

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

    @ApiModelProperty(value = "分享范围 0：全部, 1:制定")
    private Integer shareScope;

    @ApiModelProperty(value = "页面分享列表")
    private List<CmsCoreShareReq> shareInfoList;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "专题商品类型")
    private Integer subjectGoodsType;


    @ApiModelProperty(value = "资源位id合集")
    private List<Long> nicheIds;

    public String getPlatform() {
        return StringUtils.hasText(this.platform) ? this.platform.toUpperCase() : null;
    }
}
