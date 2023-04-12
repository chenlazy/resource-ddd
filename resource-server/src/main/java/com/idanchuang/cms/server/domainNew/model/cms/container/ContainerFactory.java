package com.idanchuang.cms.server.domainNew.model.cms.container;

import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-20 14:26
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class ContainerFactory {

    private ContainerFactory() {

    }

    public static Container createContainer(MasterplateId masterplateId, ContainerCode containerCode,
                                     String containerName, ContainerStatus status, ContainerExtra extra,
                                     String pageStyle, OperatorId operatorId) {
        return new Container(null, masterplateId, containerCode, containerName, status, extra, pageStyle, operatorId,0L);
    }
}
