package com.idanchuang.cms.server.interfaces.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-15 13:56
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Configuration
public class ThreadConfig {

    @Bean("operateLogExecutorService")
    ExecutorService operateLogExecutorService(){
        return new ThreadPoolExecutor(5, 10, 300,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(300), r -> new Thread(r,"t_operate_log_pool" + r.hashCode()));
    }
}
