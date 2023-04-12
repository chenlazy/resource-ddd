package com.idanchuang.cms.server.domainNew.shard;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 17:06
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@AllArgsConstructor
public enum SnapRoot {

    /**
     * 是否是根节点
     */
    IS_NOT_ROOT(0, "快照节点"),

    IS_ROOT(1, "根节点");

    private final int val;
    private final String desc;

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static SnapRoot fromVal(int val) {
        for (SnapRoot data : values()) {
            if (data.val == val) {
                return data;
            }
        }
        return null;
    }
}
