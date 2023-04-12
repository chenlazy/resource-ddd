package com.idanchuang.resource.server.infrastructure.persistence.mapper;

import com.idanchuang.resource.server.domain.model.resource.ResourceConfigQueryConditions;
import com.idanchuang.resource.server.infrastructure.persistence.model.ResourceConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by develop at 2021/2/4.
 * @author wuai
 */
@Mapper
public interface ResourceConfigMapper {

    /**
     * 根据条件查询资源位信息
     * @param resourceId
     * @param pageCode
     * @param business
     * @return
     */
    ResourceConfigDO getResourceConfigByReq(@Param("resourceId") Long resourceId, @Param("pageCode")String pageCode, @Param("business")Integer business);

    /**
     * 根据页面和平台查询资源位列表
     * @param pageCode
     * @param business
     * @return
     */
    List<ResourceConfigDO> getResourceListByPageId(@Param("pageCode") String pageCode, @Param("business") Integer business);

    /**
     * 根据资源位名获取一个资源位id
     * @param resourceName
     * @return
     */
    Long getResourceIdByNameLimitOne(@Param("resourceName") String resourceName);

    int insert(ResourceConfigDO configDO);

    int updateById(ResourceConfigDO configDO);

    ResourceConfigDO selectById(@Param("resourceId") Long resourceId);

    List<ResourceConfigDO> searchResourceConfig(ResourceConfigQueryConditions queryConditions);

    List<ResourceConfigDO> checkResourceNumbUniq(@Param("pageCode")String pageCode,@Param("resourceNumb")Integer resourceNumb,@Param("resourceId") Long resourceId);

    List<ResourceConfigDO> checkResourceNameUniq(@Param("pageCode")String pageCode,@Param("resourceName")String resourceName,@Param("resourceId") Long resourceId);
}

