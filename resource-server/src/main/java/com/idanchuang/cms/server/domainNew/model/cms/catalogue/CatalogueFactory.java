package com.idanchuang.cms.server.domainNew.model.cms.catalogue;

import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 15:39
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class CatalogueFactory {

    private CatalogueFactory() {

    }

    public static Catalogue createCatalogue(CatalogueId catalogueId, String catalogueName, PageCode pageCode,
                                            OperatorId operatorId, ClientPageId clientPageId,
                                            CatalogueType catalogueType,  CatalogueStatus status,
                                            String aliasTitle, PlatformCode platform, CatalogueExtra extra) {

        return new Catalogue(catalogueId, catalogueName, pageCode, operatorId, clientPageId,
                catalogueType, status, aliasTitle, platform, extra, null);
    }
}
