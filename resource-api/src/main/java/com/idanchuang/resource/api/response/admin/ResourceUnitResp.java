package com.idanchuang.resource.api.response.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 14:36
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@ApiModel("ResourceUnitRespDTO")
@Data
@Accessors(chain = true)
public class ResourceUnitResp implements Serializable {

    private static final long serialVersionUID = 5565935115460980364L;

    @ApiModelProperty(value = "资源位投放单元id")
    private Long id;

    @ApiModelProperty(value = "资源位id")
    private Long resourceId;

    @ApiModelProperty(value = "资源位名称")
    private String resourceName;

    @ApiModelProperty(value = "页面名称")
    private String pageName;

    @ApiModelProperty("页面code")
    private String pageCode;

    @ApiModelProperty(value = "投放平台，默认0，0.所有、1.客户端、2.h5、3.小程序")
    private List<Integer> platformFrom;

    @ApiModelProperty(value = "投放优先级，默认最低等级1")
    private Integer weight;

    @ApiModelProperty(value = "投放起始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "投放结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "投放角色（人群定向）")
    private List<Integer> visibleRoleS;

    @ApiModelProperty(value = "投放内容名称")
    private String contentTitle;

    @ApiModelProperty(value = "组件json内容")
    private String componentJsonData;

    @ApiModelProperty(value = "启用状态，0关闭，1启动")
    private Integer activeStatus;

    @ApiModelProperty(value = "投放状态，0未开始，1投放中，2过期")
    private Integer workStatus;

    @ApiModelProperty(value = "操作者")
    private String operatorUser;

    @ApiModelProperty(value = "组件样式限制，0未限制，1限制")
    private Integer componentConfined;

    @ApiModelProperty(value = "所选的组件样式，json格式")
    private String componentType;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty("资源位类型1url类型，2数字类型，3图片类型，4对象类型 5数据模型")
    private Integer resourceType;

    @ApiModelProperty("资源位scheme，仅针对资源位类型为4的对象类型时适用，json格式")
    private String resourceScheme;
}
