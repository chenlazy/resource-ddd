package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/13
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoodsComponentPageDTO implements Serializable {
    private static final long serialVersionUID = -5366090723591950457L;

    @ApiModelProperty(value = "页面定义ID")
    private Integer id;

    @ApiModelProperty(value = "页面实例ID")
    private Integer pageId;

    @ApiModelProperty(value = "标签id")
    private Integer tagId;

    @ApiModelProperty(value = "页面标题")
    private String pageTitle;

    @ApiModelProperty(value = "别名")
    private String aliasTitle;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "商品ID列表")
    private List<Long> goodsIdList;
}
