package com.idanchuang.cms.server.application.enums;

import lombok.Getter;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-22 12:32
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Getter
public enum PageTypeEnum {

    /**
     * 页面状态
     */
    PAGE_TYPE_PAGE(1, "页面"),

    PAGE_TYPE_SITUATION(2, "会场");

    PageTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private Integer type;

    private String desc;
}
