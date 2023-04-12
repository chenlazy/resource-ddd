package com.idanchuang.cms.server.domain.model.cms;

import lombok.Data;

import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/6
 */
@Data
public class ComponentGoodsPriceCondition {

    private Integer type;

    private List<Long> goodsIdList;

    private Long componentId;
}
