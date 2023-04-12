package com.idanchuang.resource.server.application.service;

import com.google.common.collect.Lists;
import com.idanchuang.resource.server.domain.model.resource.*;
import com.idanchuang.resource.server.domain.repository.ResourceConfigRepository;
import com.idanchuang.resource.server.domain.repository.ResourceUnitRepository;
import com.idanchuang.resource.server.infrastructure.common.constant.RedisBusinessKeyConstant;
import com.idanchuang.resource.server.infrastructure.transfer.ResourcePutInfoTransfer;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

/**
 * @author fym
 * @description :
 * @date 2021/3/11 下午6:19
 */
@Service
@Slf4j
public class ResourceService {

    @Resource
    private ResourceConfigRepository resourceConfigRepository;
    @Resource
    private ResourceUnitRepository resourceUnitRepository;
    @Resource
    private CacheUtil cacheUtil;

    public List<ResourcePutInfo> getContentPutInfo(List<Long> resourceIdList, String pageCode, Integer business, String platform, Integer role) {
        List<ResourcePutInfo> list = new ArrayList<>();
        for (Long resourceId : resourceIdList) {
            ResourcePutInfo resourceConfig = this.getContentPutInfoByResourceId(resourceId, pageCode, business, platform, role);
            if (null == resourceConfig) {
                continue;
            }
            list.add(resourceConfig);
        }
        return list;
    }

    public ResourcePutInfo getContentPutInfoByResourceId(Long resourceId, String pageCode, Integer business, String platform, Integer role) {
        ResourcePutInfo resourceConfigByReq = this.queryResourceConfig(Long.valueOf(resourceId), pageCode, business);
        if (null == resourceConfigByReq) {
            return null;
        }
        ResourceUnit resourceUnit = this.queryResourceUnit(Long.valueOf(resourceId), platform, role);
        resourceConfigByReq.setResourceUnit(resourceUnit);
        return resourceConfigByReq;
    }

    public List<ResourceConfig> getResourceListByPageId(String pageCode, Integer business) {
        return resourceConfigRepository.getResourceListByPageId(pageCode, business);
    }

    public ResourcePutInfo getPreviewPutContent(PreviewPutContentQuery req) {
        ResourcePutInfo resourceConfigByReq = this.queryResourceConfig(req.getResourceId(), req.getPageCode(), req.getBusiness());
        if (null == resourceConfigByReq) {
            return null;
        }
        ResourceUnit resourceUnit = resourceUnitRepository.getUnitInfoById(req.getUnitId());
        if (!req.getResourceId().equals(req.getPreResourceId())) {
            resourceUnit = this.queryPreResourceUnit(req.getResourceId(), req.getPlatform(), req.getRole(), resourceUnit.getStartTime());
        }
        resourceConfigByReq.setResourceUnit(resourceUnit);
        return resourceConfigByReq;
    }

    public List<ResourcePutInfo> getPreviewPutContentList(PreviewPutContentBatchQuery req) {
        List<ResourcePutInfo> resourceConfigs = Lists.newArrayList();
        PreviewPutContentQuery query = ResourcePutInfoTransfer.req2BatchReq(req);
        for (Long resourceId : req.getResourceIds()) {
            query.setResourceId(resourceId);
            ResourcePutInfo resourceConfig = this.getPreviewPutContent(query);
            if (null == resourceConfig) {
                continue;
            }
            resourceConfigs.add(resourceConfig);
        }
        return resourceConfigs;
    }

    private ResourcePutInfo queryResourceConfig(Long resourceId, String pageCode, Integer business) {
        String key = format(RedisBusinessKeyConstant.RESOURCE_CONFIG_KEY, resourceId, pageCode, business);
        ResourcePutInfo resourceConfigByReq = cacheUtil.get(key, ResourcePutInfo.class);
        if (null == resourceConfigByReq) {
            resourceConfigByReq = resourceConfigRepository.getResourceConfigByReq(resourceId, pageCode, business);
            if (null == resourceConfigByReq) {
                return null;
            } else {
                cacheUtil.set(key, JsonUtil.toJsonString(resourceConfigByReq), 60, TimeUnit.MINUTES);
                String listKey = format(RedisBusinessKeyConstant.RESOURCE_CONFIG_LIST_KEY, resourceId);
                this.setRedisList(listKey, key);
            }
        }
        return resourceConfigByReq;
    }

    private ResourceUnit queryPreResourceUnit(Long resourceId, String platform, Integer role, LocalDateTime time) {
        ResourceUnit resourceUnit = resourceUnitRepository.queryResourceUnitByResourceAndRole(resourceId, role, platform, time);
        return resourceUnit;
    }

    private ResourceUnit queryResourceUnit(Long resourceId, String platform, Integer role) {
        String infoKey = format(RedisBusinessKeyConstant.RESOURCE_UNIT_KEY, resourceId, platform, role);
        ResourceUnit resourceUnit = cacheUtil.get(infoKey, ResourceUnit.class);
        if (null == resourceUnit) {
            resourceUnit = resourceUnitRepository.queryResourceUnitByResourceAndRole(resourceId, role, platform, LocalDateTime.now());
            if (null != resourceUnit) {
                cacheUtil.setByTime(infoKey, JsonUtil.toJsonString(resourceUnit), resourceUnit.getEndTime());
                String listKey = format(RedisBusinessKeyConstant.RESOURCE_UNIT_LIST_KEY, resourceId);
                this.setRedisList(listKey, infoKey);
            }
        }
        return resourceUnit;
    }

    private void setRedisList(String listKey, String infoKey) {
        String forString = cacheUtil.getForString(listKey);
        String[] strings = JsonUtil.toObject(forString, String[].class);
        List<String> list1 = Lists.newArrayList();
        if (null == strings) {
            list1.add(infoKey);
        } else {
            List<String> list = Arrays.asList(strings);
            if (!list.contains(infoKey)) {
                list1.addAll(list);
                list1.add(infoKey);
            }
        }
        if (!CollectionUtils.isEmpty(list1)) {
            cacheUtil.set(listKey, JsonUtil.toJsonString(list1));
        }
    }

}
