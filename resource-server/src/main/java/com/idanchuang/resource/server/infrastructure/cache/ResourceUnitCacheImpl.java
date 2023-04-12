package com.idanchuang.resource.server.infrastructure.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idanchuang.resource.api.common.PlatformTypeEnum;
import com.idanchuang.resource.server.domain.cache.ResourceUnitCache;
import com.idanchuang.resource.server.domain.cache.ResourceUnitKey;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author wengbinbin
 * @date 2021/3/23
 */

@Component
public class ResourceUnitCacheImpl implements ResourceUnitCache {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void evictUnitCache(ResourceUnitKey resourceUnitKey) {
        stringRedisTemplate.delete(resourceUnitKey.generatorUnitKey());
    }

    @Override
    public void batchEvictUnitCache(ResourceUnit resourceUnit) {
        try {
            if (StringUtils.isEmpty(resourceUnit.getPlatformFrom())) {
                return;
            }
            Arrays.asList(objectMapper.readValue(resourceUnit.getPlatformFrom(), Integer[].class)).forEach(platformFrom -> {
                resourceUnit.getUnitStrategy().getVisibleRoleS().forEach(visibleRole ->
                        evictUnitCache(ResourceUnitKey.builder().resourceId(resourceUnit.getResourceId())
                                .platformFrom(platformFrom).role(visibleRole).build()));
            });
        } catch (IOException e) {
            throw new RuntimeException(String.format("解析投放平台字段失败, PlatformFrom : [%s]", resourceUnit.getPlatformFrom()));
        }
    }

    @Override
    public void batchEvictUnitCacheWhenUpdate(ResourceUnit resourceUnit) {
        Arrays.asList(PlatformTypeEnum.values()).forEach(platformTypeEnum ->
                resourceUnit.getUnitStrategy().getVisibleRoleS().forEach(visibleRole ->
                        evictUnitCache(ResourceUnitKey.builder().resourceId(resourceUnit.getResourceId())
                                .platformFrom(platformTypeEnum.getType()).role(visibleRole).build())));
    }

    @Override
    public void evictUnitCache(Long resourceId) {
        ResourceUnitKey resourceUnitKey = ResourceUnitKey.builder().resourceId(resourceId).build();
        String unitListKey = resourceUnitKey.generatorUnitListKey();
        String unitKeys = stringRedisTemplate.opsForValue().get(unitListKey);
        Optional.ofNullable(unitKeys).ifPresent(value -> {
            JsonUtil.toList(value, new TypeReference<List<String>>() {}).forEach(unitKey -> stringRedisTemplate.delete(unitKey.toString()));
        });
        stringRedisTemplate.delete(unitListKey);
    }
}
