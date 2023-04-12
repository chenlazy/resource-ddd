package com.idanchuang.cms.server.interfaces.assember;

import com.google.common.collect.Lists;
import com.idanchuang.cms.api.request.CmsCoreAddReq;
import com.idanchuang.cms.api.request.CmsCorePageReq;
import com.idanchuang.cms.api.request.CmsCoreShareReq;
import com.idanchuang.cms.api.request.CmsCoreUpdateReq;
import com.idanchuang.cms.api.request.CmsGoodsListReq;
import com.idanchuang.cms.api.request.CmsNormalGoodsPriceReq;
import com.idanchuang.cms.api.request.CmsPageTemplateReq;
import com.idanchuang.cms.api.response.CmsPageDetailDTO;
import com.idanchuang.cms.api.response.CmsPageGoodsCopyDTO;
import com.idanchuang.cms.api.response.CmsPageListDTO;
import com.idanchuang.cms.api.response.CmsPageTemplateDTO;
import com.idanchuang.cms.api.response.CmsShareModelDTO;
import com.idanchuang.cms.api.response.PageVersionDTO;
import com.idanchuang.cms.api.response.PriceDataDTO;
import com.idanchuang.cms.api.response.ShareJsonModelDTO;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreAdd;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreCreateOrUpdate;
import com.idanchuang.cms.server.domain.model.cms.CmsCorePageList;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreShare;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreShareImg;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreUpdate;
import com.idanchuang.cms.server.domain.model.cms.CmsGoodsList;
import com.idanchuang.cms.server.domain.model.cms.CmsNormalGoodsPrice;
import com.idanchuang.cms.server.domain.model.cms.CmsPageDetail;
import com.idanchuang.cms.server.domain.model.cms.CmsPageSchemaList;
import com.idanchuang.cms.server.domain.model.cms.CmsPageTemplate;
import com.idanchuang.cms.server.domain.model.cms.CmsPageTemplateList;
import com.idanchuang.cms.server.domain.model.cms.GoodsInfo;
import com.idanchuang.cms.server.domain.model.cms.PriceData;
import com.idanchuang.cms.server.domain.model.cms.niche.NicheId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.DateTimeUtils;
import com.idanchuang.resource.server.infrastructure.utils.DateTimeUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-07 13:55
 * @Desc: cms核心流程请求转化类
 * @Copyright VTN Limited. All rights reserved.
 */
public class CmsCoreBuildDtoAssembler {


    public static CmsCoreAdd toEntity(CmsCoreAddReq cmsCoreAddReq) {
        if (cmsCoreAddReq == null) {
            return null;
        }
        Long operatorId = Optional.ofNullable(cmsCoreAddReq.getOperatorId()).orElse(0L);
        CmsCoreAdd cmsCoreAdd = new CmsCoreAdd();
        cmsCoreAdd.setTagId(cmsCoreAddReq.getTagId());
        cmsCoreAdd.setOperatorId(operatorId.intValue());
        cmsCoreAdd.setPageTitle(cmsCoreAddReq.getPageTitle());
        cmsCoreAdd.setDescribe(cmsCoreAddReq.getDescribe());
        cmsCoreAdd.setAliasTitle(cmsCoreAddReq.getAliasTitle());
        cmsCoreAdd.setPlatform(cmsCoreAddReq.getPlatform());
        cmsCoreAdd.setCompData(cmsCoreAddReq.getCompData());
        cmsCoreAdd.setStartTime(cmsCoreAddReq.getStartTime());
        cmsCoreAdd.setEndTime(cmsCoreAddReq.getEndTime());
        cmsCoreAdd.setShareFlag(cmsCoreAddReq.getShareFlag());
        cmsCoreAdd.setShareTitle(cmsCoreAddReq.getShareTitle());
        cmsCoreAdd.setShareDesc(cmsCoreAddReq.getShareDesc());
        cmsCoreAdd.setShareImg(cmsCoreAddReq.getShareImg());
        cmsCoreAdd.setSharePoster(cmsCoreAddReq.getSharePoster());
        cmsCoreAdd.setShareScope(cmsCoreAddReq.getShareScope());
        cmsCoreAdd.setShareInfoList(convertCmsShare(cmsCoreAddReq.getShareInfoList()));
        cmsCoreAdd.setVersion(cmsCoreAddReq.getVersion());
        cmsCoreAdd.setSubjectGoodsType(cmsCoreAddReq.getSubjectGoodsType());
        cmsCoreAdd.setNicheIds(CollectionUtils.isEmpty(cmsCoreAddReq.getNicheIds()) ? Lists.newArrayList() :cmsCoreAddReq.getNicheIds().stream().map(NicheId::new).collect(Collectors.toList()));
        return cmsCoreAdd;
    }

    public static CmsCoreUpdate toEntity(CmsCoreUpdateReq coreUpdateReq) {
        if (coreUpdateReq == null) {
            return null;
        }
        CmsCoreUpdate cmsCoreUpdate = new CmsCoreUpdate();
        cmsCoreUpdate.setId(coreUpdateReq.getId());
        cmsCoreUpdate.setOperatorId(coreUpdateReq.getOperatorId());
        cmsCoreUpdate.setPlatform(coreUpdateReq.getPlatform());
        cmsCoreUpdate.setPageTitle(coreUpdateReq.getPageTitle());
        cmsCoreUpdate.setDescribe(coreUpdateReq.getDescribe());
        cmsCoreUpdate.setTemplateName(coreUpdateReq.getTemplateName());
        cmsCoreUpdate.setShareTitle(coreUpdateReq.getShareTitle());
        cmsCoreUpdate.setShareDesc(coreUpdateReq.getShareDesc());
        cmsCoreUpdate.setShareImg(coreUpdateReq.getShareImg());
        cmsCoreUpdate.setSharePoster(coreUpdateReq.getSharePoster());
        cmsCoreUpdate.setShareScope(coreUpdateReq.getShareScope());
        cmsCoreUpdate.setSharePosterList(coreUpdateReq.getSharePosterList());
        cmsCoreUpdate.setCompData(coreUpdateReq.getCompData());
        cmsCoreUpdate.setTemplateId(coreUpdateReq.getTemplateId());
        cmsCoreUpdate.setStartTime(coreUpdateReq.getStartTime());
        cmsCoreUpdate.setEndTime(coreUpdateReq.getEndTime());
        cmsCoreUpdate.setShareFlag(coreUpdateReq.getShareFlag());
        List<CmsCoreShareReq> shareInfoList = coreUpdateReq.getShareInfoList();
        cmsCoreUpdate.setShareInfoList(convertCmsShare(shareInfoList));
        cmsCoreUpdate.setVersion(coreUpdateReq.getVersion());
        cmsCoreUpdate.setNicheIds(CollectionUtils.isEmpty(coreUpdateReq.getNicheIds()) ? Lists.newArrayList() :coreUpdateReq.getNicheIds().stream().map(NicheId::new).collect(Collectors.toList()));

        return cmsCoreUpdate;
    }

    private static List<CmsCoreShare> convertCmsShare(List<CmsCoreShareReq> shareInfoList) {

        List<CmsCoreShare> shareList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(shareInfoList)) {

            shareInfoList.forEach(p -> {
                CmsCoreShare cmsCoreShare = new CmsCoreShare();
                cmsCoreShare.setShareTitle(p.getShareTitle());
                cmsCoreShare.setShareDesc(p.getShareDesc());
                cmsCoreShare.setShareScope(p.getShareScope());
                cmsCoreShare.setSharePoster(p.getSharePoster());
                cmsCoreShare.setShareImg(p.getShareImg());
                cmsCoreShare.setLevel(p.getLevel());
                cmsCoreShare.setSharePosterList(p.getSharePosterList());
                shareList.add(cmsCoreShare);
            });
        }
        return shareList;
    }

    public static CmsCorePageList toEntity(CmsCorePageReq cmsCorePageReq) {
        if (cmsCorePageReq == null) {
            return null;
        }
        CmsCorePageList cmsCorePageList = new CmsCorePageList();
        cmsCorePageList.setOld(cmsCorePageReq.getOld());
        cmsCorePageList.setId(cmsCorePageReq.getId());
        cmsCorePageList.setPageTitle(cmsCorePageReq.getPageTitle());
        cmsCorePageList.setAliasTitle(cmsCorePageReq.getAliasTitle());
        cmsCorePageList.setPlatform(cmsCorePageReq.getPlatform());
        cmsCorePageList.setTagId(cmsCorePageReq.getTagId());
        cmsCorePageList.setCurrent(cmsCorePageReq.getCurrent());
        cmsCorePageList.setSize(cmsCorePageReq.getSize());
        return cmsCorePageList;
    }

    public static CmsPageTemplateList toEntity(CmsPageTemplateReq pageTemplateReq) {
        if (pageTemplateReq == null) {
            return null;
        }
        CmsPageTemplateList cmsPageTemplateList = new CmsPageTemplateList();
        cmsPageTemplateList.setPageId(pageTemplateReq.getPageId());
        cmsPageTemplateList.setId(pageTemplateReq.getId());
        cmsPageTemplateList.setPageTitle(pageTemplateReq.getPageTitle());
        cmsPageTemplateList.setPageStatus(pageTemplateReq.getPageStatus());
        cmsPageTemplateList.setCurrent(pageTemplateReq.getCurrent());
        cmsPageTemplateList.setSize(pageTemplateReq.getSize());
        return cmsPageTemplateList;
    }

    public static CmsGoodsList toEntity(CmsGoodsListReq goodsListReq) {
        if (goodsListReq == null) {
            return null;
        }
        CmsGoodsList cmsGoodsList = new CmsGoodsList();
        cmsGoodsList.setComponentId(goodsListReq.getComponentId());
        cmsGoodsList.setLimit(goodsListReq.getLimit());
        return cmsGoodsList;
    }

    public static CmsNormalGoodsPrice toEntity(CmsNormalGoodsPriceReq normalGoodsPriceReq) {
        if (normalGoodsPriceReq == null) {
            return null;
        }
        CmsNormalGoodsPrice cmsNormalGoodsPrice = new CmsNormalGoodsPrice();
        cmsNormalGoodsPrice.setSubjectGoodsId(normalGoodsPriceReq.getSubjectGoodsId());
        cmsNormalGoodsPrice.setType(normalGoodsPriceReq.getType());
        cmsNormalGoodsPrice.setOperatorId(normalGoodsPriceReq.getOperatorId());
        List<PriceDataDTO> priceDataList = normalGoodsPriceReq.getPriceDataList();
        List<PriceData> newPriceDatas = new ArrayList<>();

        priceDataList.forEach(p -> {
            PriceData priceData = new PriceData();
            priceData.setAmountPromotionPrice(p.getAmountPromotionPrice());
            priceData.setCashValue(p.getCashValue());
            priceData.setPointValue(p.getPointValue());
            priceData.setPrice(p.getPrice());
            priceData.setType(p.getType());
            newPriceDatas.add(priceData);
        });
        cmsNormalGoodsPrice.setPriceDataList(newPriceDatas);

        return cmsNormalGoodsPrice;
    }

    public static CmsPageListDTO entityToDTO(CmsPageSchemaList pageSchemaList) {
        if (pageSchemaList == null) {
            return null;
        }
        CmsPageListDTO cmsPageListDTO = new CmsPageListDTO();
        cmsPageListDTO.setId(pageSchemaList.getId());
        cmsPageListDTO.setPageTitle(pageSchemaList.getPageTitle());
        cmsPageListDTO.setDescribe(pageSchemaList.getBackEndTitle());
        cmsPageListDTO.setAliasTitle(pageSchemaList.getAliasTitle());
        cmsPageListDTO.setPlatform(pageSchemaList.getPlatform());
        cmsPageListDTO.setStartTime(pageSchemaList.getStartTime());
        cmsPageListDTO.setEndTime(pageSchemaList.getEndTime());
        cmsPageListDTO.setOperatorId(pageSchemaList.getOperatorId());
        cmsPageListDTO.setOperator(pageSchemaList.getOperator());
        cmsPageListDTO.setShareDesc(pageSchemaList.getShareDesc());
        cmsPageListDTO.setShareTitle(pageSchemaList.getShareTitle());
        cmsPageListDTO.setTag(pageSchemaList.getTag());
        cmsPageListDTO.setUpdatedAt(pageSchemaList.getUpdatedAt());
        cmsPageListDTO.setMasterFlag(pageSchemaList.getMasterFlag());
        cmsPageListDTO.setStatus(pageSchemaList.getStatus());
        return cmsPageListDTO;
    }

    public static CmsPageDetailDTO entityToDTO(CmsPageDetail cmsPageDetail) {
        if (cmsPageDetail == null) {
            return null;
        }
        CmsPageDetailDTO cmsPageDetailDTO = new CmsPageDetailDTO();
        cmsPageDetailDTO.setId(cmsPageDetail.getId());
        cmsPageDetailDTO.setTagId(cmsPageDetail.getTagId());
        cmsPageDetailDTO.setPageTitle(cmsPageDetail.getPageTitle());
        cmsPageDetailDTO.setDescribe(cmsPageDetail.getPageName());
        cmsPageDetailDTO.setTemplateName(cmsPageDetail.getBackEndTitle());
        cmsPageDetailDTO.setAliasTitle(cmsPageDetail.getAliasTitle());
        cmsPageDetailDTO.setPlatform(cmsPageDetail.getPlatform());
        cmsPageDetailDTO.setPageStyle(cmsPageDetail.getPageStyle());
        cmsPageDetailDTO.setShareFlag(cmsPageDetail.getShareFlag());

        List<CmsCoreShareImg> shareInfoList = cmsPageDetail.getShareImgs();

        if (!CollectionUtils.isEmpty(shareInfoList)) {

            CmsCoreShareImg shareImg = shareInfoList.get(0);
            //设置通用的分享信息
            Integer shareScope = null != shareImg.getShareScope() ? shareImg.getShareScope() : 0;
            cmsPageDetailDTO.setShareTitle(shareImg.getShareTitle());
            cmsPageDetailDTO.setShareDesc(shareImg.getShareDesc());
            cmsPageDetailDTO.setShareScope(shareScope);
            cmsPageDetailDTO.setShareImg(shareImg.getShareImg());

            if (shareScope == 0) {
                cmsPageDetailDTO.setSharePoster(shareImg.getSharePoster());
                cmsPageDetailDTO.setShareInfoList(Lists.newArrayList());
            } else {
                List<CmsShareModelDTO> coreShareImgs = new ArrayList<>();
                shareInfoList.forEach(p -> {
                    CmsShareModelDTO cmsCoreShareImg = new CmsShareModelDTO();
                    cmsCoreShareImg.setShareDesc(p.getShareDesc());
                    cmsCoreShareImg.setLevel(p.getLevel());
                    cmsCoreShareImg.setShareImg(p.getShareImg());
                    cmsCoreShareImg.setShareTitle(p.getShareTitle());
                    cmsCoreShareImg.setShareScope(1);
                    cmsCoreShareImg.setSharePoster(p.getSharePoster());
                    coreShareImgs.add(cmsCoreShareImg);
                });
                cmsPageDetailDTO.setShareInfoList(coreShareImgs);
            }
        }
        cmsPageDetailDTO.setStartTime(cmsPageDetail.getStartTime());
        cmsPageDetailDTO.setEndTime(cmsPageDetail.getEndTime());
        cmsPageDetailDTO.setStatus(cmsPageDetail.getStatus());
        cmsPageDetailDTO.setParentId(cmsPageDetail.getParentId());
        cmsPageDetailDTO.setGoodsEnable(cmsPageDetail.getGoodsEnable());
        cmsPageDetailDTO.setGoodsEnableTime(cmsPageDetail.getGoodsEnableTime());
        cmsPageDetailDTO.setGoodsTagId(cmsPageDetail.getGoodsTagId());
        cmsPageDetailDTO.setPageCode(cmsPageDetail.getPageCode());
        return cmsPageDetailDTO;
    }

    public static CmsPageTemplateDTO entityToDTO(CmsPageTemplate cmsPageTemplate) {
        if (cmsPageTemplate == null) {
            return null;
        }
        CmsPageTemplateDTO cmsPageTemplateDTO = new CmsPageTemplateDTO();
        //cmsPageTemplateDTO.setId(cmsPageTemplate.getId());
        cmsPageTemplateDTO.setPageTitle(cmsPageTemplate.getPageTitle());
        cmsPageTemplateDTO.setStartTime(cmsPageTemplate.getStartTime());
        cmsPageTemplateDTO.setEndTime(cmsPageTemplate.getEndTime());
        cmsPageTemplateDTO.setStatus(cmsPageTemplate.getStatus());
        cmsPageTemplateDTO.setDescribe(cmsPageTemplate.getDescribe());
        cmsPageTemplateDTO.setOperator(cmsPageTemplate.getOperator());
        cmsPageTemplateDTO.setShareDesc(cmsPageTemplate.getShareDesc());
        cmsPageTemplateDTO.setShareTitle(cmsPageTemplate.getShareTitle());
        cmsPageTemplateDTO.setUpdatedAt(cmsPageTemplate.getUpdatedAt());
        return cmsPageTemplateDTO;
    }

    public static CmsPageGoodsCopyDTO entityToDTO(GoodsInfo goodsInfo) {
        if (goodsInfo == null) {
            return null;
        }
        CmsPageGoodsCopyDTO cmsPageGoodsCopyDTO = new CmsPageGoodsCopyDTO();
        cmsPageGoodsCopyDTO.setComponentId(goodsInfo.getComponentId());
        cmsPageGoodsCopyDTO.setGoodsId(goodsInfo.getGoodsId());
        cmsPageGoodsCopyDTO.setGoodsName(goodsInfo.getGoodsName());
        cmsPageGoodsCopyDTO.setGoodsImage(goodsInfo.getGoodsImage());
        cmsPageGoodsCopyDTO.setGoodsSubTitle(goodsInfo.getGoodsSubTitle());
        cmsPageGoodsCopyDTO.setOperatorId(goodsInfo.getOperatorId());
        cmsPageGoodsCopyDTO.setCreateTime(goodsInfo.getCreateTime());
        cmsPageGoodsCopyDTO.setSort(goodsInfo.getSort());
        cmsPageGoodsCopyDTO.setGiftImage(goodsInfo.getGiftImage());
        cmsPageGoodsCopyDTO.setSubjectGoodsType(goodsInfo.getSubjectGoodsType());
        cmsPageGoodsCopyDTO.setLimitNum(goodsInfo.getLimitNum());

        cmsPageGoodsCopyDTO.setUpdateTime(goodsInfo.getCreateTime());
        List<String> salesPoint = goodsInfo.getSalesPoint();
        cmsPageGoodsCopyDTO.setSalesPoint(!CollectionUtils.isEmpty(salesPoint) ? JsonUtil.toJsonString(salesPoint) : "");

        return cmsPageGoodsCopyDTO;
    }

    public static PriceDataDTO entityToDTO(PriceData priceData) {
        if (priceData == null) {
            return null;
        }
        PriceDataDTO priceDataDTO = new PriceDataDTO();
        priceDataDTO.setType(priceData.getType());
        priceDataDTO.setPrice(priceData.getPrice());
        priceDataDTO.setPointValue(priceData.getPointValue());
        priceDataDTO.setCashValue(priceData.getCashValue());
        priceDataDTO.setAmountPromotionPrice(priceData.getAmountPromotionPrice());
        return priceDataDTO;
    }

    public static CmsCoreCreateOrUpdate toCreateEntity(CmsCoreAdd cmsCoreAdd) {
        if (cmsCoreAdd == null) {
            return null;
        }
        CmsCoreCreateOrUpdate cmsCoreCreateOrUpdate = new CmsCoreCreateOrUpdate();
        cmsCoreCreateOrUpdate.setTagId(cmsCoreAdd.getTagId());
        cmsCoreCreateOrUpdate.setOperatorId(cmsCoreAdd.getOperatorId());
        cmsCoreCreateOrUpdate.setPageTitle(cmsCoreAdd.getPageTitle());
        cmsCoreCreateOrUpdate.setAliasTitle(cmsCoreAdd.getAliasTitle());
        cmsCoreCreateOrUpdate.setPlatform(cmsCoreAdd.getPlatform());
        cmsCoreCreateOrUpdate.setStartTime(cmsCoreAdd.getStartTime());
        cmsCoreCreateOrUpdate.setEndTime(cmsCoreAdd.getEndTime());
        cmsCoreCreateOrUpdate.setShareFlag(cmsCoreAdd.getShareFlag());
        cmsCoreCreateOrUpdate.setShareInfoList(cmsCoreAdd.getShareInfoList());
        cmsCoreCreateOrUpdate.setVersion(cmsCoreAdd.getVersion());
        cmsCoreCreateOrUpdate.setGoodsEnable(cmsCoreAdd.getGoodsEnable());
        cmsCoreCreateOrUpdate.setShareScope(cmsCoreAdd.getShareScope());
        cmsCoreCreateOrUpdate.setSubjectGoodsType(cmsCoreAdd.getSubjectGoodsType());

        cmsCoreCreateOrUpdate.setBackEndTitle(cmsCoreAdd.getDescribe());
        cmsCoreCreateOrUpdate.setNicheIds(cmsCoreAdd.getNicheIds());
        Integer shareScope = null != cmsCoreAdd.getShareScope() ? cmsCoreAdd.getShareScope() : 0;
        cmsCoreCreateOrUpdate.setPageStyle(cmsCoreAdd.getCompData());

        //如果是全部，设置一条记录
        if (shareScope == 0) {
            CmsCoreShare cmsCoreShare = new CmsCoreShare();
            cmsCoreShare.setSharePoster(cmsCoreAdd.getSharePoster());
            cmsCoreShare.setShareTitle(cmsCoreAdd.getShareTitle());
            cmsCoreShare.setShareImg(cmsCoreAdd.getShareImg());
            cmsCoreShare.setShareDesc(cmsCoreAdd.getShareDesc());
            cmsCoreShare.setShareScope(cmsCoreAdd.getShareScope());
            cmsCoreCreateOrUpdate.setShareInfoList(Lists.newArrayList(cmsCoreShare));
        } else {
            List<CmsCoreShare> shareInfoList = cmsCoreAdd.getShareInfoList();
            shareInfoList.parallelStream().forEach(p -> p.setShareScope(1));
            cmsCoreCreateOrUpdate.setShareInfoList(shareInfoList);
            cmsCoreCreateOrUpdate.setShareInfoList(cmsCoreAdd.getShareInfoList());
        }

        return cmsCoreCreateOrUpdate;
    }

    public static CmsCoreCreateOrUpdate toUpdateEntity(CmsCoreUpdate cmsCoreUpdate) {
        if (cmsCoreUpdate == null) {
            return null;
        }
        Long operatorId = Optional.ofNullable(cmsCoreUpdate.getOperatorId()).orElse(0L);
        CmsCoreCreateOrUpdate cmsCoreCreateOrUpdate = new CmsCoreCreateOrUpdate();
        cmsCoreCreateOrUpdate.setId(cmsCoreUpdate.getId());
        cmsCoreCreateOrUpdate.setTagId(cmsCoreUpdate.getTagId());
        cmsCoreCreateOrUpdate.setOperatorId(operatorId.intValue());
        cmsCoreCreateOrUpdate.setPageTitle(cmsCoreUpdate.getPageTitle());
        cmsCoreCreateOrUpdate.setPageCode(cmsCoreUpdate.getPageCode());
        cmsCoreCreateOrUpdate.setPlatform(cmsCoreUpdate.getPlatform());
        cmsCoreCreateOrUpdate.setTemplateName(cmsCoreUpdate.getTemplateName());
        cmsCoreCreateOrUpdate.setStartTime(cmsCoreUpdate.getStartTime());
        cmsCoreCreateOrUpdate.setEndTime(cmsCoreUpdate.getEndTime());
        cmsCoreCreateOrUpdate.setShareFlag(cmsCoreUpdate.getShareFlag());
        cmsCoreCreateOrUpdate.setShareScope(cmsCoreUpdate.getShareScope());
        cmsCoreCreateOrUpdate.setVersion(cmsCoreUpdate.getVersion());
        cmsCoreCreateOrUpdate.setTemplateId(cmsCoreUpdate.getTemplateId());
        cmsCoreCreateOrUpdate.setBackEndTitle(cmsCoreUpdate.getDescribe());
        cmsCoreCreateOrUpdate.setPageStyle(cmsCoreUpdate.getCompData());
        cmsCoreCreateOrUpdate.setNicheIds(cmsCoreUpdate.getNicheIds());
        Integer shareScope = null != cmsCoreUpdate.getShareScope() ? cmsCoreUpdate.getShareScope() : 0;
        //如果是全部，设置一条记录
        if (shareScope == 0 && !StringUtils.isEmpty(cmsCoreUpdate.getShareTitle())) {
            CmsCoreShare cmsCoreShare = new CmsCoreShare();
            cmsCoreShare.setShareImg(cmsCoreUpdate.getShareImg());
            cmsCoreShare.setShareDesc(cmsCoreUpdate.getShareDesc());
            cmsCoreShare.setSharePoster(cmsCoreUpdate.getSharePoster());
            cmsCoreShare.setShareTitle(cmsCoreUpdate.getShareTitle());
            cmsCoreShare.setShareScope(0);
            cmsCoreCreateOrUpdate.setShareInfoList(Lists.newArrayList(cmsCoreShare));
        } else {
            List<CmsCoreShare> shareInfoList = cmsCoreUpdate.getShareInfoList();
            shareInfoList.parallelStream().forEach(p -> p.setShareScope(1));
            cmsCoreCreateOrUpdate.setShareInfoList(shareInfoList);
        }
        return cmsCoreCreateOrUpdate;
    }

    public static PageVersionDTO entityToDTO(Masterplate masterplate) {

        if (null == masterplate) {
            return null;
        }

        LocalDateTime updateTime = masterplate.getUpdateTime();

        String versionTime = DateTimeUtils.localDateTime2String(updateTime, "yyyy.MM.dd HH:mm:ss");

        String version = masterplate.getId().getValue() + "" + masterplate.getVersion();

        return PageVersionDTO.builder().createTime(versionTime).templateId((int)masterplate.getId().getValue()).version(version).build();
    }
}
