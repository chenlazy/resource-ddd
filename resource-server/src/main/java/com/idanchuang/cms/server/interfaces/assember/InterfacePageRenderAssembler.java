package com.idanchuang.cms.server.interfaces.assember;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.ComponentRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.ContainerRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateShareForm;
import com.idanchuang.cms.server.interfaces.vo.ComponentRenderVO;
import com.idanchuang.cms.server.interfaces.vo.ContainerRenderVO;
import com.idanchuang.cms.server.interfaces.vo.PageRenderVO;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-31 15:50
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class InterfacePageRenderAssembler {

    private InterfacePageRenderAssembler() {

    }

    public static PageRenderVO domainToVo(PageRender pageRender) {

        if (null == pageRender) {
            return null;
        }

        PageRenderVO pageRenderVO = new PageRenderVO();
        pageRenderVO.setId((int)pageRender.getCatalogueId().getValue());
        pageRenderVO.setPageId((int)pageRender.getMasterplateId().getValue());
        pageRenderVO.setTagId((int)pageRender.getClientPageId().getValue());
        pageRenderVO.setPageTitle(pageRender.getPageTitle());
        pageRenderVO.setBackEndTitle(pageRender.getBackEndTitle());
        pageRenderVO.setAliasTitle(pageRender.getAliasTitle());
        pageRenderVO.setPlatform(null != pageRender.getPlatform() ? pageRender.getPlatform().getDesc() : null);
        pageRenderVO.setShareFlag(pageRender.getShareFlag().getVal());
        pageRenderVO.setVersion(pageRender.getVersion());
        pageRenderVO.setStartTime(pageRender.getStartTime());
        pageRenderVO.setEndTime(pageRender.getEndTime());
        pageRenderVO.setGoodsEnable(pageRender.getGoodsEnable().getVal());
        List<ContainerRender> containers = pageRender.getContainers();

        List<ContainerRenderVO> containerRenderVOS = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(containers)) {

            for (ContainerRender containerRender : containers) {
                ContainerRenderVO renderVO = new ContainerRenderVO();
                renderVO.setContainerId(containerRender.getContainerId().getValue());
                renderVO.setContainerCode(containerRender.getContainerCode().getValue());
                renderVO.setStyleContent(containerRender.getStyleContent());
                List<ComponentRender> components = containerRender.getComponents();

                List<ComponentRenderVO> componentRenderVOS = Lists.newArrayList();

                if (!CollectionUtils.isEmpty(components)) {
                    components.forEach(p -> {
                        ComponentRenderVO componentRenderVO = new ComponentRenderVO();
                        componentRenderVO.setModelType(p.getModelType().getModelType());
                        componentRenderVO.setModelContent(p.getModelContent());
                        componentRenderVOS.add(componentRenderVO);
                    });
                }
                renderVO.setComponents(componentRenderVOS);
                containerRenderVOS.add(renderVO);
            }
        }
        pageRenderVO.setContainers(containerRenderVOS);
        pageRenderVO.setShareList(pageRender.getShareList());
        pageRenderVO.setVersionEndTime(pageRender.getVersionEndTime());

        return pageRenderVO;
    }
}
