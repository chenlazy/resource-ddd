package com.idanchuang.resource.server.infrastructure.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.idanchuang.resource.server.domain.cache.ResourceConfigCache;
import com.idanchuang.resource.server.domain.cache.ResourceConfigKey;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author wengbinbin
 * @date 2021/3/23
 */

@Component
public class ResourceConfigCacheImpl implements ResourceConfigCache {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void evictConfigCache(ResourceConfigKey resourceConfigKey) {
        stringRedisTemplate.delete(resourceConfigKey.generatorConfigKey());
    }

    @Override
    public void evictConfigCache(Long resourceId) {
        ResourceConfigKey resourceUnitKey = ResourceConfigKey.builder().resourceId(resourceId).build();
        String configListKey = resourceUnitKey.generatorConfigListKey();
        String configKeys = stringRedisTemplate.opsForValue().get(configListKey);
        Optional.ofNullable(configKeys).ifPresent(value -> {
            JsonUtil.toList(value, new TypeReference<List<String>>() {}).forEach(unitKey -> stringRedisTemplate.delete(unitKey.toString()));
        });
        stringRedisTemplate.delete(configListKey);
    }
}
