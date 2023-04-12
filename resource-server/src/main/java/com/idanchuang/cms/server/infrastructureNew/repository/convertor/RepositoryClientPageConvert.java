package com.idanchuang.cms.server.infrastructureNew.repository.convertor;

import com.idanchuang.cms.server.domain.shard.IdObject;
import com.idanchuang.cms.server.domain.shard.StringIdObject;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPage;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.ClientPageDO;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-29 13:41
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class RepositoryClientPageConvert {

    private RepositoryClientPageConvert() {

    }

    public static ClientPageDO toClientPageDO(ClientPage clientPage) {
        if (clientPage == null) {
            return null;
        }
        return ClientPageDO.builder()
                .id(Math.toIntExact(Optional.ofNullable(clientPage.getId()).map(IdObject::getValue).orElse(0L)))
                .pageCode(Optional.ofNullable(clientPage.getPageCode()).map(StringIdObject::getValue).orElse(""))
                .name(clientPage.getName())
                .operatorId(Math.toIntExact(Optional.ofNullable(clientPage.getOperatorId()).map(IdObject::getValue).orElse(0L)))
                .platform(Optional.ofNullable(clientPage.getPlatform()).map(PlatformCode::getVal).orElse(null))
                .build();
    }

    public static ClientPage toClientPage(ClientPageDO clientPageDO) {
        if (clientPageDO == null) {
            return null;
        }

        return new ClientPage(new ClientPageId(clientPageDO.getId()),
                clientPageDO.getName(),
                PlatformCode.fromVal(clientPageDO.getPlatform()),
                StringUtils.isEmpty(clientPageDO.getPageCode()) ? null : new PageCode(clientPageDO.getPageCode()),
                clientPageDO.getOperatorId() != null && clientPageDO.getOperatorId() > 0 ?
                        new OperatorId(clientPageDO.getOperatorId()) : null,
                clientPageDO.getCreateTime(), clientPageDO.getUpdateTime());
    }
}
