package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-28 15:58
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("w_cms_container_component")
public class ComponentDO {

    /**
     * 组件id
     */
    private Long id;

    /**
     * 页面code
     */
    private String pageCode;

    /**
     * 页面id
     */
    private int pageId;

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
     * 业务数据json
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
     * 是否是快照根
     */
    private Long snapRoot;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    private Integer deleted;
}
