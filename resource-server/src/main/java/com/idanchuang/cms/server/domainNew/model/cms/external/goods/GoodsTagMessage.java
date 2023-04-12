package com.idanchuang.cms.server.domainNew.model.cms.external.goods;

import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityKey;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-22 15:43
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class GoodsTagMessage {

    /**
     * 商品id
     */
    private List<GoodsId> goodsIds;

    /**
     * 页面id
     */
    private ClientPageId pageId;

    /**
     * 模版id
     */
    private MasterplateId masterplateId;

    /**
     * 模版名称
     */
    private String masterplateName;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 指定打标状态 0不指定 1指定
     */
    private AssignTagEnableEnum assignTagEnable;

    /**
     * 指定标签类型（assignTagEnable = 1 时使用）
     */
    private Integer tagType;

    /**
     * 指定标签操作类型文案 （assignTagEnable = 1 时使用）
     */
    private String tagMsg;

    /**
     * 指定标签绑定权益Id （assignTagEnable = 1 时使用）
     */
    private EquityKey equityKey;


}
