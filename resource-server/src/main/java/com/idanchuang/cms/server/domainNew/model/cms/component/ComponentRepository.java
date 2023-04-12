package com.idanchuang.cms.server.domainNew.model.cms.component;

import com.idanchuang.cms.api.common.enums.ModelTypeEnum;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 14:39
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface ComponentRepository {

    /**
     * 批量保存组件
     *
     * @param components
     * @return
     */
    int batchStoreComponent(List<Component> components);

    /**
     * 批量更新组件
     *
     * @param components
     * @return
     */
    int batchUpdateComponent(List<Component> components);

    /**
     * 批量删除组件
     *
     * @param components
     * @return
     */
    int batchRemoveComponent(List<Component> components);


    /**
     * 获取组件信息
     *
     * @param componentId
     * @return
     */
    Component getComponentById(ComponentId componentId);

    /**
     * 查询批量组件信息
     * @param componentIds
     * @return
     */
    List<Component> getComponentIdByIds(List<ComponentId> componentIds);

    /**
     * 根据容器id查询组件列表
     *
     * @return
     */
    List<Component> getComponentListByContainerId(ContainerId containerId);

    /**
     * 查询组件列表
     *
     * @param pageQueryForm
     * @return
     */
    List<Component> queryComponentList(ComponentQueryForm pageQueryForm);

    /**
     * 查询组件列表
     *
     * @param containerIds
     * @return
     */
    List<Component> queryComponentList(List<ContainerId> containerIds);

    /**
     * 根据活动id查询组件
     *
     * @param activityId 活动id
     */
    List<Component> selectByActivityId(Long activityId, List<ContainerId> containerIds, ModelTypeEnum type);
}
