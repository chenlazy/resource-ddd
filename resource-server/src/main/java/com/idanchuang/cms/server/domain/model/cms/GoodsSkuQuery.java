package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/4/21
 */
@Data
public class GoodsSkuQuery implements Serializable {
    private static final long serialVersionUID = 2020421534481926039L;

    private List<Long> skuIdList;

    /**
     * 是否查询价格
     * 0否；1是
     */
    private int queryPrice = 0;

    /**
     * 是否查询库存
     * 0否；1是
     */
    private int queryStock = 0;

    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("用户等级，不传就不过过滤用户等级")
    private Integer userLevel;
    @Valid
    private GoodsAddressQuery goodsAddress;
}
