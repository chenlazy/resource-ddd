package com.idanchuang.cms.server.application.enums;

import lombok.Getter;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-22 13:19
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Getter
public enum ContainerStatusEnum {

    /**
     * 容器状态
     */
    CONTAINER_STATUS_DRAFT(1, "草稿"),

    CONTAINER_STATUS_PUBLISH(2, "发布");

    private Integer status;

    private String desc;

    ContainerStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
