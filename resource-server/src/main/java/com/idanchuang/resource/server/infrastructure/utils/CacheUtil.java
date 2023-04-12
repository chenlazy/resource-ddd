package com.idanchuang.resource.server.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author fym
 * @description : 适用于JackJson序列化方式的redis存储工具
 * @date 2021/3/26 下午3:28
 */
@Slf4j
@Component
public class CacheUtil {

    public static final int CACHE_SEC = 60;
    public static final long CACHE_ONE = 1;
    public static final long CACHE_ZERO = 0;

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public CacheUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    /**
     * 获取redis缓存数据 反序列化方式为JackJson
     *
     * @param key
     * @param clazz
     * @param <V>
     * @return
     */
    public <V> V get(String key, Class<V> clazz) {
        String cacheStr = stringRedisTemplate.opsForValue().get(key);
        return JsonUtil.toObject(cacheStr, clazz);
    }

    public String getForString(String key) {
        String cacheStr = stringRedisTemplate.opsForValue().get(key);
        return cacheStr;
    }

    /**
     * 存储缓存 缓存时间为 [expireSec,expireSec+20] timeUnit
     *
     * @param key
     * @param cacheStr  序列化方式为 JackJson
     * @param expireSec 缓存时间
     * @param timeUnit  时间单位
     */
    public void set(String key, String cacheStr, int expireSec, TimeUnit timeUnit) {
        try {
            stringRedisTemplate.opsForValue().set(key, cacheStr, generateRandomSeconds(expireSec), timeUnit);
        } catch (Exception e) {
            log.error("CacheUtil set redis error key:{} e:{}", key, e);
        }
    }

    public void set(String key, String cacheStr) {
        try {
            stringRedisTemplate.opsForValue().set(key, cacheStr);
        } catch (Exception e) {
            log.error("CacheUtil set redis error key:{} e:{}", key, e);
        }
    }

    /**
     * 存储缓存 缓存时间为 [endTime - nowTime] timeUnit
     *
     * @param key
     * @param cacheStr 序列化方式为 JackJson
     * @param endTime  结束时间
     */
    public void setByTime(String key, String cacheStr, LocalDateTime endTime) {
        if (LocalDateTime.now().compareTo(endTime) >= 0) {
            log.info("CacheUtil setByTime redis expireSec error key:{} now:{} endTime:{}", key, LocalDateTime.now(), endTime);
            return;
        }
        Long expireSec = cacheTime(endTime);
        try {
            stringRedisTemplate.opsForValue().set(key, cacheStr, expireSec, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("CacheUtil setByTime redis error key:{} nowTime:{} endTime:{} expireSec:{} e:{}", key, LocalDateTime.now(), endTime, expireSec, e);
        }
    }

    /**
     * 删除单个key缓存
     *
     * @param key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除key缓存
     *
     * @param keys
     */
    public void deleteBatch(List<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 随机浮动缓存时间 [expireSec,expireSec+20] timeUnit
     * NULL值也会缓存
     *
     * @param key
     * @param expireSec
     * @param clazz
     * @param supplier
     * @param timeUnit
     * @param <V>
     * @return
     */
    public <V> V computeIfAbsent(String key, int expireSec, Class<V> clazz, Supplier<V> supplier, TimeUnit timeUnit) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value == null) {
            V computeVal = supplier.get();
            stringRedisTemplate.opsForValue().set(key, JsonUtil.toJsonString(computeVal), generateRandomSeconds(expireSec), timeUnit);
            return computeVal;
        }
        return JsonUtil.toObject(value, clazz);
    }

    private int generateRandomSeconds(int originalSec) {
        return originalSec + ThreadLocalRandom.current().nextInt(20);
    }

    private Long cacheTime(LocalDateTime endTime) {
        Long time = CACHE_ONE;
        try {
            Duration duration = Duration.between(LocalDateTime.now(), endTime);
            time = duration.toMinutes();
        } catch (Exception e) {
            log.error("CacheUtil cacheTime error endTime:{} e:{}", endTime, e);
        }
        if (time == CACHE_ZERO) {
            time = CACHE_ONE;
        }
        return time;
    }

}
