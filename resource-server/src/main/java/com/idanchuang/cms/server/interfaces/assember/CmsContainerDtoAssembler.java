package com.idanchuang.cms.server.interfaces.assember;

import com.idanchuang.cms.api.request.CmsPageContainerCreateReq;
import com.idanchuang.cms.api.response.CmsPageContainerDTO;
import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.cms.server.domain.model.cms.factory.ContainerFactory;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 17:28
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class CmsContainerDtoAssembler {

    public static CmsPageContainer toEntity(CmsPageContainerCreateReq containerCreateReq) {
        if (containerCreateReq == null) {
            return null;
        }

        return ContainerFactory.createContainer(containerCreateReq.getPageId().intValue(), null,
                containerCreateReq.getStatus(), containerCreateReq.getPageStyle(),
                containerCreateReq.getContainerName(), containerCreateReq.getOperatorId().intValue(), null);
    }

    public static CmsPageContainerDTO entityToDTO(CmsPageContainer cmsPageContainer) {
        if (cmsPageContainer == null) {
            return null;
        }
        CmsPageContainerDTO cmsPageContainerDTO = new CmsPageContainerDTO();
        cmsPageContainerDTO.setId(cmsPageContainer.getId().intValue());
        cmsPageContainerDTO.setPageId(cmsPageContainer.getPageId());
        cmsPageContainerDTO.setContainerName(cmsPageContainer.getContainerName());
        cmsPageContainerDTO.setStatus(cmsPageContainer.getStatus());
        cmsPageContainerDTO.setPageStyle(cmsPageContainer.getPageStyle());
        cmsPageContainerDTO.setOperatorId(cmsPageContainer.getOperatorId());
        cmsPageContainerDTO.setCreateTime(cmsPageContainer.getCreateTime());
        cmsPageContainerDTO.setUpdateTime(cmsPageContainer.getUpdateTime());
        return cmsPageContainerDTO;
    }
}
