package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-10 10:38
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class ShareImgDTO implements Serializable {

    private static final long serialVersionUID = -6485047220232689002L;

    @ApiModelProperty(value = "分享文件ID")
    private Long id;
    @ApiModelProperty(value = "分享文件url")
    private String fileUrl;

    public ShareImgDTO() {}

    public ShareImgDTO(Long id, String fileUrl) {
        this.id = id;
        this.fileUrl = fileUrl;
    }
}
