package com.idanchuang.cms.server.domain.model.cms;

import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:05
 * @Desc: 组件商品价格表
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class ComponentGoodsPrice {

    @NonFinal
    private Integer id;

    /**
     * 添加组件id
     */
    @NonFinal
    private Long componentId;

    /**
     * 专题商品ID
     */
    private Long componentGoodsId;

    /**
     * 价格类型 1 日常价
     */
    private Integer type;

    /**
     * 价格数据，格式：[{type:"1",price:"0.01"}]
     */
    private String priceData;

    /**
     * 操作人id
     */
    private Integer operatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @NonFinal
    private LocalDateTime updateTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
