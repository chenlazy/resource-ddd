package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-13 14:01
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsCoreShare {

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

    @ApiModelProperty(value = "分享海报id")
    private List<String> sharePosterList;
}
