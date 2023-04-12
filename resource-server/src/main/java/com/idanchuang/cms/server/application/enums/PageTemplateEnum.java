package com.idanchuang.cms.server.application.enums;

import lombok.Getter;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-10-09 14:54
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Getter
public enum PageTemplateEnum {

    /**
     * 页面状态
     */
    PAGE_WAIT_VALID(0, "待生效"),

    PAGE_VALID(1, "生效中"),

    PAGE_EXPIRED(2, "已失效");

    private Integer status;

    private String desc;

    PageTemplateEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
