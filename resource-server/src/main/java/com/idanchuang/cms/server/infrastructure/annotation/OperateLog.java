package com.idanchuang.cms.server.infrastructure.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:07
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OperateLog {
    /**
     * 操作描述
     *
     * @return
     */
    String desc();

    /**
     * 业务字段 日志归属字段 例如CMS搭建下 入参页面id
     * 入出现无入参 日志归属字段在接口返回结构中 默认使用 "data"接受返回体中参数
     * @return
     */
    String field();

    /**
     * 模块名 区分日志归属功能模块 例如 CMS搭建（CMS） 资源位系统（resource）
     *
     * @return
     */
    String module();

    /**
     * 日志类型 区分日志归属功能模块层级 例如 CMS搭建下 页面(pageSchema) 页面模版(page)
     *
     * @return
     */
    String logType();

    /**
     * 自定义日志处理方法(非必填) 例如CMS搭建下 删除页面模版(page) 日志记录到页面（pageSchema）下
     *
     * @return
     */
    Class<?> businessType() default Void.class;
}
