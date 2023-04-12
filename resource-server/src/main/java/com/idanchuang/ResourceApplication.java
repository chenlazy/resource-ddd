package com.idanchuang;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by develop at 2021/2/4.
 */
@Slf4j
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableCreateCacheAnnotation
@EnableFeignClients(value = {"com.idanchuang.*"})
@EnableMethodCache(basePackages = "com.idanchuang.*.server")
@MapperScan("com.idanchuang.*.server.infrastructure.persistence.mapper")
@EnableCaching
public class ResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceApplication.class, args);
        log.info("\n ┌─────────────────────────────────────┐\n" +
                " │ resource run success│\n" +
                " └─────────────────────────────────────┘");
    }
}
