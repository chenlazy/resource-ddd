package com.idanchuang.cms.server.interfaces.assember;

import com.idanchuang.cms.api.request.CmsPageSchemaCreateReq;
import com.idanchuang.cms.api.request.CmsPageSchemaUpdateReq;
import com.idanchuang.cms.api.response.CmsPageSchemaDTO;
import com.idanchuang.cms.server.domain.model.cms.CmsPageSchema;
import com.idanchuang.cms.server.domain.model.cms.factory.PageSchemaFactory;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-03 14:52
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class CmsPageSchemaDtoAssembler {

    public static CmsPageSchema toEntity(CmsPageSchemaCreateReq schemaCreateReq) {
        if (schemaCreateReq == null) {
            return null;
        }

        return PageSchemaFactory.createPageSchema(schemaCreateReq.getId().intValue(), null,
                schemaCreateReq.getOperatorId().intValue(), schemaCreateReq.getTagId(), null,
                schemaCreateReq.getPutVersions(), schemaCreateReq.getPageName(), null, null, null);
    }

    public static CmsPageSchema toEntity(CmsPageSchemaUpdateReq schemaUpdateReq) {
        if (schemaUpdateReq == null) {
            return null;
        }

        return PageSchemaFactory.createPageSchema(schemaUpdateReq.getId(), null,
                schemaUpdateReq.getOperatorId(), null, null,
                schemaUpdateReq.getPutVersions(), schemaUpdateReq.getPageName(), null, null, null);
    }


    public static CmsPageSchemaDTO entityToDTO(CmsPageSchema cmsPageSchema) {
        if (cmsPageSchema == null) {
            return null;
        }
        CmsPageSchemaDTO cmsPageSchemaDTO = new CmsPageSchemaDTO();
        cmsPageSchemaDTO.setId(cmsPageSchema.getId());
        cmsPageSchemaDTO.setPageName(cmsPageSchema.getPageName());
        cmsPageSchemaDTO.setPageType(cmsPageSchema.getPageType());
        cmsPageSchemaDTO.setStatus(cmsPageSchema.getStatus());
        cmsPageSchemaDTO.setPutVersions(cmsPageSchema.getPutVersions());
        cmsPageSchemaDTO.setOperatorId(cmsPageSchema.getOperatorId());
        cmsPageSchemaDTO.setCreateTime(cmsPageSchema.getCreateTime());
        cmsPageSchemaDTO.setUpdateTime(cmsPageSchema.getUpdateTime());
        cmsPageSchemaDTO.setDeleted(cmsPageSchema.getDeleted());
        return cmsPageSchemaDTO;
    }
}
