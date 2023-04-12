package com.idanchuang.resource.server.infrastructure.common.constant;

/**
 * @author fym
 * @description :
 * @date 2021/3/16 上午9:56
 */
public final class RedisBusinessKeyConstant {

    /**
     * 资源位缓存集合key
     */
    public static final String RESOURCE_CONFIG_LIST_KEY = "content:resourceConfig:listKey:resourceId_%s";
    /**
     * 资源位缓存key
     */
    public static final String RESOURCE_CONFIG_KEY = "content:resourceConfig:resourceId_%s_pageCode_%s_business_%s";
    /**
     * 资源位投放内容缓存集合key
     */
    public static final String RESOURCE_UNIT_LIST_KEY = "content:ResourceUnit:listKey:resourceId_%s";
    /**
     * 资源位投放内容缓存key
     */
    public static final String RESOURCE_UNIT_KEY = "content:ResourceUnit:resourceId_%s_platform_%s_role_%s";

    /**
     * cms商品包含活动页面缓存key
     */
    public static final String CMS_SUBJECT_BY_SPU_KEY = "cms:subject:by_spuId_%s";

    /**
     * cms商品包含活动页面缓存key（重构后）
     */
    public static final String CMS_SUBJECT_DDD_BY_SPU_KEY = "cms:ddd:subject:by_spuId_%s";

    /**
     * cms活动页面缓存key
     */
    public static final String CMS_SUBJECT_BY_ACTIVITY_KEY = "cms:subject:by_activity_%s";

}
