package com.idanchuang.resource.server.domain.repository;

import com.github.pagehelper.PageInfo;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfigQueryConditions;
import com.idanchuang.resource.server.domain.model.resource.ResourcePutInfo;

import java.util.List;

/**
 * Created by develop at 2021/2/5.
 */
public interface ResourceConfigRepository{

    /**
     * 根据条件查询资源位id信息
     * @param resourceId
     * @param pageCode
     * @param business
     * @return
     */
    ResourcePutInfo getResourceConfigByReq(Long resourceId, String pageCode, Integer business);

    /**
     * 查询页面资源位信息
     *
     * @param pageCode
     * @param business
     * @return
     */
    List<ResourceConfig> getResourceListByPageId(String pageCode, Integer business);

    /**
     * 根据资源位名获取一个资源位id
     * @param resourceName
     * @return
     */
    Long getResourceIdByNameLimitOne(String resourceName);

    int saveResourceConfig(ResourceConfig resourceConfig);

    int updateResourceConfig(ResourceConfig resourceConfig);

    ResourceConfig getResourceConfigById(Long resourceId);

    PageInfo<ResourceConfig> searchResourceConfig(ResourceConfigQueryConditions queryConditions);

    Boolean checkResourceNumbUniq(String pageCode, Integer resourceNumb, Long resourceId);

    Boolean checkResourceNameUniq(String pageCode, String resourceName, Long resourceId);

}
