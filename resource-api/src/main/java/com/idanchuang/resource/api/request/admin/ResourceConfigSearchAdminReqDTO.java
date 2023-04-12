package com.idanchuang.resource.api.request.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 16:28
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Accessors(chain = true)
public class ResourceConfigSearchAdminReqDTO implements Serializable {

    @ApiModelProperty(value = "资源位所在页面编号")
    private String pageCode;

    @ApiModelProperty(value = "资源位名称")
    private String resourceName;

    @ApiModelProperty(value = "资源位配置id")
    private Long resourceId;

    @ApiModelProperty(value = "资源位状态，0关闭，1开启")
    private Integer resourceStatus;

    @ApiModelProperty(value = "操作人")
    private String operatorUser;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "资源位序号，同一个页面不能出现多个序号位置相同的资源位")
    private Integer resourceNumb;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum = 1;
}
