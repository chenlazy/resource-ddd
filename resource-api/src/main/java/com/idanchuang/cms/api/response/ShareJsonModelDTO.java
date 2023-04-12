package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fym
 * @description :
 * @date 2021/9/7 下午1:47
 */
@Data
public class ShareJsonModelDTO implements Serializable {
    private static final long serialVersionUID = 4885628344217442024L;

    @ApiModelProperty(value = "分享用户等级")
    private Integer level;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "分享图片id")
    private Long shareImg;

    @ApiModelProperty(value = "分享海报id")
    private Long sharePoster;

    @ApiModelProperty(value = "分享范围 0：全部, 1:制定")
    private Integer shareScope;
}
