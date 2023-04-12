package com.idanchuang.cms.server.domainNew.model.cms.container;

import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 14:20
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface ContainerRepository {

    /**
     * 保存容器
     * @param container
     * @return
     */
    Boolean storeContainer(Container container);

    /**
     * 更新容器
     * @param container
     * @return
     */
    Boolean updateContainer(Container container);

    /**
     * 根据id查询容器信息
     * @param containerId
     * @return
     */
    Container getContainerById(ContainerId containerId);

    /**
     * 批量获取容器信息
     * @param containerIds
     * @return
     */
    List<Container> getContainersByIds(List<ContainerId> containerIds);

    /**
     * 查询模版下的容器列表
     * @param masterplateId
     * @return
     */
    List<Container> queryContainerList(MasterplateId masterplateId);

    /**
     * 获取快照容器列表
     * @param masterplateId
     * @return
     */
    List<Container> querySnapContainerList(MasterplateId masterplateId);

    /**
     * 根据模版ids查询容器列表
     * @param masterplates
     * @return
     */
    List<Container> queryContainerByMasterplateIds(List<MasterplateId> masterplates);

}
