package com.idanchuang.resource.server.domain.cache;

import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;

/**
 * @author wengbinbin
 * @date 2021/3/23
 */


public interface ResourceUnitCache {
    void evictUnitCache(ResourceUnitKey resourceUnitKey);

    void batchEvictUnitCache(ResourceUnit resourceUnit);

    void batchEvictUnitCacheWhenUpdate(ResourceUnit resourceUnit);

    void evictUnitCache(Long resourceId);
}
