package com.idanchuang.cms.server.application.enums;

import lombok.Getter;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-22 12:41
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Getter
public enum PageStatusEnum {

    /**
     * 页面状态
     */
    PAGE_STATUS_DRAFT(0, "草稿"),

    PAGE_STATUS_PUBLISH(1, "发布");

    private Integer status;

    private String desc;

    PageStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
