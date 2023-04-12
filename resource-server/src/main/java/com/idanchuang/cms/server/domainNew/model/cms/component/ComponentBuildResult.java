package com.idanchuang.cms.server.domainNew.model.cms.component;

import lombok.Value;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-22 14:09
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */

@Value
public class ComponentBuildResult {

    /**
     * 组件
     */
    private Component component;

    /**
     * 返回详情
     */
    private Object details;

    /**
     * 组件类型
     */
    private String type;
}
