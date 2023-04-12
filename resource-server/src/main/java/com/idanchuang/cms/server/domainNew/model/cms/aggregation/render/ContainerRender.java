package com.idanchuang.cms.server.domainNew.model.cms.aggregation.render;

import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerCode;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import lombok.Value;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-23 11:11
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class ContainerRender {

    /**
     * 容器id
     */
    private ContainerId containerId;

    /**
     * 容器code
     */
    private ContainerCode containerCode;

    /**
     * 容器样式内容
     */
    private String styleContent;

    /**
     * 组件列表
     */
    private List<ComponentRender> components;
}
