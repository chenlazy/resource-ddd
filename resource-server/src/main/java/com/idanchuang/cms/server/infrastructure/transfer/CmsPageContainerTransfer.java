package com.idanchuang.cms.server.infrastructure.transfer;

import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.cms.server.domain.model.cms.ContainerExtra;
import com.idanchuang.cms.server.domain.model.cms.container.ContainerCode;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageContainerDO;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;


public class CmsPageContainerTransfer {

    public static CmsPageContainerDO entityToDO(CmsPageContainer cmsPageContainer) {
        if (cmsPageContainer == null) {
            return null;
        }
        CmsPageContainerDO cmsPageContainerDO = new CmsPageContainerDO();
        cmsPageContainerDO.setId(cmsPageContainer.getId());
        cmsPageContainerDO.setPageId(cmsPageContainer.getPageId());
        cmsPageContainerDO.setContainerCode(cmsPageContainer.getContainerCode() != null ? cmsPageContainer.getContainerCode().getValue() : "");
        cmsPageContainerDO.setPageStyle(cmsPageContainer.getPageStyle());
        cmsPageContainerDO.setOperatorId(cmsPageContainer.getOperatorId());
        cmsPageContainerDO.setContainerName(cmsPageContainer.getContainerName());
        cmsPageContainerDO.setStatus(cmsPageContainer.getStatus());
        cmsPageContainerDO.setCreateTime(cmsPageContainer.getCreateTime());
        cmsPageContainerDO.setUpdateTime(cmsPageContainer.getUpdateTime());
        cmsPageContainerDO.setDeleted(cmsPageContainer.getDeleted());
        cmsPageContainerDO.setExtra(JsonUtil.toJsonString(cmsPageContainer.getContainerExtra()));
        return cmsPageContainerDO;
    }

    public static CmsPageContainer doToEntity(CmsPageContainerDO cmsPageContainerDO) {
        if (cmsPageContainerDO == null) {
            return null;
        }

        ContainerCode containerCode = StringUtils.isNotBlank(cmsPageContainerDO.getContainerCode()) ?
                new ContainerCode(cmsPageContainerDO.getContainerCode()) : null;

        ContainerExtra containerExtra = JsonUtil.toObject(cmsPageContainerDO.getExtra(), ContainerExtra.class);

        return new CmsPageContainer(cmsPageContainerDO.getId(), cmsPageContainerDO.getPageId(), containerCode,
                cmsPageContainerDO.getContainerName(), cmsPageContainerDO.getStatus(),
                cmsPageContainerDO.getPageStyle(), cmsPageContainerDO.getOperatorId(),
                cmsPageContainerDO.getCreateTime(), cmsPageContainerDO.getUpdateTime(),
                cmsPageContainerDO.getDeleted(), containerExtra);
    }
}
