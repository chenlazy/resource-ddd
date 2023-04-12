package com.idanchuang.cms.server.infrastructureNew.repository;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRenderFactory;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRenderRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.render.PageRenderDO;
import com.idanchuang.cms.server.infrastructureNew.repository.convertor.RepositoryPageRenderConvert;
import com.idanchuang.component.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-23 16:08
 * @Desc: 页面聚合仓库实现类
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Repository
public class PageRenderRepositoryImpl implements PageRenderRepository {

    private static final String PAGE_STYLE_CURRENT_KEY = "new.pre.page.current:";

    @Resource
    private CatalogueRepository catalogueRepository;

    @Resource
    private MasterplateRepository masterplateRepository;

    @Resource
    private ContainerRepository containerRepository;

    @Override
    public void storePageRender(PageRender pageRender) {

        if (null == pageRender) {
            return;
        }

        PageRenderDO pageRenderDO = RepositoryPageRenderConvert.domainToDo(pageRender);
        RedisUtil.getInstance().setObj(getCurrentKey(new CatalogueId(pageRenderDO.getId())), pageRenderDO);

    }

    @Override
    public void removePageRender(CatalogueId catalogueId) {
        if (null == catalogueId) {
            return;
        }
        RedisUtil.getInstance().delObj(getCurrentKey(catalogueId));
    }

    @Override
    public List<PageRender> getPageRenderListForActive(CatalogueId catalogueId) {

        Catalogue catalogue = catalogueRepository.getCatalogueById(catalogueId);

        if (null == catalogue) {
            return Lists.newArrayList();
        }

        List<Masterplate> masterplateList = masterplateRepository.getMasterplateListForActive(catalogueId);

        if (CollectionUtils.isEmpty(masterplateList)) {
            return Lists.newArrayList();
        }

        return masterplateList.stream().map(p -> PageRenderFactory.createPageRender(p, catalogue.getAliasTitle(), catalogue.getPlatform())).collect(Collectors.toList());
    }

    @Override
    public List<PageRender> getPageRenderListByPageCode(PageCode pageCode, CatalogueType catalogueType) {
        Catalogue catalogue = catalogueRepository.getCatalogueByCode(pageCode, catalogueType);
        return getPageRenderList(catalogue);
    }

    @Override
    public List<PageRender> getPageRenderListById(CatalogueId catalogueId) {
        Catalogue catalogue = catalogueRepository.getCatalogueById(catalogueId);
        return getPageRenderList(catalogue);
    }

    @Override
    public PageRender getPageRenderByCatalogueId(CatalogueId catalogueId) {

        if (null == catalogueId) {
            return null;
        }

        PageRenderDO pageRenderDO = null;
        try {
            pageRenderDO = RedisUtil.getInstance().getObj(getCurrentKey(catalogueId));
        } catch (Exception e) {
            log.warn("根据目录id获取pageRender缓存失败, catalogueId:{}, e:{}", catalogueId.getValue(), e.getMessage(), e);
        }

        return RepositoryPageRenderConvert.doToDomain(pageRenderDO);
    }

    private List<PageRender> getPageRenderList(Catalogue catalogue) {

        if (null == catalogue) {
            return Lists.newArrayList();
        }

        List<Masterplate> masterplateList = masterplateRepository.getMasterplateList(catalogue.getId());

        if (CollectionUtils.isEmpty(masterplateList)) {
            return Lists.newArrayList();
        }

        List<PageRender> pageRenders = masterplateList.stream().map(p -> PageRenderFactory.createPageRender(p, catalogue.getAliasTitle(),
                catalogue.getPlatform())).collect(Collectors.toList());

        pageRenders.forEach(p -> {
            List<Container> containers = containerRepository.queryContainerList(p.getMasterplateId());
            if (CollectionUtils.isNotEmpty(containers)) {
                p.addContainers(containers);
            }
        });

        return pageRenders;
    }


    private String getCurrentKey(CatalogueId catalogueId) {
        return PAGE_STYLE_CURRENT_KEY + catalogueId.getValue();
    }

}
