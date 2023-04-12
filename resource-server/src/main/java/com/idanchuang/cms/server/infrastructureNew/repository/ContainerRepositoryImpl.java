package com.idanchuang.cms.server.infrastructureNew.repository;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domain.shard.IdObject;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.ContainerDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper.ContainerMapper;
import com.idanchuang.cms.server.infrastructureNew.repository.convertor.RepositoryContainerConvert;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-28 15:47
 * @Desc: 容器领域实现类
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class ContainerRepositoryImpl implements ContainerRepository {

    @Resource
    private ContainerMapper containerMapper;

    @Override
    public Boolean storeContainer(Container container) {
        ContainerDO containerDO = RepositoryContainerConvert.entityToDO(container);
        int insert = containerMapper.insertContainer(containerDO);
        container.setContainerId(new ContainerId(containerDO.getId()));
        return insert > 0;
    }

    @Override
    public Boolean updateContainer(Container container) {
        ContainerDO containerDO = RepositoryContainerConvert.entityToDO(container);
        int update = containerMapper.updateContainer(containerDO);
        return update > 0;
    }

    @Override
    public Container getContainerById(ContainerId containerId) {

        if (null == containerId) {
            return null;
        }

        ContainerDO containerDO = containerMapper.getContainerById(containerId.getValue());
        return RepositoryContainerConvert.doToDomain(containerDO);
    }

    @Override
    public List<Container> getContainersByIds(List<ContainerId> containerIds) {

        if (CollectionUtils.isEmpty(containerIds)) {
            return Lists.newArrayList();
        }

        List<Long> containerIdList = containerIds.stream().map(IdObject::getValue).collect(Collectors.toList());
        List<ContainerDO> containerDOS = containerMapper.getContainerListByIds(containerIdList);

        if (CollectionUtils.isEmpty(containerDOS)) {
            return Lists.newArrayList();
        }

        return containerDOS.stream().map(RepositoryContainerConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Container> queryContainerList(MasterplateId masterplateId) {

        if (null == masterplateId) {
            return Lists.newArrayList();
        }
        List<ContainerDO> containerDOS = containerMapper.queryContainerList(masterplateId.getValue());
        if (CollectionUtils.isEmpty(containerDOS)) {
            return Lists.newArrayList();
        }
        return containerDOS.stream().map(RepositoryContainerConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Container> querySnapContainerList(MasterplateId masterplateId) {
        if (null == masterplateId) {
            return Lists.newArrayList();
        }
        List<ContainerDO> containerDOS = containerMapper.querySnapContainerList(masterplateId.getValue());
        if (CollectionUtils.isEmpty(containerDOS)) {
            return Lists.newArrayList();
        }
        return containerDOS.stream().map(RepositoryContainerConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Container> queryContainerByMasterplateIds(List<MasterplateId> masterplates) {
        if (CollectionUtils.isEmpty(masterplates)) {
            return Lists.newArrayList();
        }
        List<Long> masterplateIds = masterplates.stream().map(IdObject::getValue).collect(Collectors.toList());
        List<ContainerDO> containerDOS = containerMapper.queryBatchContainerList(masterplateIds);

        if (CollectionUtils.isEmpty(containerDOS)) {
            return Lists.newArrayList();
        }
        return containerDOS.stream().map(RepositoryContainerConvert::doToDomain).collect(Collectors.toList());
    }
}
