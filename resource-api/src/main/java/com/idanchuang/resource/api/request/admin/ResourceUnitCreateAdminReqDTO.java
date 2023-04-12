package com.idanchuang.resource.api.request.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 14:26
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Accessors(chain = true)
public class ResourceUnitCreateAdminReqDTO implements Serializable {

    private static final long serialVersionUID = 6129320342958716318L;

    @ApiModelProperty(value = "资源位id")
    @NotNull(message = "资源位id不能为空")
    private Long resourceId;

    @ApiModelProperty(value = "投放平台，默认0，0.所有、1.客户端、2.h5、3.小程序")
    @NotNull(message = "投放平台不能为空")
    private List<Integer> platformFrom;

    @ApiModelProperty(value = "投放优先级，默认最低等级1")
    @NotNull(message = "投放优先级不能为空")
    private Integer weight;

    @ApiModelProperty(value = "投放起始时间")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;

    @ApiModelProperty(value = "投放结束时间")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endTime;

    @ApiModelProperty(value = "投放角色")
    @NotNull(message = "投放角色不能为空")
    private List<Integer> visibleRoleS;

    @ApiModelProperty(value = "投放内容名称")
    @NotNull(message = "投放内容名称不能为空")
    private String contentTitle;

    @ApiModelProperty(value = "组件json内容")
    @NotNull(message = "组件json内容不能为空")
    private String componentJsonData;

    @ApiModelProperty("组件名称")
    private String componentName;
}
