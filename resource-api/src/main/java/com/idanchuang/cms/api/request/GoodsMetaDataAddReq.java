package com.idanchuang.cms.api.request;

import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:09
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class GoodsMetaDataAddReq implements Serializable {

    private static final long serialVersionUID = 258381851209660307L;

    @ApiModelProperty(value = "专题ID", required = true)
    @NotNull(message = "商品ID不能为空")
    @Size(min = 1, message = "商品ID不能为空")
    private List<String> goodsIdList;

    @ApiModelProperty(value = "商品ID", hidden = true)
    private List<Long> goodsIds;

    @ApiModelProperty(value = "组件ID")
    private Long componentId;

    @ApiModelProperty(value = "专题商品类型。1:普通商品；2:积分商品。默认1")
    private Integer subjectGoodsType;

    @ApiModelProperty(value = "平台类型，默认VTN")
    private String platform;

    @ApiModelProperty(value = "获取价格 默认开启")
    private Boolean priceEnable = true;

    public List<Long> getGoodsIds() {
        if (!CollectionUtils.isEmpty(goodsIdList)) {
            return this.goodsIdList.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        }

        return null;
    }

    public Integer getSubjectGoodsType() {
        return subjectGoodsType == null ? 1 : subjectGoodsType;
    }

    public String getPlatform() {
        return platform == null ? "VTN" : platform.toUpperCase();
    }
}
