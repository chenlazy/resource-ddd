package com.idanchuang.cms.server.application.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {
//    @Bean
//    public CacheManager cacheManager(){
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        //Caffeine配置
//        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
//                                            //最后一次写入后经过固定时间过期
//                                            //maximumSize=[long]: 缓存的最大条数
//
//                                            .expireAfterWrite(20, TimeUnit.SECONDS)
//                                            .maximumSize(1000);
//        cacheManager.setCaffeine(caffeine);
//        return cacheManager;
//    }
//
//
//    /**
//     * 相当于在构建LoadingCache对象的时候 build()方法中指定过期之后的加载策略方法
//     * 必须要指定这个Bean，refreshAfterWrite=60s属性才生效
//     * @return
//     */
//    @Bean
//    public CacheLoader<String, Object> cacheLoader() {
//        CacheLoader<String, Object> cacheLoader = new CacheLoader<String, Object>() {
//            @Override
//            public Object load(String key) throws Exception {
//                return null;
//            }
//            // 重写这个方法将oldValue值返回回去，进而刷新缓存
//            @Override
//            public Object reload(String key, Object oldValue) throws Exception {
//                return oldValue;
//            }
//        };
//        return cacheLoader;
//    }
}