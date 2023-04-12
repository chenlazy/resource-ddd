package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper;

import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.ContainerDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-28 15:54
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface ContainerMapper {

    int insertContainer(ContainerDO containerDO);

    ContainerDO getContainerById(Long containerId);

    List<ContainerDO> getContainerListByIds(List<Long> containerIds);

    List<ContainerDO> queryContainerList(Long masterplateId);

    List<ContainerDO> querySnapContainerList(Long masterplateId);

    List<ContainerDO> queryBatchContainerList(@Param("masterplateIds") List<Long> masterplateIds);

    int updateContainer(ContainerDO containerDO);

}
