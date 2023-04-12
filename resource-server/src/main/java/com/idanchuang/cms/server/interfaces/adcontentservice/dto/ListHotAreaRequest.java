package com.idanchuang.cms.server.interfaces.adcontentservice.dto;

import com.idanchuang.component.base.page.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 首页热区筛选
 * @author zhousun
 * @create 2020/11/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListHotAreaRequest extends PageDTO {

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("投放平台 1-VTN 2-ABM单创")
    private Integer platform;

    @ApiModelProperty("位置")
    private String displayPosition;

    @ApiModelProperty("所在行")
    private Integer displayLine;

    @ApiModelProperty("行中位置")
    private Integer linePosition;

    @ApiModelProperty("状态 0-未发布 1-已发布")
    private Integer status;

    @ApiModelProperty("等级定向 1-注册用户 2-粉卡 3-白金卡 4-黑钻卡 5-黑钻PLUS")
    private String destLevel;

    @ApiModelProperty("热区类型 1-Banner 2-倒计时")
    private Integer hotType;

}
