package com.idanchuang.resource.server.infrastructure.persistence.mapper;

import com.idanchuang.resource.server.infrastructure.persistence.model.ResourcePageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuai
 */
@Mapper
public interface ResourcePageMapper {

    List<ResourcePageDO> getResourcePageByBusinessId(Integer businessId);

    ResourcePageDO getResourcePageByPageCode(@Param("pageCode") String pageCode);
}
