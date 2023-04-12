package com.idanchuang.cms.server.infrastructure.transfer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.idanchuang.cms.server.application.enums.PageTemplateEnum;
import com.idanchuang.cms.server.domain.model.cms.CmsCompData;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreShare;
import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.domain.model.cms.CmsPageTemplate;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageDO;
import com.idanchuang.resource.server.infrastructure.utils.DateTimeUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 15:10
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class CmsPageTransfer {

    public static CmsPageDO entityToDO(CmsPage cmsPage) {
        if (cmsPage == null) {
            return null;
        }
        CmsPageDO cmsPageDO = new CmsPageDO();
        cmsPageDO.setId(cmsPage.getId());
        cmsPageDO.setPageSchemaId(cmsPage.getPageSchemaId());
        cmsPageDO.setPageName(cmsPage.getPageName());
        cmsPageDO.setPageType(cmsPage.getPageType());
        cmsPageDO.setBackEndTitle(cmsPage.getBackEndTitle());
        cmsPageDO.setTagId(cmsPage.getTagId());
        cmsPageDO.setPlatform(cmsPage.getPlatform());
        cmsPageDO.setStatus(cmsPage.getStatus());
        //入库时全部转换为json字符串
        cmsPageDO.setSort(JsonUtil.toJsonString(cmsPage.getSort()));
        cmsPageDO.setShareJson(cmsPage.getShareJson());
        cmsPageDO.setStartTime(cmsPage.getStartTime());
        cmsPageDO.setAliasTitle(cmsPage.getAliasTitle());
        cmsPageDO.setShareFlag(cmsPage.getShareFlag());
        cmsPageDO.setEndTime(cmsPage.getEndTime());
        cmsPageDO.setCreateTime(cmsPage.getCreateTime());
        cmsPageDO.setUpdateTime(cmsPage.getUpdateTime());
        cmsPageDO.setGoodsEnable(cmsPage.getGoodsEnable());
        cmsPageDO.setOperatorId(cmsPage.getOperatorId());
        cmsPageDO.setDeleted(cmsPage.getDeleted());
        return cmsPageDO;
    }

    public static CmsPage dOToEntity(CmsPageDO cmsPageDO) {
        if (cmsPageDO == null) {
            return null;
        }

        List<String> sort = Lists.newArrayList();

        if(!StringUtils.isEmpty(cmsPageDO.getSort())){
            sort = NumberUtils.isCreatable(cmsPageDO.getSort()) ? Collections.singletonList(cmsPageDO.getSort()) : JsonUtil.toList(cmsPageDO.getSort(),new TypeReference<List<String>>() {
            });
        }

        return new CmsPage(cmsPageDO.getId(), cmsPageDO.getPageSchemaId(), cmsPageDO.getPageName(),
                cmsPageDO.getPageType(), cmsPageDO.getBackEndTitle(), cmsPageDO.getTagId(), cmsPageDO.getPlatform(),
                cmsPageDO.getStatus(),sort , cmsPageDO.getAliasTitle(), cmsPageDO.getShareFlag(),
                cmsPageDO.getShareJson(), cmsPageDO.getStartTime(), cmsPageDO.getEndTime(),
                cmsPageDO.getGoodsEnable(), cmsPageDO.getOperatorId(), cmsPageDO.getCreateTime(), cmsPageDO.getUpdateTime(),
                cmsPageDO.getDeleted(), cmsPageDO.getVersion());
    }

    public static CmsPageTemplate dOToTemplate(CmsPageDO cmsPageDO) {
        if (null == cmsPageDO) {
            return null;
        }

        CmsPageTemplate cmsPageTemplate = new CmsPageTemplate();
        cmsPageTemplate.setId(cmsPageDO.getId());
        cmsPageTemplate.setPageTitle(cmsPageDO.getBackEndTitle());
        LocalDateTime startTime = cmsPageDO.getStartTime();
        LocalDateTime endTime = cmsPageDO.getEndTime();
        if (null != startTime) {
            cmsPageTemplate.setStartTime(DateTimeUtil.localDateTime2String(cmsPageDO.getStartTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        }
        if (null != endTime) {
            cmsPageTemplate.setEndTime(DateTimeUtil.localDateTime2String(cmsPageDO.getEndTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        }

        Integer status = PageTemplateEnum.PAGE_WAIT_VALID.getStatus();
        //转化模版状态
        if (null != startTime && null != endTime) {

            if (LocalDateTime.now().isBefore(startTime)) {
                status = PageTemplateEnum.PAGE_WAIT_VALID.getStatus();
            }

            if (LocalDateTime.now().isAfter(startTime) && LocalDateTime.now().isBefore(endTime)) {
                status = PageTemplateEnum.PAGE_VALID.getStatus();
            }

            if (LocalDateTime.now().isAfter(endTime)) {
                status = PageTemplateEnum.PAGE_EXPIRED.getStatus();
            }
        }

        if (null == endTime && null == startTime) {
            status = PageTemplateEnum.PAGE_VALID.getStatus();
        }

        if (null != startTime && null == endTime) {
            status = startTime.isAfter(LocalDateTime.now()) ? PageTemplateEnum.PAGE_WAIT_VALID.getStatus() :
                    PageTemplateEnum.PAGE_VALID.getStatus();
        }

        if (null == startTime && null != endTime) {
            status = endTime.isBefore(LocalDateTime.now()) ? PageTemplateEnum.PAGE_EXPIRED.getStatus() :
                    PageTemplateEnum.PAGE_VALID.getStatus();
        }
        cmsPageTemplate.setStatus(status);
        cmsPageTemplate.setDescribe(cmsPageDO.getPageName());
        cmsPageTemplate.setOperatorId(cmsPageDO.getOperatorId());
        cmsPageTemplate.setUpdatedAt(cmsPageDO.getUpdateTime());
        String shareJson = cmsPageDO.getShareJson();

        if (!StringUtils.isEmpty(shareJson)) {
            List<CmsCoreShare> cmsCoreShares = JsonUtil.toList(shareJson, new TypeReference<List<CmsCoreShare>>() {});

            if (!CollectionUtils.isEmpty(cmsCoreShares)) {
                CmsCoreShare cmsCoreShare = cmsCoreShares.get(0);
                cmsPageTemplate.setShareTitle(cmsCoreShare.getShareTitle());
                cmsPageTemplate.setShareDesc(cmsCoreShare.getShareDesc());
            }
        }
        return cmsPageTemplate;
    }
}
