package com.idanchuang.resource.server.application.service;

import com.github.pagehelper.PageInfo;
import com.idanchuang.resource.server.domain.cache.ResourceUnitCache;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.domain.model.resource.ResourcePage;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnitQueryConditions;
import com.idanchuang.resource.server.domain.repository.ResourceConfigRepository;
import com.idanchuang.resource.server.domain.repository.ResourcePageRepository;
import com.idanchuang.resource.server.domain.repository.ResourceUnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author wengbinbin
 * @date 2021/3/16
 */

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ResourceUnitService {

    @Resource
    private ResourceUnitRepository resourceUnitRepository;

    @Resource
    private ResourcePageRepository resourcePageRepository;

    @Resource
    private ResourceConfigRepository resourceConfigRepository;

    @Resource
    private ResourceUnitCache resourceUnitCache;

    public void createResourceUnit(ResourceUnit resourceUnit) {
        resourceUnitRepository.saveResourceUnit(resourceUnit);
        resourceUnitCache.evictUnitCache(resourceUnit.getResourceId());
    }

    public ResourceUnit getResourceUnitById(Long id) {
        ResourceUnit resourceUnit = resourceUnitRepository.getUnitInfoById(id);
        return assembleUnit(resourceUnit);
    }

    private ResourceUnit assembleUnit(ResourceUnit resourceUnit) {
        ResourceConfig resourceConfig = resourceConfigRepository.getResourceConfigById(resourceUnit.getResourceId());
        if (resourceConfig != null) {
            resourceUnit.setResourceName(resourceConfig.getResourceName());
            resourceUnit.setComponentConfined(resourceConfig.getComponentConfined());
            resourceUnit.setComponentType(resourceConfig.getComponentType());
            resourceUnit.setPageCode(resourceConfig.getPageCode());
            resourceUnit.setResourceType(resourceConfig.getResourceType());
            resourceUnit.setResourceScheme(resourceConfig.getResourceScheme());
        }
        ResourcePage resourcePage = resourcePageRepository.getResourcePageByPageCode(resourceConfig.getPageCode());
        if (resourcePage != null) {
            resourceUnit.setPageName(resourcePage.getPageName());
        }
        return resourceUnit;
    }

    public PageInfo<ResourceUnit> searchResourceUnit(ResourceUnitQueryConditions queryConditions) {
        PageInfo<ResourceUnit> pageInfo = resourceUnitRepository.searchResourceUnit(queryConditions);
        LocalDateTime currentDateTime = LocalDateTime.now();
        pageInfo.getList().forEach(resourceUnit -> {
            try {
                assembleUnit(resourceUnit);
                resourceUnit.currentWorkStatus(currentDateTime);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
        return pageInfo;
    }

    public void updateResourceUnit(ResourceUnit resourceUnit) {
        ResourceUnit oldUnit = getResourceUnitById (resourceUnit.getId());
        resourceUnitRepository.updateResourceUnit(resourceUnit);
        resourceUnitCache.evictUnitCache(oldUnit.getResourceId());
    }

    public Long getUnitIdByUnitNameLimitOne(String unitName){
        Long unitIdByUnitNameLimitOne = resourceUnitRepository.getUnitIdByUnitNameLimitOne(unitName);
        return unitIdByUnitNameLimitOne;
    }

}