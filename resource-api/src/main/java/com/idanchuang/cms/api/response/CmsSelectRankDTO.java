package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午1:56
 */
@Data
public class CmsSelectRankDTO {

    /**
     * 页面定义id
     */
    @ApiModelProperty(value = "页面定义id")
    private Integer pageSchemaId;

    /**
     * 页面名称
     */
    @ApiModelProperty(value = "页面名称")
    private String pageName;

    /**
     * 页面别名 例如 VTN_123
     */
    @ApiModelProperty(value = "页面别名 例如 VTN_123")
    private String aliasTitle;

    /**
     * 页面模版id
     */
    @ApiModelProperty(value = "页面模版id")
    private Integer pageId;

    /**
     * 模版创建人id
     */
    @ApiModelProperty(value = "模版创建人id")
    private Integer createId;

    /**
     * 模版最后更新人id
     */
    @ApiModelProperty(value = "模版最后更新人id")
    private Integer operatorId;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String operatorName;

}
