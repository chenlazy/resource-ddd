package com.idanchuang.resource.server.infrastructure.common.config;

import com.alicp.jetcache.MultiLevelCache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.method.CacheHandler;
import com.alicp.jetcache.anno.method.CacheInvokeConfig;
import com.alicp.jetcache.anno.support.CachedAnnoConfig;
import com.alicp.jetcache.anno.support.ConfigMap;
import com.idanchuang.spi.config.IConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wyh
 * @date 2020/4/23
 */
@Component
@Slf4j
public class JetCacheDynamicConfig {

    @Resource
    private IConfig iConfig;

    @Resource
    private ConfigMap configMap;

    @PostConstruct
    public void addListener() {
        iConfig.addChangeListener((keys) -> {
            for (String key : keys) {
                try {
                    if (StringUtils.startsWith(key, "jetcache.dynamic.time.remote")) {
                        String[] split = StringUtils.split(key, ".");
                        if (split.length < 6) {
                            continue;
                        }

                        synchronized (JetCacheDynamicConfig.class) {
                            final CacheInvokeConfig config = configMap.getByCacheName(split[4], split[5]);
                            if (null == config) {
                                continue;
                            }

                            CachedAnnoConfig cachedAnnoConfig = config.getCachedAnnoConfig();
                            if (null == cachedAnnoConfig) {
                                continue;
                            }

                            if(iConfig.getLong(key, 180L).equals(cachedAnnoConfig.getExpire())) {
                                continue;
                            }

                            if(cachedAnnoConfig.getCacheType() == CacheType.LOCAL) {
                                continue;
                            }

                            cachedAnnoConfig.setExpire(iConfig.getLong(key, 240L));
                            if(null == cachedAnnoConfig.getCache()) {
                                continue;
                            }

                            CacheHandler.CacheHandlerRefreshCache cache = (CacheHandler.CacheHandlerRefreshCache) cachedAnnoConfig.getCache();

                            if(CacheType.REMOTE == cachedAnnoConfig.getCacheType()) {
                                cache.getTargetCache().config().setExpireAfterWriteInMillis(TimeUnit.SECONDS.toMillis(iConfig.getLong(key, 240L)));
                            }

                            if(CacheType.BOTH == cachedAnnoConfig.getCacheType()) {
                                MultiLevelCache targetCache = (MultiLevelCache) cache.getTargetCache();
                                targetCache.caches()[1].config().setExpireAfterWriteInMillis(TimeUnit.SECONDS.toMillis(iConfig.getLong(key, 240L)));
                            }

                            log.info("缓存key:{},更新后时间：{}",key, cachedAnnoConfig.getExpire());
                        }

                    }
                } catch (Exception e) {
                    log.error("缓存时间动态变更异常key:{} ", key);
                }
            }
        });
    }
}
