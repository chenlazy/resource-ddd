package com.idanchuang.resource.api.request.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 15:23
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class ResourceDataUnitReqDTO implements Serializable {

    private static final long serialVersionUID = -1422305439943445421L;

    @ApiModelProperty(value = "内容投放id")
    private Long unitId;
    @ApiModelProperty(value = "内容投放名称")
    private String contentTitle;
    @NotNull(message = "用户等级不能空")
    @ApiModelProperty(value = "用户等级 0所有 1注册用户 2粉卡 3白金 4黑钻 5黑钻plus")
    private Integer level;
    @NotNull(message = "开始时间不能空")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @NotNull(message = "结束时间不能空")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
