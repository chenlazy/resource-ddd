package com.idanchuang.cms.server.infrastructure.repository;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponent;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponentCondition;
import com.idanchuang.cms.server.domain.repository.ContainerComponentRepository;
import com.idanchuang.cms.server.infrastructure.persistence.mapper.ContainerComponentMapper;
import com.idanchuang.cms.server.infrastructure.persistence.model.ContainerComponentDO;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class ContainerComponentRepositoryImpl implements ContainerComponentRepository {

    @Resource
    private ContainerComponentMapper containerComponentMapper;

    @Override
    public boolean insert(ContainerComponent entity) {
        return entity != null && containerComponentMapper.insert(convertOf(entity)) > 0;
    }

    @Override
    public List<Long> insertBatch(List<ContainerComponent> containerComponentList) {
        if (CollectionUtils.isEmpty(containerComponentList)) {
            return Lists.newArrayList();
        }

        List<ContainerComponentDO> entityList = containerComponentList.stream().map(this::convertOf).collect(Collectors.toList());
        int result = containerComponentMapper.insertBatch(entityList);
        if (result <= 0) {
            return Lists.newArrayList();
        }

        return entityList.stream().map(ContainerComponentDO::getId).collect(Collectors.toList());
    }

    @Override
    public boolean updateById(ContainerComponent entity) {
        return containerComponentMapper.updateById(convertOf(entity)) > 0;
    }

    @Override
    public ContainerComponent selectById(Serializable id) {
        if (null == id) {
            return null;
        }
        ContainerComponentDO entity = containerComponentMapper.selectById(id);
        return entity == null ? null : convertOf(entity);
    }

    @Override
    public List<ContainerComponent> selectByCondition(ContainerComponentCondition condition) {
        List<ContainerComponentDO> entityList = containerComponentMapper.selectByCondition(condition);
        return CollectionUtils.isEmpty(entityList) ? null : entityList.stream().map(this::convertOf).collect(Collectors.toList());
    }

    /**
     * 根据组件ID更新容器ID
     *
     * @param idList      组件ID
     * @param containerId 容器ID
     * @param operatorId  操作人ID
     * @return 是否成功
     */
    @Override
    public boolean updateContainerId(List<Long> idList, Long containerId, Integer operatorId) {
        if (CollectionUtils.isEmpty(idList)) {
            return true;
        }
        return containerComponentMapper.updateContainerId(idList, containerId, operatorId, LocalDateTime.now()) > 0;
    }

    @Override
    public List<ContainerComponent> queryComponentInfoByContainerIds(List<Long> containerIds) {
        if (CollectionUtils.isEmpty(containerIds)) {
            return Lists.newArrayList();
        }
        List<ContainerComponentDO> containerComponentDOS = containerComponentMapper.queryComponentInfoByContainerIds(containerIds);
        if (CollectionUtils.isEmpty(containerComponentDOS)) {
            return Lists.newArrayList();
        }
        return containerComponentDOS.stream().map(this::convertOf).collect(Collectors.toList());
    }

    @Override
    public List<ContainerComponent> selectByActivityId(Long activityId) {
        if (activityId != null) {
            List<ContainerComponentDO> containerComponentDOS = containerComponentMapper.selectByActivityId(String.valueOf(activityId));
            return containerComponentDOS.stream().map(this::convertOf).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private ContainerComponent convertOf(ContainerComponentDO source) {

        return new ContainerComponent(source.getId(), source.getContainerId(), source.getComponentType(), source.getModelType(), source.getBizJson(), source.getModelJson(),
                source.getOperatorId(), null, null, source.getCreateTime(), source.getUpdateTime());
    }

    private ContainerComponentDO convertOf(ContainerComponent source) {
        return ContainerComponentDO.builder()
                .id(source.getId())
                .containerId(source.getContainerId())
                .componentType(source.getComponentType())
                .modelType(source.getModelType())
                .bizJson(source.getBizJson())
                .modelJson(source.getModelJson())
                .operatorId(source.getOperatorId())
                .createTime(source.getCreateTime())
                .updateTime(source.getUpdateTime())
                .build();
    }

}
