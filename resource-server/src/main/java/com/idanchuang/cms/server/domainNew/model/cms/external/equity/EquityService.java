package com.idanchuang.cms.server.domainNew.model.cms.external.equity;

import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;

import java.util.Map;
import java.util.Set;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-05-20 14:29
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface EquityService {

    void checkEquityValid(Set<EquityKey> equityKeys);

    /**
     * 同步权益信息
     * @param equityTagMap
     * @param masterplateId
     */
    void syncEquityTag(Map<EquityKey, Long> equityTagMap, MasterplateId masterplateId);
}
