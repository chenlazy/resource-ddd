package com.idanchuang.resource.server.infrastructure.cache;

import com.idanchuang.resource.server.SpringTest;
import com.idanchuang.resource.server.domain.cache.ResourceUnitCache;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author wengbinbin
 * @date 2021/4/2
 */


public class ResourceUnitCacheImplTest extends SpringTest {
    @Resource
    private ResourceUnitCache resourceUnitCache;

    @Test
    public void evictUnitCache() {
        resourceUnitCache.evictUnitCache(98L);
        resourceUnitCache.evictUnitCache(98L);
        resourceUnitCache.evictUnitCache(98L);
    }
}