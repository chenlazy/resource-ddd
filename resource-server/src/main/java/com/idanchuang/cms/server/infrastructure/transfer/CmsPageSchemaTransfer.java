package com.idanchuang.cms.server.infrastructure.transfer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.idanchuang.cms.api.response.ShareImgDTO;
import com.idanchuang.cms.server.domain.model.cms.*;
import com.idanchuang.cms.server.domain.model.cms.factory.PageSchemaFactory;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaCode;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageDetailDO;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageSchemaDO;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageSchemaListDO;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-03 15:09
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class CmsPageSchemaTransfer {

    public static CmsPageSchemaDO entityToDO(CmsPageSchema cmsPageSchema) {
        if (cmsPageSchema == null) {
            return null;
        }
        CmsPageSchemaDO cmsPageSchemaDO = new CmsPageSchemaDO();
        cmsPageSchemaDO.setId(cmsPageSchema.getId());
        cmsPageSchemaDO.setPageName(cmsPageSchema.getPageName());
        cmsPageSchemaDO.setPageCode(cmsPageSchema.getSchemaCode()!= null?cmsPageSchema.getSchemaCode().getValue() : "");
        cmsPageSchemaDO.setPageType(cmsPageSchema.getPageType());
        cmsPageSchemaDO.setStatus(cmsPageSchema.getStatus());
        cmsPageSchemaDO.setCreateTime(cmsPageSchema.getCreateTime());
        cmsPageSchemaDO.setUpdateTime(cmsPageSchema.getUpdateTime());
        cmsPageSchemaDO.setPutVersions(cmsPageSchema.getPutVersions());
        cmsPageSchemaDO.setOperatorId(cmsPageSchema.getOperatorId());
        cmsPageSchemaDO.setTagId(cmsPageSchema.getTagId());
        cmsPageSchemaDO.setDeleted(cmsPageSchema.getDeleted());
        cmsPageSchemaDO.setExtra(JsonUtil.toJsonString(cmsPageSchema.getSchemaExtra()));
        return cmsPageSchemaDO;
    }

    public static CmsPageSchema doToEntity(CmsPageSchemaDO pageSchemaDO) {
        if (pageSchemaDO == null) {
            return null;
        }

        SchemaCode schemaCode = StringUtils.isNotBlank(pageSchemaDO.getPageCode()) ? new SchemaCode(pageSchemaDO.getPageCode()) : null;
        SchemaExtra schemaExtra = JsonUtil.toObject(pageSchemaDO.getExtra(), SchemaExtra.class);

        return PageSchemaFactory.createPageSchema(pageSchemaDO.getId(), schemaCode, pageSchemaDO.getOperatorId(),
                pageSchemaDO.getTagId(), pageSchemaDO.getStatus(), pageSchemaDO.getPutVersions(),
                pageSchemaDO.getPageName(), pageSchemaDO.getCreateTime(), pageSchemaDO.getUpdateTime(), schemaExtra);
    }

    public static CmsPageDetail doToPageDetail(CmsPageSchemaDO pageSchemaDO) {
        if (pageSchemaDO == null) {
            return null;
        }
        CmsPageDetail cmsPageSchema = new CmsPageDetail();
        cmsPageSchema.setId(pageSchemaDO.getId());
        cmsPageSchema.setPageTitle(pageSchemaDO.getPageName());
        cmsPageSchema.setPutVersions(pageSchemaDO.getPutVersions());
        cmsPageSchema.setStatus(pageSchemaDO.getStatus());
        return cmsPageSchema;
    }

    public static CmsPageDetail doToEntity(CmsPageDetailDO cmsPageDetailDO) {
        if (cmsPageDetailDO == null) {
            return null;
        }
        CmsPageDetail cmsPageDetail = new CmsPageDetail();
        cmsPageDetail.setId(cmsPageDetailDO.getId());
        cmsPageDetail.setTagId(cmsPageDetailDO.getTagId());
        cmsPageDetail.setBackEndTitle(cmsPageDetailDO.getBackEndTitle());
        cmsPageDetail.setAliasTitle(cmsPageDetailDO.getAliasTitle());
        cmsPageDetail.setPlatform(cmsPageDetailDO.getPlatform());
        cmsPageDetail.setShareFlag(cmsPageDetailDO.getShareFlag());
        cmsPageDetail.setPageId(cmsPageDetailDO.getPageId());
        cmsPageDetail.setSort(cmsPageDetailDO.getSort());
        cmsPageDetail.setStartTime(cmsPageDetailDO.getStartTime());
        cmsPageDetail.setEndTime(cmsPageDetailDO.getEndTime());
        cmsPageDetail.setGoodsEnable(cmsPageDetailDO.getGoodsEnable());
        cmsPageDetail.setGoodsEnableTime(cmsPageDetailDO.getGoodsEnableTime());
        cmsPageDetail.setGoodsTagId(cmsPageDetailDO.getGoodsTagId());
        cmsPageDetail.setStatus(cmsPageDetailDO.getStatus());
        cmsPageDetail.setParentId(cmsPageDetailDO.getParentId());
        cmsPageDetail.setPageTitle(cmsPageDetailDO.getPageTitle());
        cmsPageDetail.setPageName(cmsPageDetailDO.getPageName());
        cmsPageDetail.setPageCode(cmsPageDetailDO.getPageCode());

        String shareJson = cmsPageDetailDO.getShareJson();
        if (StringUtils.isNotBlank(shareJson)) {
            List<CmsCoreShare> cmsCoreShares = JsonUtil.toList(shareJson, new TypeReference<List<CmsCoreShare>>() {});
            if (!CollectionUtils.isEmpty(cmsCoreShares)) {
                List<CmsCoreShareImg> coreShareImgs = new ArrayList<>();
                cmsCoreShares.forEach(p -> {
                    CmsCoreShareImg cmsCoreShareImg = new CmsCoreShareImg();
                    cmsCoreShareImg.setLevel(p.getLevel());
                    cmsCoreShareImg.setShareDesc(p.getShareDesc());
                    cmsCoreShareImg.setShareTitle(p.getShareTitle());
                    cmsCoreShareImg.setShareScope(p.getShareScope());
                    if (p.getShareImg() != null) {
                        ShareImgDTO shareImg = new ShareImgDTO();
                        shareImg.setId(p.getShareImg());
                        cmsCoreShareImg.setShareImg(shareImg);
                    }
                    if (p.getSharePoster() != null) {
                        ShareImgDTO sharePoster = new ShareImgDTO();
                        sharePoster.setId(p.getSharePoster());
                        cmsCoreShareImg.setSharePoster(sharePoster);
                    }
                    coreShareImgs.add(cmsCoreShareImg);
                });
                cmsPageDetail.setShareImgs(coreShareImgs);
            }
        }

        return cmsPageDetail;
    }

    public static CmsPageSchemaList doToEntity(CmsPageSchemaListDO schemaListDO) {
        if (schemaListDO == null) {
            return null;
        }
        CmsPageSchemaList cmsPageSchemaList = new CmsPageSchemaList();
        cmsPageSchemaList.setId(schemaListDO.getId());
        cmsPageSchemaList.setBackEndTitle(schemaListDO.getBackEndTitle());
        cmsPageSchemaList.setAliasTitle(schemaListDO.getAliasTitle());
        cmsPageSchemaList.setPlatform(schemaListDO.getPlatform());
        cmsPageSchemaList.setOperatorId(schemaListDO.getOperatorId());
        cmsPageSchemaList.setOperator(schemaListDO.getOperator());
        cmsPageSchemaList.setTagId(schemaListDO.getTagId());
        cmsPageSchemaList.setStartTime(schemaListDO.getStartTime());
        cmsPageSchemaList.setEndTime(schemaListDO.getEndTime());
        cmsPageSchemaList.setMasterFlag(schemaListDO.getMasterFlag());
        cmsPageSchemaList.setStatus(schemaListDO.getStatus());

        cmsPageSchemaList.setPageTitle(schemaListDO.getPageName());
        cmsPageSchemaList.setUpdatedAt(schemaListDO.getUpdateTime());
        String shareJson = schemaListDO.getShareJson();
        if (StringUtils.isNotBlank(shareJson)) {
            List<CmsCoreShare> cmsCoreShares = JsonUtil.toList(shareJson, new TypeReference<List<CmsCoreShare>>() {});
            if (!CollectionUtils.isEmpty(cmsCoreShares)) {
                CmsCoreShare cmsCoreShare = cmsCoreShares.get(0);
                cmsPageSchemaList.setShareDesc(cmsCoreShare.getShareDesc());
                cmsPageSchemaList.setShareTitle(cmsCoreShare.getShareTitle());
            }
        }
        return cmsPageSchemaList;
    }
}
