package com.idanchuang.resource.server.domain.repository;

import com.github.pagehelper.PageInfo;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnitQueryConditions;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;

import java.time.LocalDateTime;

/**
 * Created by develop at 2021/2/5.
 */
public interface ResourceUnitRepository {

    /**
     * 根据资源位和用户等级获取可用的投放内容
     *
     * @param resourceId
     * @param role
     * @param platform
     * @param time
     * @return
     */
    ResourceUnit queryResourceUnitByResourceAndRole(Long resourceId, Integer role, String platform, LocalDateTime time);

    /**
     * 根据投放内容名获取一个投放id
     * @param unitName
     * @return
     */
    Long getUnitIdByUnitNameLimitOne(String unitName);

    /**
     * 根据内容投放id查询内容
     * @param unitId
     * @return
     */
    ResourceUnit getUnitInfoById(Long unitId);

    int saveResourceUnit(ResourceUnit resourceUnit);

    int updateResourceUnit(ResourceUnit resourceUnit);

    PageInfo<ResourceUnit> searchResourceUnit(ResourceUnitQueryConditions queryConditions);
}
