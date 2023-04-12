package com.idanchuang.cms.server.domainNew.model.cms.aggregation.render;

import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentBusinessType;
import lombok.Value;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-23 11:16
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class ComponentRender {

    /**
     * 组件业务类型
     */
    private ComponentBusinessType modelType;

    /**
     * 组件内容
     */
    private String modelContent;
}
