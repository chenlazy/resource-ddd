package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 15:21
 * @Desc: 页面实例查询请求类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageQueryReq implements Serializable {

    private static final long serialVersionUID = -88766688542595286L;

    @ApiModelProperty(value = "页面id")
    private Integer id;

}
