package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-15 10:23
 * @Desc: 通知请求类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class ActivityNoticeReq implements Serializable {

    private static final long serialVersionUID = 5460264998391755504L;

    @NotNull
    @ApiModelProperty(value = "活动ID")
    private Integer activityId;

    @NotNull
    @ApiModelProperty(value = "活动类型")
    private Integer activityType;

    @ApiModelProperty(value = "通知的具体信息")
    private String noticeInfo;
}
