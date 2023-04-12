package com.idanchuang.cms.server.domain.repository;

import com.idanchuang.cms.server.domain.model.cms.ContainerComponent;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponentCondition;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:28
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface ContainerComponentRepository {

    boolean insert(ContainerComponent entity);

    List<Long> insertBatch(List<ContainerComponent> entityList);

    boolean updateById(ContainerComponent entity);

    ContainerComponent selectById(Serializable id);

    List<ContainerComponent> selectByCondition(ContainerComponentCondition condition);

    /**
     * 根据组件ID更新容器ID
     *
     * @param idList      组件ID
     * @param containerId 容器ID
     * @param operatorId  操作人ID
     * @return 是否成功
     */
    boolean updateContainerId(List<Long> idList, Long containerId, Integer operatorId);

    /**
     * 根据容器ids查询容器关联组件信息
     *
     * @param containerIds
     * @return
     */
    List<ContainerComponent> queryComponentInfoByContainerIds(List<Long> containerIds);

    /**
     * 根据活动id查询组件
     *
     * @param activityId 活动id
     */
    List<ContainerComponent> selectByActivityId(Long activityId);

}
