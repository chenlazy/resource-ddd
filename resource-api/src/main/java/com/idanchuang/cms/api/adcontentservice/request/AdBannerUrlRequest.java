package com.idanchuang.cms.api.adcontentservice.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: lei.liu
 * @date 2021/1/27 10:42
 **/
@Data
public class AdBannerUrlRequest implements Serializable {
    private static final long serialVersionUID = 9209271352627431876L;

    @ApiModelProperty(value = "广告图分类key")
    @NotNull(message = "广告图分类key不能为空")
    @NotEmpty(message = "广告图分类key不能为空")
    private String adCategoryKey;

    @ApiModelProperty(value = "广告图位置key")
    @NotNull(message = "广告图分类key不能为空")
    @NotEmpty(message = "广告图分类key不能为空")
    private String adPositionKey;

    @ApiModelProperty(value = "会员等级")
    private String level;
}
