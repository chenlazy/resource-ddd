package com.idanchuang.resource.server.infrastructure.persistence.mapper;

import com.idanchuang.resource.server.infrastructure.persistence.model.ResourceBusinessDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wuai
 */
@Mapper
public interface ResourceBusinessMapper {

    List<ResourceBusinessDO> getResourceBusiness();
}
