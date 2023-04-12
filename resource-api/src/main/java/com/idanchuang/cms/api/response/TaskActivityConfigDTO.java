package com.idanchuang.cms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:40
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class TaskActivityConfigDTO implements Serializable {

    private static final long serialVersionUID = -1701054569023407466L;

    @ApiModelProperty(value = "活动id")
    private Long activityId;

    @ApiModelProperty(value = "活动id")
    private String activityName;

    @ApiModelProperty(value = "活动开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "允许等级")
    private List<Integer> levels;
}
