package com.idanchuang.cms.server.interfaces.assember;

import com.google.common.collect.Lists;
import com.idanchuang.cms.api.request.CmsPageCreateReq;
import com.idanchuang.cms.api.request.CmsPageQueryReq;
import com.idanchuang.cms.api.response.CmsPageDTO;
import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.domain.model.cms.CmsPageQuery;

import java.util.Optional;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 14:58
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class CmsPageDtoAssembler {

    public static CmsPage toEntity(CmsPageCreateReq cmsPageCreateReq) {
        if (cmsPageCreateReq == null) {
            return null;
        }
        Long operatorId = Optional.ofNullable(cmsPageCreateReq.getOperatorId()).orElse(0L);
        Long id = Optional.ofNullable(cmsPageCreateReq.getId()).orElse(0L);
        Long pageSchemaId = Optional.ofNullable(cmsPageCreateReq.getPageSchemaId()).orElse(0L);

        return new CmsPage(id.intValue(), pageSchemaId.intValue(), cmsPageCreateReq.getPageName(),
                cmsPageCreateReq.getPageType(), cmsPageCreateReq.getPageName(), cmsPageCreateReq.getTagId(), cmsPageCreateReq.getPlatform(),
                cmsPageCreateReq.getStatus(), Lists.newArrayList(cmsPageCreateReq.getSort()), cmsPageCreateReq.getAliasTitle(),
                cmsPageCreateReq.getShareFlag(), cmsPageCreateReq.getShareJson(), cmsPageCreateReq.getStartTime(), cmsPageCreateReq.getEndTime(),
                cmsPageCreateReq.getGoodsEnable(), operatorId.intValue(), null, null, 0, null);
    }

    public static CmsPageQuery toQuery(CmsPageQueryReq pageQueryReq) {
        if (pageQueryReq == null) {
            return null;
        }
        CmsPageQuery cmsPageQuery = new CmsPageQuery();
        cmsPageQuery.setId(pageQueryReq.getId());
        return cmsPageQuery;
    }

    public static CmsPageDTO entityToDTO(CmsPage cmsPage) {
        if (cmsPage == null) {
            return null;
        }
        CmsPageDTO cmsPageDTO = new CmsPageDTO();
        cmsPageDTO.setId(cmsPage.getId());
        cmsPageDTO.setPageSchemaId(cmsPage.getPageSchemaId());
        cmsPageDTO.setPageName(cmsPage.getPageName());
        cmsPageDTO.setPageType(cmsPage.getPageType());
        cmsPageDTO.setBackEndTitle(cmsPage.getBackEndTitle());
        cmsPageDTO.setShareFlag(cmsPage.getShareFlag());
        cmsPageDTO.setShareJson(cmsPage.getShareJson());
        cmsPageDTO.setStartTime(cmsPage.getStartTime());
        cmsPageDTO.setEndTime(cmsPage.getEndTime());
        cmsPageDTO.setGoodsEnable(cmsPage.getGoodsEnable());
        cmsPageDTO.setTagId(null != cmsPage.getTagId() ? cmsPage.getTagId().intValue() : 0);
        cmsPageDTO.setPlatform(cmsPage.getPlatform());
        cmsPageDTO.setStatus(cmsPage.getStatus());
        cmsPageDTO.setSort(cmsPage.getSort().get(0));
        cmsPageDTO.setAliasTitle(cmsPage.getAliasTitle());
        cmsPageDTO.setOperatorId(cmsPage.getOperatorId());
        cmsPageDTO.setCreateTime(cmsPage.getCreateTime());
        cmsPageDTO.setUpdateTime(cmsPage.getUpdateTime());
        return cmsPageDTO;
    }
}
