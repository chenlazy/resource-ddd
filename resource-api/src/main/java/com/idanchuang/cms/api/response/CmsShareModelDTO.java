package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-15 11:42
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsShareModelDTO implements Serializable {

    private static final long serialVersionUID = -9057210372380607186L;

    @ApiModelProperty(value = "分享用户等级")
    private Integer level;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "分享图片id")
    private ShareImgDTO shareImg;

    @ApiModelProperty(value = "分享海报id")
    private ShareImgDTO sharePoster;

    @ApiModelProperty(value = "分享范围 0：全部, 1:制定")
    private Integer shareScope;

    @ApiModelProperty(value = "分享海报url数组")
    private List<String> sharePosterList;
}
