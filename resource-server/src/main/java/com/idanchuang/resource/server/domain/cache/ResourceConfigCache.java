package com.idanchuang.resource.server.domain.cache;

/**
 * @author wengbinbin
 * @date 2021/3/23
 */


public interface ResourceConfigCache {
    void evictConfigCache(ResourceConfigKey resourceConfigKey);

    void evictConfigCache(Long resourceId);
}
