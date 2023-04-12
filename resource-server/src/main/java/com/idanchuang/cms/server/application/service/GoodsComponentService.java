package com.idanchuang.cms.server.application.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.idanchuang.cms.api.model.CmsGoods;
import com.idanchuang.cms.api.response.GoodsComponentPageDTO;
import com.idanchuang.cms.api.response.GoodsInfoDTO;
import com.idanchuang.cms.api.response.PriceDataDTO;
import com.idanchuang.cms.api.response.SubjectEnableGoodsInfoDTO;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.constant.RedisKeyConstant;
import com.idanchuang.cms.server.application.enums.PageStatusEnum;
import com.idanchuang.cms.server.domain.model.cms.*;
import com.idanchuang.cms.server.domain.repository.CmsPageContainerRepository;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import com.idanchuang.cms.server.domain.repository.CmsPageSchemaRepository;
import com.idanchuang.cms.server.domain.repository.ContainerComponentRepository;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageSchemaListDO;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author lei.liu
 * @date 2021/9/13
 */
@Slf4j
@Service
public class GoodsComponentService {

    @Resource
    private CmsPageRepository cmsPageRepository;

    @Resource
    private CmsPageContainerService cmsPageContainerService;

    @Resource
    private ContainerComponentService containerComponentService;

    @Resource
    private CacheUtil cacheUtil;

    @Resource
    private ContainerComponentRepository containerComponentRepository;

    @Resource
    private ComponentGoodsPriceService componentGoodsPriceService;

    @Resource
    private CmsPageSchemaRepository cmsPageSchemaRepository;


    @Resource
    private CmsPageContainerRepository cmsPageContainerRepository;


    public List<GoodsComponentPageDTO> getGoodsComponentPage(List<Integer> tagIdList) {
        if (CollectionUtils.isEmpty(tagIdList)) {
            return null;
        }

        CmsCorePageList condition = new CmsCorePageList();
        condition.setTagIdList(tagIdList.stream().map(Integer::longValue).collect(Collectors.toList()));
        List<CmsPageSchemaListDO> pageSchemaList = cmsPageSchemaRepository.queryByCondition(condition);
        if (CollectionUtils.isEmpty(pageSchemaList)) {
            return null;
        }

        List<CmsPage> pageList = cmsPageRepository.getCmsPageListForValid(pageSchemaList.stream().map(e -> e.getId().intValue()).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(pageList)) {
            return null;
        }

        Map<Integer, CmsPage> pageMap = new HashMap<>();
        for (CmsPage cmsPage : pageList) {
            CmsPage page = pageMap.get(cmsPage.getPageSchemaId());
            if (page == null || cmsPage.getStartTime().compareTo(page.getStartTime()) > 0 || cmsPage.getId().compareTo(page.getId()) > 0) {
                pageMap.put(cmsPage.getPageSchemaId(), cmsPage);
            }
        }

        pageList = new ArrayList<>(pageMap.values());
        Set<Long> containerIdList = Sets.newHashSet();
        pageList.forEach(cmsPage -> {
            containerIdList.addAll(cmsPage.getSort().stream().map(Long::parseLong).collect(Collectors.toList()));
        });

        if (CollectionUtils.isEmpty(containerIdList)) {
            return null;
        }
        Map<Long, List<ContainerComponent>> containerComponentMap = getContainerComponentMap(containerIdList);

        List<GoodsComponentPageDTO> goodsComponentPageList = new ArrayList<>();
        GoodsComponentPageDTO goodsComponentPage = null;
        for (CmsPage page : pageList) {
            List<String> containerIds = page.getSort();
            if (CollectionUtils.isEmpty(containerIds)) {
                continue;
            }

            List<ContainerComponent> componentList = Lists.newArrayList();

            containerIds.forEach(s -> {
                if(containerComponentMap.get(Long.parseLong(s)) != null) {
                    componentList.addAll(containerComponentMap.get(Long.parseLong(s)));
                }
            });

            if (CollectionUtils.isEmpty(componentList)) {
                continue;
            }

            Set<Long> goodsIdSet = new HashSet<>();
            for (ContainerComponent component : componentList) {
                if (StringUtils.isBlank(component.getBizJson())) {
                    continue;
                }

                List<CmsGoods> goodsList = JSON.parseArray(component.getBizJson(), CmsGoods.class);
                if (CollectionUtils.isEmpty(goodsList)) {
                    continue;
                }

                goodsIdSet.addAll(goodsList.stream().map(CmsGoods::getGoodsId).collect(Collectors.toList()));
            }
            goodsComponentPage= GoodsComponentPageDTO.builder()
                    .id(page.getPageSchemaId())
                    .pageId(page.getId())
                    .tagId(page.getTagId() == null ? null : page.getTagId().intValue())
                    .pageTitle(page.getPageName())
                    .aliasTitle(page.getAliasTitle())
                    .startTime(page.getStartTime())
                    .endTime(page.getEndTime())
                    .goodsIdList(new ArrayList<>(goodsIdSet))
                    .build();
            goodsComponentPageList.add(goodsComponentPage);
        }
        return goodsComponentPageList;
    }

    public PageData<GoodsInfoDTO> selectPageForGoods(GoodsComponentQuery goodsComponentQuery) {
        List<GoodsInfoDTO> goodsInfoList = selectListForGoods(goodsComponentQuery.getComponentId(), true);
        if (goodsInfoList == null) {
            goodsInfoList = new ArrayList<>();
        }
        return PageData.of(goodsInfoList, 1, goodsComponentQuery.getLimit(), goodsInfoList.size());
    }

    public List<GoodsInfoDTO> selectListForGoods(Long componentId) {
        return selectListForGoods(componentId, false);
    }

    public List<GoodsInfoDTO> selectListForGoods(Long componentId, boolean price) {
        ContainerComponent containerComponent = containerComponentRepository.selectById(componentId);
        if (containerComponent == null) {
            return null;
        }

        if (containerComponent.getModelType() == null || containerComponent.getModelType() != 1) {
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
        if (price) {
            // 商品价格信息
            Map<Long, ComponentGoodsPrice> priceMap = null;
            List<ComponentGoodsPrice> prices = componentGoodsPriceService.selectListByComponentId(componentId, 1);
            if (CollectionUtils.isNotEmpty(prices)) {
                priceMap = prices.stream().collect(Collectors.toMap(ComponentGoodsPrice::getComponentGoodsId, e -> e, (a, b) -> a));
                for (GoodsInfoDTO goodsInfo : goodsInfoList) {
                    ComponentGoodsPrice componentGoodsPrice = priceMap.get(goodsInfo.getGoodsId());
                    if (componentGoodsPrice != null && StringUtils.isNotBlank(componentGoodsPrice.getPriceData())) {
                        goodsInfo.setNormalPrice(JSON.parseArray(componentGoodsPrice.getPriceData(), PriceDataDTO.class));
                    }
                }
            }
        }

        CmsPage cmsPage = null;
        CmsPageContainer pageContainer = cmsPageContainerService.getById(containerComponent.getContainerId());
        if (pageContainer != null) {
            CmsPage page = cmsPageRepository.selectCmsPageByIdIncludeDeleted(pageContainer.getPageId());
            if (page != null && PageStatusEnum.PAGE_STATUS_PUBLISH.getStatus().equals(page.getStatus())) {
                cmsPage = page;
            }
        }

        for (GoodsInfoDTO goodsInfo : goodsInfoList) {
            if (cmsPage != null) {
                goodsInfo.setPageSchemaId(cmsPage.getPageSchemaId());
                goodsInfo.setPageId(cmsPage.getId());
            }
        }

        return goodsInfoList;
    }

    public List<SubjectEnableGoodsInfoDTO> getSubjectGoodsList(LocalDateTime time) {
        // 获取标签下的所有页面定义
        CmsCorePageList condition = new CmsCorePageList();
        condition.setTagId(SystemConfig.getInstance().getSubjectTagId());
        List<CmsPageSchemaListDO> pageSchemaList = cmsPageSchemaRepository.queryByCondition(condition);
        if (CollectionUtils.isEmpty(pageSchemaList)) {
            return null;
        }

        List<SubjectEnableGoodsInfoDTO> goodsInfoDTO = Lists.newArrayList();
        for (CmsPageSchemaListDO pageSchema : pageSchemaList) {
            if (pageSchema.getId() == null) {
                continue;
            }

            // 获取页面实例
            List<CmsPage> pageList = cmsPageRepository.getCmsPageList(pageSchema.getId().intValue());
            if (CollectionUtils.isEmpty(pageList)) {
                continue;
            }

            // 保留已失效的页面实例
            pageList = pageList.stream().filter(e -> e.getGoodsEnable() != null && e.getGoodsEnable() == 1 && e.getEndTime() != null && e.getEndTime().compareTo(time) <= 0).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(pageList)) {
                continue;
            }

            for (CmsPage page : pageList) {
                List<String> containerIds = page.getSort();

                if(CollectionUtils.isEmpty(containerIds)){
                    continue;
                }

                List<CmsPageContainer> pageContainers = cmsPageContainerRepository.getByIds(containerIds.stream().map(Long::parseLong).collect(Collectors.toList()));
                if (CollectionUtils.isEmpty(pageContainers)) {
                    continue;
                }


                List<ContainerComponent> componentList = containerComponentService.selectList(pageContainers.stream().map(CmsPageContainer::getId).collect(Collectors.toList()));
                if (CollectionUtils.isEmpty(componentList)) {
                    continue;
                }

                List<Long> componentIdList = new ArrayList<>();
                Set<Long> goodsIdSet = new HashSet<>();
                for (ContainerComponent component : componentList) {
                    if (component.getModelType() == null || component.getModelType() != 1) {
                        continue;
                    }

                    List<CmsGoods> goodsList = JSON.parseArray(component.getBizJson(), CmsGoods.class);
                    if (CollectionUtils.isEmpty(goodsList)) {
                        continue;
                    }

                    componentIdList.add(component.getId());
                    for (CmsGoods goodsInfo : goodsList) {
                        goodsIdSet.add(goodsInfo.getGoodsId());
                    }
                }

                SubjectEnableGoodsInfoDTO subjectEnableGoodsInfoDTO = new SubjectEnableGoodsInfoDTO();
                subjectEnableGoodsInfoDTO.setSubjectId(page.getId().longValue());
                subjectEnableGoodsInfoDTO.setComponentId(componentIdList);
                subjectEnableGoodsInfoDTO.setGoodsIds(new ArrayList<>(goodsIdSet));
                goodsInfoDTO.add(subjectEnableGoodsInfoDTO);
            }
        }
        return goodsInfoDTO;
    }

    private Map<Long, List<ContainerComponent>> getContainerComponentMap(Set<Long> containerIdList) {
        ContainerComponentCondition condition = new ContainerComponentCondition();
        condition.setModelType(1);
        condition.setContainerIdList(new ArrayList<>(containerIdList));
        List<ContainerComponent> containerComponentList = containerComponentService.selectByCondition(condition);
        if (CollectionUtils.isEmpty(containerComponentList)) {
            return new HashMap<>();
        }
        return containerComponentList.stream().collect(Collectors.groupingBy(ContainerComponent::getContainerId));
    }

    public void updateSubjectEnable(Long subjectId, Integer enable) {
        if (subjectId == null) {
            return;
        }
        cmsPageRepository.updateGoodsEnable(subjectId, enable);
    }

    public Long getGoodsTagBySubjectId(Long id) {
        String tagKey = format(RedisKeyConstant.SUBJECT_GOODS_TAG_ID_KEY, id);
        return cacheUtil.get(tagKey, Long.class);
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
