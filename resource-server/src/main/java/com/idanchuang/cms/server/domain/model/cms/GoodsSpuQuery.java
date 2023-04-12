package com.idanchuang.cms.server.domain.model.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/4/20
 */
@Data
public class GoodsSpuQuery implements Serializable {
    private static final long serialVersionUID = 8880434768266684096L;

    @NotNull
    public List<Long> spuIdList;

    /**
     * 是否查询价格
     * 0否；1是
     */
    private int queryPrice = 0;

    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("用户等级，不传就不过过滤用户等级")
    private Integer userLevel;
    @Valid
    private GoodsAddressQuery goodsAddress;

    @Override
    public String toString() {
        return "SpuQueryRequest{" +
                "spuIdList=" + spuIdList +
                '}';
    }
}
