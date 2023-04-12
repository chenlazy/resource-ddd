package com.idanchuang.resource.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fym
 * @description :
 * @date 2021/3/9 下午5:18
 */
@Data
public class PutResourceDTO {

    @ApiModelProperty(value = "资源位Id")
    private Long id;
    @ApiModelProperty(value = "资源位名称")
    private String resourceName;
    @ApiModelProperty(value = "资源位所在页面")
    private String pageCode;
    @ApiModelProperty(value = "启用状态，0关闭，1启动")
    private Integer resourceStatus;
    @ApiModelProperty(value = "业务类型，1 abm，2vtn，3tj")
    private Integer businessType;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "资源位序号")
    private Integer resourceNumb;
    @ApiModelProperty(value = "内容投放信息")
    private PutInfoDTO putInfoDTO;

}
