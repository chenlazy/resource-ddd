package com.idanchuang.cms.api.request;

import com.idanchuang.component.base.page.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-09 15:00
 * @Desc: 专题页面查询请求类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsCorePageReq extends PageDTO implements Serializable {

    private static final long serialVersionUID = -3916708788659372386L;


    @ApiModelProperty(value = "是否过滤老数据，0否；1是，默认是0")
    private int old = 0;

    @ApiModelProperty(value = "专题ID")
    private Long id;
    @ApiModelProperty(value = "页面标题")
    private String pageTitle;
    @ApiModelProperty(value = "别名")
    private String aliasTitle;
    @ApiModelProperty(value = "平台类型")
    private String platform;
    @ApiModelProperty(value = "标签ID")
    private Long tagId;

    public String getPlatform() {
        return StringUtils.hasText(this.platform) ? this.platform.toUpperCase() : null;
    }
}
