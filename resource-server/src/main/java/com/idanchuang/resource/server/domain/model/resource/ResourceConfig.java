package com.idanchuang.resource.server.domain.model.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 资源位配置
 * Created by develop at 2021/2/4.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceConfig {

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
     * 资源位所在页面名称
     */
    private String pageName;

    /**
     * 业务类型，1 abm，2vtn，3tj
     */
    private Integer businessType;

    /**
     * 资源位类型1url类型，2数字类型，3图片类型，4对象类型
     */
    private Integer resourceType;

    /**
     * 资源位scheme，仅针对资源位类型为4的对象类型时适用，json格式
     */
    private String resourceScheme;

    /**
     * 组件样式限制，0未限制，1限制
     */
    private Integer componentConfined;

    /**
     * 所选的组件样式，json格式
     */
    private String componentType;

    /**
     * 资源位状态
     */
    private Integer resourceStatus;

    /**
     * 操作人
     */
    private String operatorUser;

    /**
     * 操作人id
     */
    private Integer operatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记，1表示删除
     */
    private Integer isDeleted;

    /**
     * 资源位序号，同一个页面不能出现多个序号位置相同的资源位
     */
    private Integer resourceNumb;

    /**
     * 创建人id
     */
    private Integer createUserId;

    /**
     * 创建人
     */
    private String createUser;

}
