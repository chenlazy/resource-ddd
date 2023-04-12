package com.idanchuang.resource.server.domain.model.resource;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wengbinbin
 * @date 2021/3/19
 */
@Data
public class ResourceUnitQueryConditions {

    private String pageCode;

    private String resourceName;

    /**
     * 资源位id
     */
    private Long resourceId;

    /**
     * 投放起始时间
     */
    private LocalDateTime startTime;

    /**
     * 投放结束时间
     */
    private LocalDateTime endTime;

    /**
     * 启用状态，0关闭，1启动
     */
    private Integer activeStatus;
    /**
     * 投放内容名称
     */
    private String contentTitle;

    /**
     * 操作人
     */
    private String operatorUser;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 投放状态，0未投放，1投放中，2过期
     */
    private Integer workStatus;

    private Integer pageSize = 10;

    private Integer pageNum = 1;

    private LocalDateTime queryDateTime = LocalDateTime.now();

}
