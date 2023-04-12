package com.idanchuang.cms.server.infrastructure.persistence.mapper;

import com.idanchuang.cms.server.domain.model.cms.ContainerComponentCondition;
import com.idanchuang.cms.server.infrastructure.persistence.model.ContainerComponentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:39
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface ContainerComponentMapper {

    int insert(ContainerComponentDO entity);

    int insertBatch(List<ContainerComponentDO> entityList);

    int updateById(ContainerComponentDO entity);

    ContainerComponentDO selectById(@Param("id") Serializable id);

    List<ContainerComponentDO> selectByCondition(ContainerComponentCondition condition);

    int updateContainerId(@Param("idList") List<Long> idList, @Param("containerId") Long containerId, @Param(
            "operatorId") Integer operatorId, @Param("updateTime") LocalDateTime updateTime);

    /**
     * 根据容器ids查询容器关联组件信息
     *
     * @param containerIds
     * @return
     */
    List<ContainerComponentDO> queryComponentInfoByContainerIds(@Param("containerIds") List<Long> containerIds);


    void updateComponentSnapRoot(@Param("ids") List<Long> ids, @Param("snapRoot") String snapRoot);

    List<ContainerComponentDO> selectByActivityId(String activityId);
}
