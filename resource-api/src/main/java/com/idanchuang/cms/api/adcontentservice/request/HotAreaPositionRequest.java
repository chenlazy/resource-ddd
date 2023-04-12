package com.idanchuang.cms.api.adcontentservice.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fym
 * @description :
 * @date 2021/6/10 下午3:39
 */
@Data
public class HotAreaPositionRequest {

    @ApiModelProperty("位置（bannerTop:轮播上方， homepage:宫格导航下banner）")
    private String position;
    @ApiModelProperty("投放平台（1-VTN， 2-ABM单创）")
    private Integer platform;
    @ApiModelProperty("当前时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timePoint;
}
