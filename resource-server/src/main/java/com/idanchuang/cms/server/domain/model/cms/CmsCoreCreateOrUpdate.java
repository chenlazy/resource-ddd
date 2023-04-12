package com.idanchuang.cms.server.domain.model.cms;

import com.idanchuang.cms.server.domain.model.cms.niche.NicheId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-08 17:52
 * @Desc: 创建或者更新
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsCoreCreateOrUpdate {

    @ApiModelProperty(value = "专题id", required = true)
    private Integer id;

    @ApiModelProperty(value = "模版id", required = true)
    private Integer templateId;

    @ApiModelProperty(value = "标签id", required = true)
    private Long tagId;

    @ApiModelProperty(value = "操作人id")
    private Integer operatorId;

    @ApiModelProperty(value = "页面标题", required = true)
    private String pageTitle;

    @ApiModelProperty(value = "页面定义code")
    private String pageCode;

    @ApiModelProperty(value = "后台标题", required = true)
    private String backEndTitle;

    @ApiModelProperty(value = "别名")
    private String aliasTitle;

    @ApiModelProperty(value = "模型名称")
    private String templateName;

    @ApiModelProperty(value = "平台类型", required = true)
    private String platform;

    @ApiModelProperty(value = "前端页面格式")
    private String pageStyle;

    @ApiModelProperty(value = "活动开始时间")
    private String startTime;

    @ApiModelProperty(value = "活动结束时间")
    private String endTime;

    @ApiModelProperty(value = "是否分享 1-是 0-否", required = true)
    private Integer shareFlag;

    @ApiModelProperty(value = "分享范围 0:全部，1：指定")
    private Integer shareScope;

    @ApiModelProperty("分享具体信息")
    private List<CmsCoreShare> shareInfoList;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "商品自动下架标记 0不下架 1下架")
    private Integer goodsEnable;

    @ApiModelProperty(value = "专题商品类型")
    private Integer subjectGoodsType;

    @ApiModelProperty(value = "资源位id合集")
    private List<NicheId> nicheIds;
}
