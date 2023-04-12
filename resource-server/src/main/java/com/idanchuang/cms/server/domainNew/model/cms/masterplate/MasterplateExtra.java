package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.SelectInfo;
import lombok.*;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-15 11:43
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterplateExtra {

    /**
     * 商品自动下架标记 0不下架 1下架(goods_enable)
     */
    private GoodsEnable goodsEnable;

    /**
     * 圈选信息
     */
    private List<SelectInfo> selectInfoList;
}
