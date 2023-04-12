package com.idanchuang.cms.server.applicationNew.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.idanchuang.cms.api.model.CmsGoods;
import com.idanchuang.cms.api.response.GoodsComponentPageDTO;
import com.idanchuang.cms.api.response.GoodsInfoDTO;
import com.idanchuang.cms.api.response.SubjectEnableGoodsInfoDTO;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.constant.RedisKeyConstant;
import com.idanchuang.cms.server.application.enums.PageStatusEnum;
import com.idanchuang.cms.server.domain.model.cms.ActivityPage;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.component.*;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.GoodsEnable;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.component.redis.util.RedisUtil;
import com.idanchuang.resource.server.infrastructure.common.constant.RedisBusinessKeyConstant;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author fym
 * @description :
 * @date 2022/1/5 下午2:18
 */
@Slf4j
@Service
public class ComponentGoodsService {

    @Resource
    private CacheUtil cacheUtil;
    @Resource
    private CatalogueRepository catalogueRepository;
    @Resource
    private ComponentRepository componentRepository;
    @Resource
    private ContainerRepository containerRepository;
    @Resource
    private MasterplateRepository masterplateRepository;

    public List<GoodsInfoDTO> selectListForGoods(Long componentId) {
        Component containerComponent = componentRepository.getComponentById(new ComponentId(componentId));
        if (containerComponent == null) {
            return null;
        }
        if (containerComponent.getComponentBusinessType() == null || containerComponent.getComponentBusinessType().getModelType() != 1) {
            return null;
        }
        String bizJson = containerComponent.getBizJson();
        if (StringUtils.isBlank(bizJson)) {
            return null;
        }
        // 商品信息
        List<CmsGoods> goodsList = JSON.parseArray(bizJson, CmsGoods.class);
        if (CollectionUtils.isEmpty(goodsList)) {
            return null;
        }
        List<GoodsInfoDTO> goodsInfoList = goodsList.stream().map(this::convertOf).collect(Collectors.toList());
        Masterplate cmsPage = null;
        Container pageContainer = containerRepository.getContainerById(containerComponent.getContainerId());
        if (pageContainer != null) {
            Masterplate page = masterplateRepository.getMasterplateById(pageContainer.getMasterplateId());
            if (page != null && PageStatusEnum.PAGE_STATUS_PUBLISH.getStatus().equals(page.getStatus())) {
                cmsPage = page;
            }
        }
        for (GoodsInfoDTO goodsInfo : goodsInfoList) {
            if (cmsPage != null) {
                goodsInfo.setPageSchemaId((int) cmsPage.getCatalogueId().getValue());
                goodsInfo.setPageId((int) cmsPage.getId().getValue());
            }
        }
        return goodsInfoList;
    }

    public List<SubjectEnableGoodsInfoDTO> getSubjectGoodsList(LocalDateTime time) {
        Long subjectTagId = SystemConfig.getInstance().getSubjectTagId();
        List<Catalogue> catalogues = catalogueRepository.queryAllCatalogByTagId(new ClientPageId(subjectTagId));
        if (CollectionUtils.isEmpty(catalogues)) {
            return null;
        }
        List<SubjectEnableGoodsInfoDTO> goodsInfoDTO = Lists.newArrayList();
        for (Catalogue pageSchema : catalogues) {
            if (pageSchema.getId() == null) {
                continue;
            }
            List<Masterplate> pageList = masterplateRepository.getMasterplateList(pageSchema.getId());
            if (CollectionUtils.isEmpty(pageList)) {
                continue;
            }
            pageList = pageList.stream().filter(e -> e.getExtra() != null
                    && e.getExtra().getGoodsEnable().getVal() == 1
                    && e.getEndTime() != null
                    && e.getEndTime().compareTo(time) <= 0).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(pageList)) {
                continue;
            }
            for (Masterplate page : pageList) {
                String key = format(RedisKeyConstant.SUBJECT_GOODS_MARK_LIST_KEY, page.getId().getValue());
                String goodsIdsStr = RedisUtil.getInstance().get(key);
                List<Long> goodsIds = JsonUtil.toList(goodsIdsStr, new TypeReference<List<Long>>() {
                });
                SubjectEnableGoodsInfoDTO subjectEnableGoodsInfoDTO = new SubjectEnableGoodsInfoDTO();
                subjectEnableGoodsInfoDTO.setSubjectId(page.getId().getValue());
                subjectEnableGoodsInfoDTO.setGoodsIds(goodsIds);
                goodsInfoDTO.add(subjectEnableGoodsInfoDTO);
            }
        }
        return goodsInfoDTO;
    }

    public void updateSubjectEnable(Long subjectId, Integer enable) {
        if (subjectId == null) {
            return;
        }
        masterplateRepository.updateGoodsEnable(new MasterplateId(subjectId), GoodsEnable.fromVal(enable));
    }

    public List<GoodsComponentPageDTO> getGoodsComponentPage(List<Integer> tagIdList) {
        if (CollectionUtils.isEmpty(tagIdList)) {
            return null;
        }
        List<ClientPageId> collect = tagIdList.stream().map(ClientPageId::new).collect(Collectors.toList());
        List<Catalogue> catalogues = catalogueRepository.queryAllCatalogByTagIds(collect);
        if (CollectionUtils.isEmpty(catalogues)) {
            return null;
        }
        Map<Long, Catalogue> catalogueMap = catalogues.stream().collect(Collectors.toMap(e -> e.getId().getValue(), e -> e, (s1, s2) -> s1));
        List<Masterplate> pageList = masterplateRepository.getCmsPageListForValid(catalogues.stream().map(Catalogue::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(pageList)) {
            return null;
        }
        Map<Long, Masterplate> pageMap = Maps.newHashMap();
        for (Masterplate cmsPage : pageList) {
            Masterplate page = pageMap.get(cmsPage.getCatalogueId().getValue());
            if (page == null || cmsPage.getStartTime().compareTo(page.getStartTime()) > 0 || cmsPage.getId().getValue() > page.getId().getValue()) {
                pageMap.put(cmsPage.getCatalogueId().getValue(), cmsPage);
            }
        }
        pageList = new ArrayList<>(pageMap.values());
        List<Container> containers = containerRepository.queryContainerByMasterplateIds(pageList.stream().map(u -> u.getId()).collect(Collectors.toList()));
        Map<Long, List<Container>> map = containers.stream().collect(Collectors.groupingBy(x -> x.getMasterplateId().getValue(), Collectors.toList()));
        Set<Long> containerIdList = containers.stream().map(u -> u.getId().getValue()).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(containerIdList)) {
            return null;
        }
        Map<Long, List<Component>> containerComponentMap = this.getContainerComponentMap(containerIdList);
        List<GoodsComponentPageDTO> goodsComponentPageList = Lists.newArrayList();
        GoodsComponentPageDTO goodsComponentPage = null;
        for (Masterplate page : pageList) {
            List<Component> componentList = Lists.newArrayList();
            List<Container> containerIds = map.get(page.getId().getValue());
            containerIds.forEach(u -> {
                if (!CollectionUtils.isEmpty(containerComponentMap.get(u.getId().getValue()))) {
                    componentList.addAll(containerComponentMap.get(u.getId().getValue()));
                }
            });
            if (CollectionUtils.isEmpty(componentList)) {
                continue;
            }
            Set<Long> goodsIdSet = new HashSet<>();
            for (Component component : componentList) {
                if (StringUtils.isBlank(component.getBizJson())) {
                    continue;
                }
                List<CmsGoods> goodsList = JSON.parseArray(component.getBizJson(), CmsGoods.class);
                if (CollectionUtils.isEmpty(goodsList)) {
                    continue;
                }
                goodsIdSet.addAll(goodsList.stream().map(CmsGoods::getGoodsId).collect(Collectors.toList()));
            }
            goodsComponentPage = GoodsComponentPageDTO.builder()
                    .id((int) page.getCatalogueId().getValue())
                    .pageId((int) page.getId().getValue())
                    .tagId(page.getPageId() == null ? null : (int) page.getPageId().getValue())
                    .pageTitle(page.getAppTitle())
                    .aliasTitle(catalogueMap.get(page.getCatalogueId().getValue()) != null ? catalogueMap.get(page.getCatalogueId().getValue()).getAliasTitle() : "")
                    .startTime(page.getStartTime())
                    .endTime(page.getEndTime())
                    .goodsIdList(new ArrayList<>(goodsIdSet))
                    .build();
            goodsComponentPageList.add(goodsComponentPage);
        }
        return goodsComponentPageList;
    }

    public Long getGoodsTagBySubjectId(Long id) {
        String tagKey = format(RedisKeyConstant.SUBJECT_GOODS_TAG_ID_KEY, id);
        return cacheUtil.get(tagKey, Long.class);
    }

    public Map<Long, List<ActivityPage>> queryActivityOnSubjectListBySpuId(Set<Long> spuId) {
        Map<Long, List<ActivityPage>> maps = Maps.newHashMap();
        for (Long m : spuId) {
            String infoKey = format(RedisBusinessKeyConstant.CMS_SUBJECT_DDD_BY_SPU_KEY, m);
            String info = cacheUtil.getForString(infoKey);
            List<ActivityPage> cmsPages = JsonUtil.toListErrorNull(info, new TypeReference<List<ActivityPage>>() {
            });
            List<ActivityPage> activityPages = Lists.newArrayList();
            if (null == info || cmsPages == null) {
                //无缓存走db
                cmsPages = masterplateRepository.queryActivityPageBySpuId(m);
                cacheUtil.set(infoKey, JsonUtil.toJsonString(cmsPages), SystemConfig.getInstance().getQueryActivityOnSubjectListBySpuIdExpireTime(), TimeUnit.SECONDS);
                Map<Long, ActivityPage> pageMap = cmsPages.stream().collect(Collectors.toMap(e -> e.getPageId(), e -> e, (e1, e2) -> e1));
                List<ActivityPage> collect = pageMap.values().stream().collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    collect.stream().forEach(u -> activityPages.add(u));
                }
            } else {
                //有缓存
                Map<Long, ActivityPage> pageMap = cmsPages.stream().collect(Collectors.toMap(e -> e.getPageId(), e -> e, (e1, e2) -> e1));
                List<ActivityPage> collect = pageMap.values().stream().collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    collect.stream().forEach(u -> {
                        if (LocalDateTime.now().isAfter(u.getStartTime()) && LocalDateTime.now().isBefore(u.getEndTime())) {
                            activityPages.add(u);
                        }
                    });
                }
            }
            if (CollectionUtils.isNotEmpty(activityPages)) {
                maps.put(m, activityPages);
            }
        }
        return maps;
    }

    private ActivityPage cmsPage2ActivityPage(Masterplate cmsPage) {
        ActivityPage activityPage = new ActivityPage();
        activityPage.setPageId(cmsPage.getCatalogueId().getValue());
        activityPage.setStartTime(cmsPage.getStartTime());
        activityPage.setEndTime(cmsPage.getEndTime());
        return activityPage;
    }

    private Map<Long, List<Component>> getContainerComponentMap(Set<Long> containerIdList) {
        ComponentQueryForm pageQueryForm = new ComponentQueryForm();
        pageQueryForm.setContainerIds(containerIdList.stream().map(ContainerId::new).collect(Collectors.toList()));
        pageQueryForm.setBusinessType(ComponentBusinessType.BUSINESS_TYPE_GOODS);
        List<Component> componentList = componentRepository.queryComponentList(pageQueryForm);
        if (CollectionUtils.isEmpty(componentList)) {
            return Maps.newHashMap();
        }
        return componentList.stream().collect(Collectors.groupingBy(x -> x.getContainerId().getValue(), Collectors.toList()));
    }

    private GoodsInfoDTO convertOf(CmsGoods source) {
        return GoodsInfoDTO.builder()
                .goodsId(source.getGoodsId())
                .goodsNameEnable(source.getGoodsNameEnable())
                .goodsName(source.getGoodsName())
                .goodsImageEnable(source.getGoodsImageEnable())
                .goodsImage(source.getGoodsImage())
                .goodsSubTitleEnable(source.getGoodsSubTitleEnable())
                .goodsSubTitle(source.getGoodsSubTitle())
                .salesPoint(source.getSalesPoint() == null ? null : JSON.parseArray(source.getSalesPoint(), String.class))
                .operatorId(source.getOperatorId())
                .createTime(source.getCreateTime())
                .updateTime(source.getUpdateTime())
                .giftImage(source.getGiftImage())
                .subjectGoodsType(source.getSubjectGoodsType())
                .limitNum(source.getLimitNum())
                .swellPercentage(source.getSwellPercentage())
                .textBgImage(source.getTextBgImage())
                .componentId(source.getComponentId())
                .build();
    }
}
