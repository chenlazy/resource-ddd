package com.idanchuang.resource.server.infrastructure.transfer;

import com.idanchuang.resource.api.request.PrePutContentBatchReq;
import com.idanchuang.resource.api.request.PreviewPutContentReq;
import com.idanchuang.resource.api.response.PutResourceDTO;
import com.idanchuang.resource.server.domain.model.resource.PreviewPutContentBatchQuery;
import com.idanchuang.resource.server.domain.model.resource.PreviewPutContentQuery;
import com.idanchuang.resource.server.domain.model.resource.ResourcePutInfo;
import com.idanchuang.resource.server.infrastructure.persistence.model.ResourceConfigDO;

/**
 * @author fym
 * @description :
 * @date 2021/3/25 下午1:19
 */
public class ResourcePutInfoTransfer {

    public static ResourcePutInfo do2Model(ResourceConfigDO resourceConfigDO) {
        if (resourceConfigDO == null) {
            return null;
        }
        ResourcePutInfo resourcePutInfo = new ResourcePutInfo();
        resourcePutInfo.setId(resourceConfigDO.getId());
        resourcePutInfo.setResourceName(resourceConfigDO.getResourceName());
        resourcePutInfo.setPageCode(resourceConfigDO.getPageCode());
        resourcePutInfo.setBusinessType(resourceConfigDO.getBusinessType());
        resourcePutInfo.setResourceType(resourceConfigDO.getResourceType());
        resourcePutInfo.setResourceScheme(resourceConfigDO.getResourceScheme());
        resourcePutInfo.setResourceStatus(resourceConfigDO.getResourceStatus());
        resourcePutInfo.setCreateTime(resourceConfigDO.getCreateTime());
        resourcePutInfo.setResourceNumb(resourceConfigDO.getResourceNumb());
        return resourcePutInfo;
    }

    public static PutResourceDTO model2Dto(ResourcePutInfo resourcePutInfo) {
        if (resourcePutInfo == null) {
            return null;
        }
        PutResourceDTO putResourceDTO = new PutResourceDTO();
        putResourceDTO.setId(resourcePutInfo.getId());
        putResourceDTO.setResourceName(resourcePutInfo.getResourceName());
        putResourceDTO.setPageCode(resourcePutInfo.getPageCode());
        putResourceDTO.setBusinessType(resourcePutInfo.getBusinessType());
        putResourceDTO.setResourceStatus(resourcePutInfo.getResourceStatus());
        putResourceDTO.setCreateTime(resourcePutInfo.getCreateTime());
        putResourceDTO.setResourceNumb(resourcePutInfo.getResourceNumb());
        putResourceDTO.setPutInfoDTO(ResourceUnitTransfer.resourceUnit2PutInfoDTO(resourcePutInfo.getResourceUnit()));
        return putResourceDTO;
    }

    public static PreviewPutContentQuery req2ModelQuery(PreviewPutContentReq req) {
        if (req == null) {
            return null;
        }
        PreviewPutContentQuery previewPutContentQuery = new PreviewPutContentQuery();
        previewPutContentQuery.setPageCode(req.getPageCode());
        previewPutContentQuery.setBusiness(req.getBusiness());
        previewPutContentQuery.setRole(req.getRole());
        previewPutContentQuery.setPreResourceId(req.getPreResourceId());
        previewPutContentQuery.setResourceId(req.getResourceId());
        previewPutContentQuery.setUnitId(req.getUnitId());
        previewPutContentQuery.setPlatform(req.getPlatform());
        return previewPutContentQuery;
    }

    public static PreviewPutContentBatchQuery batchReq2ModelQuery(PrePutContentBatchReq req) {
        if (req == null) {
            return null;
        }
        PreviewPutContentBatchQuery previewPutContentBatchQuery = new PreviewPutContentBatchQuery();
        previewPutContentBatchQuery.setPageCode(req.getPageCode());
        previewPutContentBatchQuery.setBusiness(req.getBusiness());
        previewPutContentBatchQuery.setRole(req.getRole());
        previewPutContentBatchQuery.setPreResourceId(req.getPreResourceId());
        previewPutContentBatchQuery.setResourceIds(req.getResourceIds());
        previewPutContentBatchQuery.setUnitId(req.getUnitId());
        previewPutContentBatchQuery.setPlatform(req.getPlatform());
        return previewPutContentBatchQuery;
    }

    public static PreviewPutContentQuery req2BatchReq(PreviewPutContentBatchQuery req) {
        if (req == null) {
            return null;
        }
        PreviewPutContentQuery previewPutContentQuery = new PreviewPutContentQuery();
        previewPutContentQuery.setPageCode(req.getPageCode());
        previewPutContentQuery.setBusiness(req.getBusiness());
        previewPutContentQuery.setRole(req.getRole());
        previewPutContentQuery.setPreResourceId(req.getPreResourceId());
        previewPutContentQuery.setUnitId(req.getUnitId());
        previewPutContentQuery.setPlatform(req.getPlatform());
        return previewPutContentQuery;
    }
}
