package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fym
 * @description :
 * @date 2021/9/8 下午7:05
 */
@Data
public class ActivityPageDTO {

    @ApiModelProperty(value = "页面实例id")
    private Long pageId;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;
}
