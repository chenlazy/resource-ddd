package com.idanchuang.cms.server.interfaces.assember;

import com.google.common.collect.Lists;
import com.idanchuang.cms.api.request.CmsCorePageReq;
import com.idanchuang.cms.api.response.CmsPageListDTO;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CataloguePageQueryForm;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPage;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.sso.model.dto.system.UserDetailDTO;
import org.codehaus.plexus.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-23 19:11
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class InterfaceCatalogueAssembler {

    private InterfaceCatalogueAssembler() {

    }

    public static CataloguePageQueryForm dtoToDomain(CmsCorePageReq cmsCorePageReq) {

        if (null == cmsCorePageReq) {
            return null;
        }

        String platform = cmsCorePageReq.getPlatform();
        List<PlatformCode> platformCodes = Lists.newArrayList();
        if (StringUtils.isNotBlank(platform)) {
            List<String> platfroms = Arrays.asList(platform.split(","));
            platfroms.forEach( p -> {
                PlatformCode platformCode = PlatformCode.fromDesc(p);
                if (platformCode != null) {
                    platformCodes.add(platformCode);
                }
            });
        }

        return CataloguePageQueryForm.builder()
                .aliasTitle(cmsCorePageReq.getAliasTitle())
                .catalogueId(null != cmsCorePageReq.getId() ? new CatalogueId(cmsCorePageReq.getId()) : null)
                .catalogueName(cmsCorePageReq.getPageTitle())
                .old(cmsCorePageReq.getOld())
                .platformCodes(platformCodes)
                .clientPageId(null != cmsCorePageReq.getTagId() ? new ClientPageId(cmsCorePageReq.getTagId()) : null)
                .current(cmsCorePageReq.getCurrent())
                .size(cmsCorePageReq.getSize()).build();
    }

    public static CmsPageListDTO entityToDTO(Catalogue catalogue, Map<Long, ClientPage> clientPageMap,
                                             Map<CatalogueId, List<Masterplate>> catalogueMap, Map<Long, UserDetailDTO> usersMap) {

        if (null == catalogue) {
            return null;
        }

        List<Masterplate> masterplates = catalogueMap.get(catalogue.getId());

        Masterplate masterplate = catalogue.fetchPriorityMasterplate(masterplates);
        long waitValidCount = masterplates.stream().filter(p -> p.getStartTime().isAfter(LocalDateTime.now())).count();

        CmsPageListDTO cmsPageListDTO = new CmsPageListDTO();
        cmsPageListDTO.setId(catalogue.getId().getValue());
        cmsPageListDTO.setPageTitle(catalogue.getCatalogueName());
        cmsPageListDTO.setAliasTitle(catalogue.getAliasTitle());
        cmsPageListDTO.setPlatform(PlatformCode.simplyPlatform(catalogue.getPlatform()));
        cmsPageListDTO.setVersion(PlatformCode.getVersionByPlatform(catalogue.getPlatform()));
        cmsPageListDTO.setOperatorId(catalogue.getOperatorId().getValue());
        UserDetailDTO userDetailDTO = null != usersMap ? usersMap.get(catalogue.getOperatorId().getValue()) : null;
        cmsPageListDTO.setOperator(null != userDetailDTO ? userDetailDTO.getRealName() : "");
        cmsPageListDTO.setTag(null != catalogue.getPageId() && null != clientPageMap.get(catalogue.getPageId().getValue()) ? clientPageMap.get(catalogue.getPageId().getValue()).getName() : "");
        cmsPageListDTO.setStatus(catalogue.getCatalogueStatus().getVal());
        cmsPageListDTO.setUpdatedAt(catalogue.getUpdateTime());
        cmsPageListDTO.setChangeButton(CatalogueType.CLIENT_PAGE.equals(catalogue.getCatalogueType()) ? "转成H5页面" : "转成原生页面");
        if (null != masterplate) {
            cmsPageListDTO.setTemplateId((int) masterplate.getId().getValue());
            cmsPageListDTO.setCurrentPageId((int) masterplate.getId().getValue());
            cmsPageListDTO.setCurrentPageTitle(masterplate.getMasterplateName() + "(" + masterplate.getId().getValue() + ")");
            cmsPageListDTO.setCurrentStartTime(masterplate.getStartTime());
            cmsPageListDTO.setPageVersionCount((int) waitValidCount);
        }
        return cmsPageListDTO;
    }
}
