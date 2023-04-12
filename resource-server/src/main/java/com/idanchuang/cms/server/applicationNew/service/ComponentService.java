package com.idanchuang.cms.server.applicationNew.service;

import com.google.common.collect.Lists;
import com.idanchuang.cms.api.common.constants.ModelNameConstant;
import com.idanchuang.cms.api.common.enums.ModelTypeEnum;
import com.idanchuang.cms.server.application.constant.PageStyleConstant;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.model.cms.component.Component;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentBuildResult;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentFactory;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentRepository;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPrice;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityKey;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 15:16
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class ComponentService {

    @Resource
    private ComponentRepository componentRepository;

    @Resource
    private MasterplateRepository masterplateRepository;

    @Resource
    private CatalogueRepository catalogueRepository;

    /**
     * 新增或删除组件
     *
     * @param components 组件信息
     * @param containerId 容器id
     * @return 写入状态
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpsertComponent(List<Component> components, ContainerId containerId) {

        if (CollectionUtils.isEmpty(components) || null == containerId) {
            return false;
        }

        List<Component> componentList = componentRepository.getComponentListByContainerId(containerId);

        //如果容器下面没有组件，就批量插入
        if (CollectionUtils.isEmpty(componentList)) {
            return storeComponents(containerId, components);
        }
        List<Component> storeComponents = Lists.newArrayList();
        List<Component> updateComponents = Lists.newArrayList();
        List<Component> removeComponents = Lists.newArrayList();

        //获取更新和新增的数组
        for (Component component : components) {
            AtomicBoolean store = new AtomicBoolean(true);
            componentList.forEach(p -> {
                if (component.getId().sameValueAs(p.getId())) {
                    updateComponents.add(component);
                    store.set(false);
                }
            });
            if (store.get()) {
                storeComponents.add(component);
            }
        }

        //获取删除的数组
        for (Component component : componentList) {

            AtomicBoolean remove = new AtomicBoolean(true);
            components.forEach(p -> {
                if (p.getId().sameValueAs(component.getId())) {
                    remove.set(false);
                }
            });
            if (remove.get()) {
                removeComponents.add(component);
            }
        }

        if (!CollectionUtils.isEmpty(storeComponents)) {
            storeComponents(containerId, storeComponents);
        }

        if (!CollectionUtils.isEmpty(updateComponents)) {
            componentRepository.batchUpdateComponent(updateComponents);
        }

        if (!CollectionUtils.isEmpty(removeComponents)) {
            componentRepository.batchRemoveComponent(removeComponents);
        }

        return true;
    }

    /**
     * 批量保存组件信息
     * @param containerId 容器id
     * @param components 组件信息
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean storeComponents(ContainerId containerId, List<Component> components) {

        if (CollectionUtils.isEmpty(components)) {
            return false;
        }

        List<ComponentId> componentIdList = components.stream().map(Component::getId).distinct().collect(Collectors.toList());

        //校验是否存在重复的key
        if (components.size() != componentIdList.size()) {
            List<ComponentId> componentIds = components.stream().map(Component::getId).collect(Collectors.toList());
            log.info("storeComponents key duplicate, containerId:{}, componentIds:{}", containerId, componentIds);
            //手动设置回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.EXIST_DUPLICATE_KEY.getCode(),
                    ErrorEnum.EXIST_DUPLICATE_KEY.getMsg()));
        }

        List<Component> oldComponentList = componentRepository.getComponentIdByIds(componentIdList);
        if (!CollectionUtils.isEmpty(oldComponentList)) {
            //手动设置回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            List<ComponentId> componentIds = oldComponentList.stream().map(Component::getId).collect(Collectors.toList());
            log.info("storeComponents key exist, containerId:{}, componentIds:{}", containerId, componentIds);
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.COMPONENT_INFO_EXIST_ERROR.getCode(),
                    ErrorEnum.COMPONENT_INFO_EXIST_ERROR.getMsg()));
        }
        int insert = componentRepository.batchStoreComponent(components);
        return insert > 0;

    }

    /**
     * 组装完整的组件信息
     *
     * @param components 组件信息
     * @param containerId 容器id
     * @param pageCode 页面code
     * @param masterplateId 模版id
     * @param goodsIds 商品信息集合
     * @param goodsPrices 商品日常价格集合
     * @return 构建完成的组件信息
     */
    public List<Component> fillComponents(List<Object> components, ContainerId containerId, PageCode pageCode,
                                          MasterplateId masterplateId, OperatorId operatorId, List<GoodsId> goodsIds,
                                          List<GoodsPrice> goodsPrices, Map<EquityKey, List<GoodsId>> equityIdMap) {

        List<Component> containerComponents = new ArrayList<>();

        for (Object component : components) {

            //构建组件信息
            ComponentBuildResult componentBuildResult = buildComponentInfo(component, containerId, pageCode, masterplateId, operatorId, goodsIds, goodsPrices, equityIdMap);

            containerComponents.add(componentBuildResult.getComponent());

            //解析tab里面的组件
            if (ModelNameConstant.COMP_TAB.equals(componentBuildResult.getType()) || ModelNameConstant.NEW_COMP_TAB.equals(componentBuildResult.getType())) {

                String detailJson = JsonUtil.toJsonString(componentBuildResult.getDetails());
                List<Object> detailList = JsonUtil.toList(detailJson, Object.class);

                if (!CollectionUtils.isEmpty(detailList)) {

                    detailList.forEach(p -> {
                        Map<Object, Object> obmMap = JsonUtil.toMap(JsonUtil.toJsonString(p), Map.class);
                        List<Object> tabComponents = JsonUtil.toList(JsonUtil.toJsonString(obmMap.get(PageStyleConstant.LIST)), Object.class);

                        //添加tab里面的组件
                        tabComponents.forEach(m -> {
                            ComponentBuildResult buildResult = buildComponentInfo(m, containerId, pageCode, masterplateId, operatorId, goodsIds, goodsPrices, equityIdMap);
                            containerComponents.add(buildResult.getComponent());
                        });
                    });
                }
            }
        }
        return containerComponents;
    }

    private ComponentBuildResult buildComponentInfo(Object component, ContainerId containerId, PageCode pageCode,
                                                    MasterplateId masterplateId, OperatorId operatorId,
                                                    List<GoodsId> goodsIds, List<GoodsPrice> goodsPrices,
                                                    Map<EquityKey, List<GoodsId>> equityIdMap) {

        //创建组件信息
        Component fillComponent = ComponentFactory.createComponent(component, containerId, pageCode, masterplateId,
                operatorId, goodsIds, goodsPrices, equityIdMap);

        String compStr = JsonUtil.toJsonString(component);
        Map<String, Object> compMap = JsonUtil.toMap(compStr, Map.class);
        Object details = compMap.get(PageStyleConstant.DETAIL);
        String type = (String) compMap.get(PageStyleConstant.COMP_TYPE);

        return new ComponentBuildResult(fillComponent, details, type);
    }


    /**
     * 根据抽奖活动ID，获取关联的专题页别名
     * 若关联多个，返回最新的一个
     *
     * @param activityId 抽奖活动ID
     * @return 专题别名
     */
    public String getLuckyComponentPageId(Long activityId, List<ContainerId> containerIds) {
        List<Component> containerComponents = componentRepository.selectByActivityId(activityId, containerIds, ModelTypeEnum.MODEL_TYPE_LUCKY);
        if (!CollectionUtils.isEmpty(containerComponents)) {
            List<Component> collect = containerComponents.stream()
                    .sorted((a, b) -> a.getId().getValue() > b.getId().getValue() ? 1 : 0)
                    .collect(Collectors.toList());
            if (collect.get(0).getMasterplateId() != null) {
                Masterplate masterplate = masterplateRepository.getMasterplateById(collect.get(0).getMasterplateId());
                Catalogue catalogue = catalogueRepository.getCatalogueById(masterplate.getCatalogueId());
                return catalogue.getAliasTitle();
            }
        }
        return null;
    }


}
