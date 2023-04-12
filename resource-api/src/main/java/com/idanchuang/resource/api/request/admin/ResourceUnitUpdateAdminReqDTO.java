package com.idanchuang.resource.api.request.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 14:43
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Accessors(chain = true)
public class ResourceUnitUpdateAdminReqDTO implements Serializable {

    private static final long serialVersionUID = -4971773495356130032L;

    @ApiModelProperty(value = "资源位投放单元id")
    @NotNull(message = "资源位投放单元id不能为空")
    private Long unitId;

    @ApiModelProperty(value = "资源位id")
    private Long resourceId;

    @ApiModelProperty(value = "投放平台，默认0，0.所有、1.客户端、2.h5、3.小程序")
    private List<Integer> platformFrom;

    @ApiModelProperty(value = "投放优先级，默认最低等级1")
    private Integer weight;

    @ApiModelProperty(value = "投放起始时间")
    private String startTime;

    @ApiModelProperty(value = "投放结束时间")
    private String endTime;

    @ApiModelProperty(value = "投放角色")
    private List<Integer> visibleRoleS;

    @ApiModelProperty(value = "投放内容名称")
    private String contentTitle;

    @ApiModelProperty(value = "组件json内容")
    private String componentJsonData;

    @ApiModelProperty("启用状态，0关闭，1启动")
    private Integer activeStatus;

    @ApiModelProperty(value = "组件名称")
    private String componentName;
}
