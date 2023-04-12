package com.idanchuang.resource.server.application.service;

import com.github.pagehelper.PageInfo;
import com.idanchuang.resource.server.domain.cache.ResourceConfigCache;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfigQueryConditions;
import com.idanchuang.resource.server.domain.repository.ResourceConfigRepository;
import com.idanchuang.resource.server.domain.repository.ResourcePageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author wengbinbin
 * @date 2021/3/16
 */

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ResourceConfigService {
    @Resource
    private ResourceConfigRepository resourceConfigRepository;

    @Resource
    private ResourcePageRepository resourcePageRepository;

    @Resource
    private ResourceConfigCache resourceConfigCache;

    public void createResourceConfig(ResourceConfig resourceConfig) {
        resourceConfigRepository.saveResourceConfig(resourceConfig);
    }

    public ResourceConfig getResourceConfigById(Long id) {
        ResourceConfig resourceConfig = resourceConfigRepository.getResourceConfigById(id);
        resourceConfig.setPageName(resourcePageRepository.getResourcePageByPageCode(resourceConfig.getPageCode()).getPageName());
        return resourceConfig;
    }

    public PageInfo<ResourceConfig> searchResourceConfig(ResourceConfigQueryConditions queryConditions) {
        PageInfo<ResourceConfig> pageInfo = resourceConfigRepository.searchResourceConfig(queryConditions);
        pageInfo.getList().forEach(resourceConfig -> {
            String pageName = resourcePageRepository.getResourcePageByPageCode(resourceConfig.getPageCode()).getPageName();
            resourceConfig.setPageName(pageName);
        });
        return pageInfo;
    }

    public void updateResourceConfig(ResourceConfig resourceConfig) {
        resourceConfigRepository.updateResourceConfig(resourceConfig);
        resourceConfigCache.evictConfigCache(resourceConfig.getId());
    }

    public Boolean checkResourceNumbUniq(String pageCode, Integer resourceNumb, Long resourceId) {
        if (resourceNumb != null) {
            Assert.isTrue(resourceConfigRepository.checkResourceNumbUniq(pageCode, resourceNumb, resourceId), "资源位序号重复");
        }
        return true;
    }

    public Boolean checkResourceNameUniq(String pageCode, String resourceName, Long resourceId) {
        if (!StringUtils.isEmpty(resourceName)) {
            Assert.isTrue(resourceConfigRepository.checkResourceNameUniq(pageCode, resourceName, resourceId), "资源位名称重复");
        }
        return true;
    }

    public Long getResourceIdByNameLimitOne(String resourceName){
        return resourceConfigRepository.getResourceIdByNameLimitOne(resourceName);
    }
}