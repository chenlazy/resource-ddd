package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-13 13:57
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsCoreShareReq implements Serializable {

    private static final long serialVersionUID = -6797098674684023853L;

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

    @ApiModelProperty(value = "分享海报url")
    private List<String> sharePosterList;




}
