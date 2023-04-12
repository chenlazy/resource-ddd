package com.idanchuang.cms.server.domainNew.model.cms.aggregation.render;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-01-12 09:49
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum PageRenderStatus {

    /**
     * 页面状态
     */
    PAGE_STATUS_DEFAULT(0, "未知"),

    PAGE_STATUS_NO_START(1, "未开始"),

    PAGE_STATUS_PROCESS(2, "进行中"),

    PAGE_STATUS_END(3, "已结束");

    private final int val;

    private final String desc;

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static PageRenderStatus fromVal(int val) {
        for (PageRenderStatus data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
