package com.idanchuang.cms.server.interfaces.assember;

import com.google.common.collect.Lists;
import com.idanchuang.abmio.feign.dto.FileInfoDTO;
import com.idanchuang.cms.api.request.CmsCoreShareReq;
import com.idanchuang.cms.api.request.CmsCoreUpdateReq;
import com.idanchuang.cms.api.request.CmsPageTemplateReq;
import com.idanchuang.cms.api.response.*;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPage;
import com.idanchuang.cms.server.domainNew.model.cms.component.Component;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.external.niche.NicheId;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.SelectInfo;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplatePageQueryForm;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateShareForm;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateStatus;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateUpsertForm;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.ShareFlag;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.cms.server.interfaces.app.dto.ContainerDTO;
import com.idanchuang.resource.server.infrastructure.utils.DateTimeUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import com.idanchuang.sso.model.dto.system.UserDetailDTO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InterfaceMasterplateAssembler {

    private InterfaceMasterplateAssembler() {

    }

    public static MasterplatePageQueryForm dtoToDomain(CmsPageTemplateReq cmsPageTemplateReq) {

        if (cmsPageTemplateReq == null) {
            return null;
        }
        MasterplatePageQueryForm masterplatePageQueryForm = new MasterplatePageQueryForm();
        Integer pageId = cmsPageTemplateReq.getPageId() != null && cmsPageTemplateReq.getPageId() > 0 ? cmsPageTemplateReq.getPageId() : null;
        Integer masterplateId = cmsPageTemplateReq.getId() != null &&cmsPageTemplateReq.getId() > 0 ? cmsPageTemplateReq.getId() : null;
        masterplatePageQueryForm.setCatalogueId(null != pageId ? new CatalogueId(pageId) : null);
        masterplatePageQueryForm.setMasterplateId(null != masterplateId ? new MasterplateId(masterplateId) : null);
        masterplatePageQueryForm.setMasterplateName(cmsPageTemplateReq.getPageTitle());

        MasterplateStatus status = MasterplateStatus.fromVal(cmsPageTemplateReq.getPageStatus());

        if (status != null) {
            masterplatePageQueryForm.setStatus(status);
        }

        masterplatePageQueryForm.setCurrent(cmsPageTemplateReq.getCurrent());
        masterplatePageQueryForm.setSize(cmsPageTemplateReq.getSize());
        return masterplatePageQueryForm;

    }

    public static CmsPageTemplateDTO entityToDTO(Masterplate masterplate, Map<Long, UserDetailDTO> usersMap) {
        if (masterplate == null) {
            return null;
        }
        CmsPageTemplateDTO cmsPageTemplateDTO = new CmsPageTemplateDTO();
        cmsPageTemplateDTO.setId(masterplate.getId().getValue());
        cmsPageTemplateDTO.setPageTitle(masterplate.getMasterplateName());
        cmsPageTemplateDTO.setStartTime(DateTimeUtil.localDateTime2String(masterplate.getStartTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        cmsPageTemplateDTO.setEndTime(DateTimeUtil.localDateTime2String(masterplate.getEndTime(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));
        cmsPageTemplateDTO.setStatus(masterplate.getStatus().getVal());
        cmsPageTemplateDTO.setDescribe(masterplate.getAppTitle());

        //用户信息
        UserDetailDTO detailDTO = null != usersMap ? usersMap.get(masterplate.getOperatorId().getValue()) : null;
        cmsPageTemplateDTO.setOperator(null != detailDTO ? detailDTO.getRealName() : "");
        UserDetailDTO creator = null != usersMap ? usersMap.get(masterplate.getCreateId().getValue()) : null;
        cmsPageTemplateDTO.setCreator(null != creator ? creator.getRealName() : "");

        //分享标题等信息默认取第一个
        if (!CollectionUtils.isEmpty(masterplate.getShareForms())) {
            MasterplateShareForm masterplateShareForm = masterplate.getShareForms().get(0);
            cmsPageTemplateDTO.setShareDesc(masterplateShareForm.getShareDesc());
            cmsPageTemplateDTO.setShareTitle(masterplateShareForm.getShareTitle());

        }

        cmsPageTemplateDTO.setUpdatedAt(masterplate.getUpdateTime());
        return cmsPageTemplateDTO;
    }


    public static CmsPageDetailDTO domainToDto(Catalogue catalogue, ClientPage clientPage, Masterplate masterplate,
                                               List<Container> containers, Map<Long, FileInfoDTO> imageMap, List<SelectInfoDTO> selectInfo) {

        if (null == catalogue || null == clientPage || null == masterplate || CollectionUtils.isEmpty(containers)) {
            return null;
        }

        CmsPageDetailDTO pageDetailDTO = new CmsPageDetailDTO();
        pageDetailDTO.setId((int) catalogue.getId().getValue());
        pageDetailDTO.setTagId(clientPage.getId().getValue());
        pageDetailDTO.setTagName(clientPage.getName());
        pageDetailDTO.setPageTitle(masterplate.getAppTitle());
        pageDetailDTO.setDescribe(catalogue.getCatalogueName());
        pageDetailDTO.setTemplateName(masterplate.getMasterplateName());
        pageDetailDTO.setAliasTitle(catalogue.getAliasTitle());
        pageDetailDTO.setPlatform(PlatformCode.simplyPlatform(catalogue.getPlatform()));
        pageDetailDTO.setShareFlag(masterplate.getShareFlag().getVal());

        List<MasterplateShareForm> shareForms = masterplate.getShareForms();
        if (!CollectionUtils.isEmpty(shareForms)) {
            MasterplateShareForm shareForm = shareForms.get(0);
            int shareScope = null != shareForm.getShareScope() ? shareForm.getShareScope() : 0;
            pageDetailDTO.setShareTitle(shareForm.getShareTitle());
            pageDetailDTO.setShareDesc(shareForm.getShareDesc());
            pageDetailDTO.setShareScope(shareScope);

            ShareImgDTO shareImgDTO = new ShareImgDTO();
            if (shareForm.getShareImg() != null) {
                FileInfoDTO shareImg = null != imageMap ? imageMap.get(shareForm.getShareImg()) : null;
                shareImgDTO.setId(shareForm.getShareImg());
                shareImgDTO.setFileUrl(null != shareImg ? shareImg.getUrl() : "");
                pageDetailDTO.setShareImg(shareImgDTO);
            }

            if (shareScope == 0) {
                if (shareForm.getSharePoster() != null) {
                    ShareImgDTO sharePoster = new ShareImgDTO();
                    sharePoster.setId(shareForm.getSharePoster());
                    FileInfoDTO posterImg = null != imageMap ? imageMap.get(shareForm.getSharePoster()) : null;
                    sharePoster.setFileUrl(null != posterImg ? posterImg.getUrl() : "");
                    pageDetailDTO.setSharePoster(sharePoster);
                    pageDetailDTO.setSharePosterList(shareForm.getSharePosterList());
                }
                pageDetailDTO.setShareInfoList(Lists.newArrayList());
            } else {
                List<CmsShareModelDTO> shareImages = new ArrayList<>();
                shareForms.forEach(p -> {
                    CmsShareModelDTO cmsShareModelDTO = new CmsShareModelDTO();
                    cmsShareModelDTO.setShareDesc(p.getShareDesc());
                    cmsShareModelDTO.setLevel(p.getLevel());
                    cmsShareModelDTO.setShareTitle(p.getShareTitle());
                    cmsShareModelDTO.setShareScope(shareScope);
                    //小程序图片不同等级一致
                    cmsShareModelDTO.setShareImg(shareImgDTO);
                    //海报图随不同等级改变
                    if (p.getSharePoster() != null) {
                        ShareImgDTO sharePoster = new ShareImgDTO();
                        FileInfoDTO posterImg = null != imageMap ? imageMap.get(p.getSharePoster()) : null;
                        sharePoster.setId(p.getSharePoster());
                        sharePoster.setFileUrl(null != posterImg ? posterImg.getUrl() : "");
                        cmsShareModelDTO.setSharePoster(sharePoster);
                        cmsShareModelDTO.setSharePosterList(p.getSharePosterList());
                    }
                    shareImages.add(cmsShareModelDTO);
                });
                pageDetailDTO.setShareInfoList(shareImages);
            }
        }

        //设置样式信息
        List<ContainerDTO> containerDTOS = containers.stream().map(p -> new ContainerDTO(p.getId().getValue(), p.getContainerCode() != null ?
                p.getContainerCode().getValue() : "", p.getContainerName(), p.getPageStyle())).collect(Collectors.toList());
        pageDetailDTO.setPageStyle(JsonUtil.toJsonString(containerDTOS));

        pageDetailDTO.setStartTime(masterplate.getStartTime());
        pageDetailDTO.setEndTime(masterplate.getEndTime());
        pageDetailDTO.setStatus(masterplate.getStatus().getVal());
        pageDetailDTO.setGoodsEnable(masterplate.getExtra().getGoodsEnable().getVal());
        pageDetailDTO.setPageCode(null != clientPage.getPageCode() ? clientPage.getPageCode().getValue() : null);
        pageDetailDTO.setSelectInfoDTOS(selectInfo);
        return pageDetailDTO;
    }

    public static MasterplateUpsertForm dtoToDomain(CmsCoreUpdateReq coreUpdateReq) {

        if (null == coreUpdateReq) {
            return null;
        }

        MasterplateUpsertForm masterplateUpsertForm = new MasterplateUpsertForm();
        masterplateUpsertForm.setCatalogueId(new CatalogueId(coreUpdateReq.getId()));
        masterplateUpsertForm.setMasterplateId(null != coreUpdateReq.getTemplateId() ? new MasterplateId(coreUpdateReq.getTemplateId()) : null);
        masterplateUpsertForm.setOperatorId(new OperatorId(coreUpdateReq.getOperatorId()));
        masterplateUpsertForm.setAppTitle(coreUpdateReq.getPageTitle());
        masterplateUpsertForm.setBackEndTitle(coreUpdateReq.getDescribe());
        masterplateUpsertForm.setMasterplateName(coreUpdateReq.getTemplateName());
        masterplateUpsertForm.setPlatform(PlatformCode.fromDesc(coreUpdateReq.getPlatform()));
        masterplateUpsertForm.setPageStyle(coreUpdateReq.getCompData());
        masterplateUpsertForm.setStartTime(coreUpdateReq.getStartTime());
        masterplateUpsertForm.setEndTime(coreUpdateReq.getEndTime());
        masterplateUpsertForm.setShareFlag(ShareFlag.fromVal(coreUpdateReq.getShareFlag()));
        List<CmsCoreShareReq> shareInfoList = coreUpdateReq.getShareInfoList();

        //分享信息
        Integer shareScope = null != coreUpdateReq.getShareScope() ? coreUpdateReq.getShareScope() : 0;
        List<MasterplateShareForm> shareForms = Lists.newArrayList();

        //开启分享才设置分享数据
        if (coreUpdateReq.getShareFlag() == 1) {
            if (shareScope == 0) {
                MasterplateShareForm shareForm = MasterplateShareForm.builder().shareImg(coreUpdateReq.getShareImg()).shareScope(0).sharePoster(coreUpdateReq
                        .getSharePoster()).shareTitle(coreUpdateReq.getShareTitle()).shareDesc(coreUpdateReq.getShareDesc()).sharePosterList(coreUpdateReq.getSharePosterList()).build();
                shareForms.add(shareForm);
            } else if (!CollectionUtils.isEmpty(shareInfoList)) {
                shareForms = shareInfoList.stream().map(p -> MasterplateShareForm.builder().level(p.getLevel()).shareTitle(p.getShareTitle()).shareDesc(p.getShareDesc())
                        .shareImg(p.getShareImg()).sharePoster(p.getSharePoster()).shareScope(1).sharePosterList(p.getSharePosterList()).build()).collect(Collectors.toList());
            }
            masterplateUpsertForm.setShareInfoList(shareForms);
        }

        //资源位
        List<NicheId> nicheIds = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(coreUpdateReq.getNicheIds())) {
            nicheIds = coreUpdateReq.getNicheIds().stream().map(NicheId::new).collect(Collectors.toList());
        }
        masterplateUpsertForm.setNicheIds(nicheIds);
        if (!CollectionUtils.isEmpty(coreUpdateReq.getSelectInfoList())) {
            List<SelectInfo> collect = coreUpdateReq.getSelectInfoList().stream().map(u -> {
                SelectInfo selectInfo = new SelectInfo();
                selectInfo.setSelectType(u.getSelectType());
                selectInfo.setSelectId(u.getSelectId());
                return selectInfo;
            }).collect(Collectors.toList());
            masterplateUpsertForm.setSelectInfoList(collect);
        }
        return masterplateUpsertForm;
    }

}
