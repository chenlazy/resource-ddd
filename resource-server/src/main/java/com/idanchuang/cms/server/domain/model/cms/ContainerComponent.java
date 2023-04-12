package com.idanchuang.cms.server.domain.model.cms;

import lombok.Value;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 16:55
 * @Desc: CMS组件实例表
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class ContainerComponent {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 容器id
     */
    private Long containerId;

    /**
     * 组件类型
     */
    private Integer componentType;

    /**
     * 模型类型
     */
    private Integer modelType;

    /**
     * 业务json数据
     */
    private String bizJson;

    /**
     * 模型数据json数据
     */
    private String modelJson;

    /**
     * 操作人id
     */
    private Integer operatorId;

    /**
     * 属性类型
     */
    private String type;

    /**
     * 返回详情
     */
    private Object details;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
