package com.idanchuang.resource.api.request.admin;

import com.idanchuang.component.base.page.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 15:33
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class ResourceDataPageReqDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = -4892807589603668678L;

    @ApiModelProperty(value = "资源位id")
    private Long resourceId;
    @ApiModelProperty(value = "资源位名称")
    private String resourceName;
    @ApiModelProperty(value = "用户等级 99所有 1注册用户 2粉卡 3白金 4黑钻 5黑钻plus")
    private Integer level;
    @NotNull(message = "开始时间不能为空")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @NotNull(message = "结束时间不能不为空")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
