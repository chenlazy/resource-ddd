package com.idanchuang.cms.server.domain.model.cms;

import com.idanchuang.cms.api.response.ShareImgDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-15 11:38
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsCoreShareImg {

    @ApiModelProperty(value = "分享用户等级")
    private Integer level;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "分享图片")
    private ShareImgDTO shareImg;

    @ApiModelProperty(value = "分享海报")
    private ShareImgDTO sharePoster;

    @ApiModelProperty(value = "分享范围 0：全部, 1:制定")
    private Integer shareScope;
}
