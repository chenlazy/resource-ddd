package com.idanchuang.cms.server.infrastructureNew.repository.convertor;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.ComponentRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.ContainerRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentBusinessType;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerCode;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.GoodsEnable;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.ShareFlag;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.render.ComponentRenderDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.render.ContainerRenderDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.render.PageRenderDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-01-04 16:08
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class RepositoryPageRenderConvert {

    private RepositoryPageRenderConvert() {

    }

    public static PageRenderDO domainToDo(PageRender pageRender) {
        
        if (null == pageRender) {
            return null;
        }

        PageRenderDO pageRenderDO = new PageRenderDO();
        pageRenderDO.setId((int)pageRender.getCatalogueId().getValue());
        pageRenderDO.setPageId((int)pageRender.getMasterplateId().getValue());
        pageRenderDO.setAliasTitle(pageRender.getAliasTitle());
        pageRenderDO.setPlatform(null != pageRender.getPlatform() ? pageRender.getPlatform().getDesc() : null);
        pageRenderDO.setShareFlag(pageRender.getShareFlag().getVal());
        pageRenderDO.setTagId((int)pageRender.getClientPageId().getValue());
        pageRenderDO.setPageTitle(pageRender.getPageTitle());
        pageRenderDO.setBackEndTitle(pageRender.getBackEndTitle());
        pageRenderDO.setVersion(pageRender.getVersion());
        pageRenderDO.setNextStartTime(pageRender.getNextStartTime());
        pageRenderDO.setStartTime(pageRender.getStartTime());
        pageRenderDO.setEndTime(pageRender.getEndTime());
        pageRenderDO.setShareList(pageRender.getShareList());
        pageRenderDO.setVersionEndTime(pageRender.getVersionEndTime());
        pageRenderDO.setPageStyle(pageRender.getPageStyle());
        pageRenderDO.setGoodsEnable(pageRender.getGoodsEnable().getVal());

        List<ContainerRenderDO> containerRenderDOS = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(pageRender.getContainers())) {

            for (ContainerRender containerRender : pageRender.getContainers()) {
                ContainerRenderDO renderDO = new ContainerRenderDO();
                renderDO.setContainerId(containerRender.getContainerId().getValue());
                renderDO.setContainerCode(containerRender.getContainerCode() != null ? containerRender.getContainerCode().getValue() : "");
                renderDO.setStyleContent(containerRender.getStyleContent());
                List<ComponentRender> components = containerRender.getComponents();

                List<ComponentRenderDO> componentRenderDOS = Lists.newArrayList();

                if (CollectionUtils.isNotEmpty(components)) {
                    components.forEach(p -> {
                        ComponentRenderDO componentRenderDO = new ComponentRenderDO();
                        componentRenderDO.setModelType(null != p.getModelType() ? p.getModelType().getModelType() : 0);
                        componentRenderDO.setModelContent(p.getModelContent());
                        componentRenderDOS.add(componentRenderDO);
                    });
                }
                renderDO.setComponents(componentRenderDOS);
                containerRenderDOS.add(renderDO);
            }
        }
        pageRenderDO.setContainers(containerRenderDOS);

        return pageRenderDO;
    }
    
    public static PageRender doToDomain(PageRenderDO pageRenderDO) {
        
        if (null == pageRenderDO) {
            return null;
        }

        List<ContainerRenderDO> containers = pageRenderDO.getContainers();

        List<ContainerRender> containerRenders = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(containers)) {
            for (ContainerRenderDO containerRenderDO : containers) {

                List<ComponentRenderDO> components = containerRenderDO.getComponents();

                List<ComponentRender> componentRenders = Lists.newArrayList();
                if (CollectionUtils.isNotEmpty(components)) {
                    components.forEach(p -> {
                        ComponentRender componentRender = new ComponentRender(ComponentBusinessType.fromType(p.getModelType()),
                                        p.getModelContent());
                        componentRenders.add(componentRender);
                    });
                }

                ContainerRender containerRender = new ContainerRender(new ContainerId(containerRenderDO.getContainerId()),
                        StringUtils.isNotEmpty(containerRenderDO.getContainerCode()) ? new ContainerCode(containerRenderDO.getContainerCode()) : null,
                        containerRenderDO.getStyleContent(), componentRenders);
                containerRenders.add(containerRender);
            }
        }

        return new PageRender(new CatalogueId(pageRenderDO.getId()),
                new MasterplateId(pageRenderDO.getPageId()), new ClientPageId(pageRenderDO.getTagId()),
                pageRenderDO.getPageTitle(), pageRenderDO.getBackEndTitle(), pageRenderDO.getAliasTitle(),
                PlatformCode.fromDesc(pageRenderDO.getPlatform()), ShareFlag.fromVal(pageRenderDO.getShareFlag()),
                pageRenderDO.getVersion(), pageRenderDO.getNextStartTime(), pageRenderDO.getStartTime(),
                pageRenderDO.getEndTime(), GoodsEnable.fromVal(pageRenderDO.getGoodsEnable()), pageRenderDO.getPageStyle(), containerRenders,
                pageRenderDO.getShareList(), pageRenderDO.getVersionEndTime());
    }
}
