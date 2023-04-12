package com.idanchuang.cms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-10 15:33
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageGoodsCopyDTO implements Serializable {

    private static final long serialVersionUID = 8845843212365752492L;

    @ApiModelProperty(value = "组件ID")
    private Long componentId;

    @ApiModelProperty(value = "商品ID(SPU)")
    private Long goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品主图ID")
    private Long goodsImage;

    @ApiModelProperty(value = "商品副标题")
    private String goodsSubTitle;

    @ApiModelProperty(value = "商品卖点。格式：['卖点1','卖点2']")
    private String salesPoint;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除状态")
    private Integer deleted;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "赠品图片ID")
    private Long giftImage;

    @ApiModelProperty(value = "专题商品类型")
    private Integer subjectGoodsType;

    @ApiModelProperty(value = "商品限购数量")
    private Integer limitNum;
}
