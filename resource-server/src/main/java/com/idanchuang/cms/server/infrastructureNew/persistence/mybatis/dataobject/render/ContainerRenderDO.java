package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.render;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-01-04 16:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ContainerRenderDO {

    /**
     * 容器ID
     */
    private Long containerId;

    /**
     * 容器code
     */
    private String containerCode;

    /**
     * 容器样式内容
     */
    private String styleContent;

    /**
     * 承载的组件
     */
    private List<ComponentRenderDO> components;
}
