package com.idanchuang.resource.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fym
 * @description :
 * @date 2021/3/9 下午5:45
 */
@Data
public class PutInfoDTO {

    @ApiModelProperty(value = "内容投放Id")
    private Long id;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "启用状态，0关闭，1启动")
    private Integer activeStatus;
    @ApiModelProperty(value = "资源位Id")
    private Long resourceId;
    @ApiModelProperty(value = "投放平台，默认0，0.所有、1.客户端、2.H5、3.小程序")
    private String platformFrom;
    @ApiModelProperty(value = "投放优先级，默认最低等级1")
    private Integer weight;
    @ApiModelProperty(value = "投放起始时间")
    private LocalDateTime startTime;
    @ApiModelProperty(value = "投放结束时间")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "投放角色")
    private String visibleRoleS;
    @ApiModelProperty(value = "投放内容名称")
    private String contentTitle;
    @ApiModelProperty(value = "组件json内容")
    private String componentJsonData;
}
