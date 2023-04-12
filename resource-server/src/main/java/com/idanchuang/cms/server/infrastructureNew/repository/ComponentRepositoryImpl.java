package com.idanchuang.cms.server.infrastructureNew.repository;

import com.google.common.collect.Lists;
import com.idanchuang.cms.api.common.enums.ModelTypeEnum;
import com.idanchuang.cms.server.domain.shard.IdObject;
import com.idanchuang.cms.server.domainNew.model.cms.component.Component;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentQueryForm;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentRepository;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.ComponentDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper.ComponentMapper;
import com.idanchuang.cms.server.infrastructureNew.repository.convertor.RepositoryComponentConvert;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-29 13:39
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class ComponentRepositoryImpl implements ComponentRepository {

    @Resource
    private ComponentMapper componentMapper;

    @Override
    public int batchStoreComponent(List<Component> components) {
        if (CollectionUtils.isEmpty(components)) {
            return 0;
        }
        List<ComponentDO> componentDOS = components.stream().map(RepositoryComponentConvert::entityToDO).collect(Collectors.toList());
        return componentMapper.batchInsertComponents(componentDOS);
    }

    @Override
    public int batchUpdateComponent(List<Component> components) {
        List<ComponentDO> componentDOS = components.stream().map(RepositoryComponentConvert::entityToDO).collect(Collectors.toList());
        return componentMapper.batchUpdateComponents(componentDOS);
    }

    @Override
    public int batchRemoveComponent(List<Component> components) {
        if (CollectionUtils.isEmpty(components)) {
            return 0;
        }
        List<Long> componentIds = components.stream().map(p -> p.getId().getValue()).collect(Collectors.toList());
        return componentMapper.batchRemoveComponents(componentIds);
    }

    @Override
    public Component getComponentById(ComponentId componentId) {
        ComponentDO componentDO = componentMapper.getComponentById(componentId.getValue());
        return RepositoryComponentConvert.doToDomain(componentDO);
    }

    @Override
    public List<Component> getComponentIdByIds(List<ComponentId> componentIds) {

        if (CollectionUtils.isEmpty(componentIds)) {
            return Lists.newArrayList();
        }

        List<ComponentDO> componentDOS = componentMapper.getComponentByIds(componentIds.stream().map(ComponentId::getValue).collect(Collectors.toList()));

        if (CollectionUtils.isEmpty(componentDOS)) {
            return Lists.newArrayList();
        }
        return componentDOS.stream().map(RepositoryComponentConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Component> getComponentListByContainerId(ContainerId containerId) {
        List<ComponentDO> componentList = componentMapper.getComponentList(containerId.getValue());
        if (CollectionUtils.isEmpty(componentList)) {
            return Lists.newArrayList();
        }
        return componentList.stream().map(RepositoryComponentConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Component> queryComponentList(ComponentQueryForm pageQueryForm) {

        if (CollectionUtils.isEmpty(pageQueryForm.getContainerIds()) || pageQueryForm.getBusinessType() == null) {
            return Lists.newArrayList();
        }
        List<ContainerId> containerIds = pageQueryForm.getContainerIds();
        List<Long> containerIdList = containerIds.stream().map(IdObject::getValue).collect(Collectors.toList());
        List<ComponentDO> componentDOS = componentMapper.queryComponentList(containerIdList, pageQueryForm.getBusinessType().getModelType());
        return componentDOS.stream().map(RepositoryComponentConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Component> queryComponentList(List<ContainerId> containerIds) {
        if (CollectionUtils.isEmpty(containerIds)) {
            return Lists.newArrayList();
        }
        List<Long> containerIdList = containerIds.stream().map(IdObject::getValue).collect(Collectors.toList());
        List<ComponentDO> componentDOS = componentMapper.queryComponentList(containerIdList, null);
        return componentDOS.stream().map(RepositoryComponentConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Component> selectByActivityId(Long activityId, List<ContainerId> containerIds, ModelTypeEnum type) {

        List<Long> containerIdList = containerIds.stream().map(ContainerId::getValue).collect(Collectors.toList());

        List<ComponentDO> componentDOS = componentMapper.selectByActivityId(activityId, containerIdList, type.getModelType());
        return componentDOS.stream()
                .map(RepositoryComponentConvert::doToDomain)
                .collect(Collectors.toList());
    }
}
