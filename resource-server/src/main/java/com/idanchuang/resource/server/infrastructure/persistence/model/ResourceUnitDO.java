package com.idanchuang.resource.server.infrastructure.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wuai
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("resource_unit")
public class ResourceUnitDO {
    /**
     * id
     */
    private Long id;

    /**
     * 是否删除，0：未删除，1：已删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 操作人id
     */
    private Integer operatorId;

    /**
     * 操作人
     */
    private String operatorUser;

    /**
     * 启用状态，0关闭，1启动
     */
    private Integer activeStatus;

    /**
     * 资源位id
     */
    private Long resourceId;

    /**
     * 投放平台，默认1 1.客户端、2.h5、3.小程序
     */
    private String platformFrom;

    /**
     * 投放优先级，默认最低等级1
     */
    private Integer weight;

    /**
     * 投放起始时间
     */
    private LocalDateTime startTime;

    /**
     * 投放结束时间
     */
    private LocalDateTime endTime;

    /**
     * 投放角色
     */
//    private String visibleRoleS;
    private String unitStrategy;

    /**
     * 投放内容名称
     */
    private String contentTitle;

    /**
     * 组件json内容
     */
    private String componentJsonData;

    private String createUser;

    private Integer createUserId;

    private String componentName;
}
