package com.idanchuang.cms.server.infrastructureNew.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domain.shard.IdObject;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CataloguePageQueryForm;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.CatalogueDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper.CatalogueMapper;
import com.idanchuang.cms.server.infrastructureNew.repository.convertor.RepositoryCatalogueConvert;
import com.idanchuang.component.base.page.PageData;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-23 19:54
 * @Desc: 目录领域实现类
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class CatalogueRepositoryImpl implements CatalogueRepository {

    @Resource
    private CatalogueMapper catalogueMapper;

    @Override
    public int storeCatalogue(Catalogue catalogue) {
        CatalogueDO catalogueDO = RepositoryCatalogueConvert.entityToDO(catalogue);
        int insert = catalogueMapper.insertCatalogue(catalogueDO);
        catalogue.setCatalogueId(new CatalogueId(catalogueDO.getId()));
        return insert;
    }

    @Override
    public Catalogue getCatalogueById(CatalogueId catalogueId) {
        if (null == catalogueId) {
            return null;
        }
        CatalogueDO catalogueDO = catalogueMapper.getCatalogueById(catalogueId.getValue());
        return RepositoryCatalogueConvert.doToDomain(catalogueDO);
    }

    @Override
    public List<Catalogue> getCatalogueByIds(List<CatalogueId> catalogueIds) {
        List<CatalogueDO> catalogueByIds = catalogueMapper.getCatalogueByIds(catalogueIds.stream().map(CatalogueId::getValue).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(catalogueByIds)) {
            return Lists.newArrayList();
        }
        return catalogueByIds.stream().map(RepositoryCatalogueConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public Catalogue getCatalogueByCode(PageCode pageCode, CatalogueType catalogueType) {
        CatalogueDO catalogueDO = catalogueMapper.getCatalogueByCode(pageCode.getValue(), catalogueType.getVal());
        return RepositoryCatalogueConvert.doToDomain(catalogueDO);
    }

    @Override
    public int updateCatalogue(Catalogue catalogue) {
        return catalogueMapper.updateCatalogue(RepositoryCatalogueConvert.entityToDO(catalogue));
    }

    @Override
    public PageData<Catalogue> queryCatalogueByPage(CataloguePageQueryForm pageQueryForm) {
        Page<Object> page = PageHelper.startPage(pageQueryForm.getCurrent().intValue(), pageQueryForm.getSize().intValue());
        Long catalogueId = null != pageQueryForm.getCatalogueId() ? pageQueryForm.getCatalogueId().getValue() : null;
        Long clientPageId = null != pageQueryForm.getClientPageId() ? pageQueryForm.getClientPageId().getValue() : null;
        List<Long> clientPageIdList = null;
        if (!CollectionUtils.isEmpty(pageQueryForm.getClientPageIdList())) {
            clientPageIdList = pageQueryForm.getClientPageIdList().stream().map(IdObject::getValue).collect(Collectors.toList());
        }

        List<Integer> platforms = null;
        if (!CollectionUtils.isEmpty(pageQueryForm.getPlatformCodes())) {
            platforms = pageQueryForm.getPlatformCodes().stream().map(PlatformCode::getVal).collect(Collectors.toList());
            //兼容老数据
            if (pageQueryForm.getOld() == 0) {
                platforms.add(-1);
            }
        }

        List<CatalogueDO> catalogueDOS = catalogueMapper.queryCatalogueList(catalogueId, pageQueryForm.getCatalogueName(), clientPageId,
                clientPageIdList, pageQueryForm.getAliasTitle(), platforms, pageQueryForm.getOld());
        if (CollectionUtils.isEmpty(catalogueDOS)) {
            return new PageData<>(Lists.newArrayList(), pageQueryForm.getCurrent(), pageQueryForm.getSize(), 0);
        }
        List<Catalogue> catalogues = catalogueDOS.stream().map(RepositoryCatalogueConvert::doToDomain).collect(Collectors.toList());
        return new PageData<>(catalogues, pageQueryForm.getCurrent(), pageQueryForm.getSize(), page.getTotal());
    }

    @Override
    public Boolean removeCatalogue(CatalogueId catalogueId, OperatorId operatorId) {
        return catalogueMapper.removeCatalogue(catalogueId.getValue(), operatorId.getValue());
    }

    @Override
    public List<Catalogue> queryAllCatalogueList() {

        List<CatalogueDO> catalogueDOS = catalogueMapper.queryAllCatalogueList();
        return catalogueDOS.stream().map(RepositoryCatalogueConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Catalogue> queryAllCatalogByTagId(ClientPageId pageId) {
        List<CatalogueDO> catalogueDOS = catalogueMapper.queryAllCatalogueListByTagId(pageId.getValue());
        return catalogueDOS.stream().map(RepositoryCatalogueConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Catalogue> queryAllCatalogByTagIds(List<ClientPageId> pageIds) {
        List<CatalogueDO> catalogueDOS = catalogueMapper.queryAllCatalogByTagIds(pageIds.stream().map(ClientPageId::getValue).collect(Collectors.toList()));
        return catalogueDOS.stream().map(RepositoryCatalogueConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public Integer queryTotalCatalogue() {
        return catalogueMapper.queryTotalCatalogue();
    }
}
