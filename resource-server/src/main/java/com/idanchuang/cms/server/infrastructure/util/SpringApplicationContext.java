package com.idanchuang.cms.server.infrastructure.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-15 11:43
 * @Desc: spring容器组件
 * @Copyright VTN Limited. All rights reserved.
 */
@Component
public class SpringApplicationContext {

    /**
     * spring容器
     */
    private ApplicationContext context;

    /**
     * 构造函数
     * @param context spring容器
     */
    @Autowired
    public SpringApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 获取bean
     * @param clazz bean类型
     * @return bean实例
     */
    public <T> T getBean(Class<? extends T> clazz) {
        return context.getBean(clazz);
    }

}
