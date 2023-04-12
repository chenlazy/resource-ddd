package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-09 15:59
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsCorePageList {

    /**
     * 是否过滤老数据，0否；1是，默认是0
     */
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
    @ApiModelProperty("当前页 默认值1")
    private Long current;
    @ApiModelProperty("每页显示条数 默认值30")
    private Long size;

    /**
     * 标签ID列表
     */
    private List<Long> tagIdList;
}
