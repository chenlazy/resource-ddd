package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper;

import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.ComponentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-28 15:59
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface ComponentMapper {

    ComponentDO getComponentById(@Param("componentId") Long componentId);

    List<ComponentDO> getComponentByIds(@Param("componentIds") List<Long> componentIds);

    List<ComponentDO> getComponentList(@Param("containerId") Long containerId);

    List<ComponentDO> queryComponentList(@Param("list") List<Long> containerIdList, @Param("modelType") Integer modelType);

    int batchInsertComponents(@Param("list") List<ComponentDO> componentDOS);

    int batchUpdateComponents(@Param("list") List<ComponentDO> componentDOS);

    int batchRemoveComponents(@Param("componentIds") List<Long> componentIds);

    List<ComponentDO> selectByActivityId(@Param("activityId") Long activityId, @Param("containerIds") List<Long> containerIds,
                                         @Param("modelType") Integer modelType);
}
