package com.idanchuang.cms.server.domain.model.cms;

import com.idanchuang.cms.server.domain.model.cms.container.ContainerCode;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 17:23
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class CmsPageContainer {

    /**
     * 主键id
     */
    @NonFinal
    private Long id;

    /**
     * 页面实例id
     */
    private Integer pageId;

    /**
     * 容器code
     */
    private ContainerCode containerCode;

    /**
     * 容器名称
     */
    private String containerName;

    /**
     * 容器状态 1 草稿 2 已发布
     */
    private Integer status;

    /**
     * 前端页面格式
     */
    private String pageStyle;

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
     * 删除时间
     */
    private Integer deleted;


    /**
     * 扩展字段
     */
    private ContainerExtra containerExtra;

    public void setId(Long id) {
        this.id = id;
    }
}
