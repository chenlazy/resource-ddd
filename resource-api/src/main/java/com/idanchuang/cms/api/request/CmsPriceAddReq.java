package com.idanchuang.cms.api.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fym
 * @description :
 * @date 2021/9/16 下午5:02
 */
@Data
public class CmsPriceAddReq {

    /**
     * 添加组件id
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

}
