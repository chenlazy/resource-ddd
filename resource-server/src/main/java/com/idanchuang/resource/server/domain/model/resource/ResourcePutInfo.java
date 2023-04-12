package com.idanchuang.resource.server.domain.model.resource;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fym
 * @description : 资源位投放内容模型
 * @date 2021/3/25 下午1:05
 */
@Data
public class ResourcePutInfo {

    /**
     * 自增id
     */
    private long id;

    /**
     * 资源位名称
     */
    private String resourceName;

    /**
     * 资源位所在页面id
     */
    private String pageCode;

    /**
     * 业务类型，1 abm，2vtn，3tj
     */
    private int businessType;

    /**
     * 资源位类型1url类型，2数字类型，3图片类型，4对象类型
     */
    private int resourceType;

    /**
     * 资源位scheme，仅针对资源位类型为4的对象类型时适用，json格式
     */
    private String resourceScheme;

    /**
     * 资源位状态
     */
    private int resourceStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 资源位序号，同一个页面不能出现多个序号位置相同的资源位
     */
    private int resourceNumb;

    /**
     * 投放内容
     */
    private ResourceUnit resourceUnit;
}
