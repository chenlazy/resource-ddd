package com.idanchuang.resource.server.infrastructure.persistence.mapper;

import com.idanchuang.resource.server.domain.model.resource.ResourceUnitQueryConditions;
import com.idanchuang.resource.server.infrastructure.persistence.model.ResourceUnitDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by develop at 2021/2/4.
 *
 * @author wuai
 */
@Mapper
public interface ResourceUnitMapper{

    /**
     * 获取资源位投放内容集合
     *
     * @param resourceId
     * @param platform
     * @param nowTime
     * @return
     */
    List<ResourceUnitDO> queryResourceUnitDOListByResourceId(@Param("resourceId") Long resourceId, @Param("platform") String platform, @Param("nowTime") LocalDateTime nowTime);

    /**
     * 根据投放内容名获取一个投放id
     * @param unitName
     * @return
     */
    Long getUnitIdByUnitNameLimitOne(@Param("unitName") String unitName);

    /**
     * 根据id查询内容投放信息
     * @param unitId
     * @return
     */
    ResourceUnitDO getUnitInfoById(@Param("unitId") Long unitId);

    int insert(ResourceUnitDO resourceUnitDO);

    int updateById(ResourceUnitDO resourceUnitDO);

    List<ResourceUnitDO> searchResourceUnit(ResourceUnitQueryConditions queryConditions);
}

