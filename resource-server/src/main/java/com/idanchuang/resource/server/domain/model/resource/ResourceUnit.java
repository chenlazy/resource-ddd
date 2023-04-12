package com.idanchuang.resource.server.domain.model.resource;

import com.idanchuang.resource.server.domain.model.strategy.UnitStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResourceUnit {
    /**
     * id
     */
    private long id;

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

    private UnitStrategy unitStrategy;

    /**
     * 投放内容名称
     */
    private String contentTitle;

    /**
     * 组件json内容
     */
    private String componentJsonData;

    /**
     * 资源位名称
     */
    private String resourceName;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面code
     */
    private String pageCode;

    /**
     * 投放状态，0未投放，1投放中，2过期
     */
    private Integer workStatus;

    /**
     * 组件样式限制，0未限制，1限制
     */
    private Integer componentConfined;



    /**
     * 所选的组件样式，json格式
     */
    private String componentType;

    /**
     * 创建人id
     */
    private Integer createUserId;

    /**
     * 创建人
     */
    private String createUser;

    private String componentName;

    /**
     * 资源位类型1url类型，2数字类型，3图片类型，4对象类型
     */
    private Integer resourceType;

    /**
     * 资源位scheme，仅针对资源位类型为4的对象类型时适用，json格式
     */
    private String resourceScheme;

    public void currentWorkStatus(LocalDateTime dateTime) {
        if (!dateTime.isBefore(startTime)) {
            if(dateTime.isBefore(endTime)){
                this.workStatus = 1;
            }else {
                this.workStatus = 2;
            }
        } else {
            this.workStatus = 0;
        }
    }
}
