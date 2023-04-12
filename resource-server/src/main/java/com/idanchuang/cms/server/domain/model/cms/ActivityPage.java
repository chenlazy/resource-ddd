package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fym
 * @description :
 * @date 2021/9/22 上午9:50
 */
@Data
public class ActivityPage {

    @ApiModelProperty(value = "页面实例id")
    private Long pageId;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;

}
