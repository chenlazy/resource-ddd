package com.idanchuang.resource.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wengbinbin
 * @date 2021/3/12
 */

@Data
@Accessors(chain = true)
public class ResourceConfigSearchReqDTO implements Serializable {
    @ApiModelProperty(value = "资源位所在页面id")
    private String pageCode;

    @ApiModelProperty(value = "资源位名称")
    private String resourceName;

    @ApiModelProperty(value = "资源位配置id")
    private Long resourceId;

    @ApiModelProperty(value = "资源位状态")
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
