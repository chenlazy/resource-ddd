package com.idanchuang.cms.server.infrastructureNew.repository.convertor;

import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueExtra;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueStatus;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.CatalogueDO;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-24 14:17
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class RepositoryCatalogueConvert {

    private RepositoryCatalogueConvert() {

    }

    public static CatalogueDO entityToDO(Catalogue catalogue) {

        if (null == catalogue) {
            return null;
        }
        Integer catalogueId = null != catalogue.getId() ? (int) catalogue.getId().getValue() : null;
        Integer platform = null != catalogue.getPlatform() ? catalogue.getPlatform().getVal() : 0;
        String pageCode = null != catalogue.getPageCode() ? catalogue.getPageCode().getValue() : "";
        Integer catalogueType = null != catalogue.getCatalogueType() ? catalogue.getCatalogueType().getVal() : null;
        Integer operatorId = null != catalogue.getOperatorId() ? (int) catalogue.getOperatorId().getValue() : null;
        Integer status = null != catalogue.getCatalogueStatus() ? catalogue.getCatalogueStatus().getVal() : null;
        Long clientPageId = null != catalogue.getPageId() ? catalogue.getPageId().getValue() : 0;
        String extra = null != catalogue.getExtra() ? JsonUtil.toJsonString(catalogue.getExtra()) : "";

        return CatalogueDO.builder().id(catalogueId).platform(platform).pageCode(pageCode).pageName(catalogue.getCatalogueName())
                .aliasTitle(catalogue.getAliasTitle()).pageType(catalogueType).operatorId(operatorId).status(status)
                .tagId(clientPageId).extra(extra).deleted(0).build();
    }

    public static Catalogue doToDomain(CatalogueDO catalogueDO) {
        if (null == catalogueDO) {
            return null;
        }
        return new Catalogue(new CatalogueId(catalogueDO.getId()),
                catalogueDO.getPageName(),
                StringUtils.isNotEmpty(catalogueDO.getPageCode()) ? new PageCode(catalogueDO.getPageCode()) : null,
                null != catalogueDO.getOperatorId() && catalogueDO.getOperatorId() > 0 ? new OperatorId(catalogueDO.getOperatorId()) : null,
                catalogueDO.getTagId() != null && catalogueDO.getTagId() > 0 ? new ClientPageId(catalogueDO.getTagId()) : null,
                catalogueDO.getPageType() == null ? null : CatalogueType.fromVal(catalogueDO.getPageType()),
                catalogueDO.getStatus() == null ? null : CatalogueStatus.fromVal(catalogueDO.getStatus()),
                catalogueDO.getAliasTitle(),
                catalogueDO.getPlatform() == null ? null : PlatformCode.fromVal(catalogueDO.getPlatform()),
                null, catalogueDO.getUpdateTime());
    }
}
