package com.idanchuang.cms.server.infrastructure.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:05
 * @Desc: 组件商品价格表
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("w_component_goods_price")
public class ComponentGoodsPriceDO {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 组件id
     */
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
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    private Integer deleted;

}
