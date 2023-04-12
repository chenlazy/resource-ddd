package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/8/9 下午5:36
 */
@Data
public class SubjectEnableGoodsInfoDTO {

    @ApiModelProperty(value = "专题id")
    private Long subjectId;

    @ApiModelProperty(value = "组件id集合")
    private List<Long> componentId;

    @ApiModelProperty(value = "组件关联商品")
    private List<Long> goodsIds;
}
