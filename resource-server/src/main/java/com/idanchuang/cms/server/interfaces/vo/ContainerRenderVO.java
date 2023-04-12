package com.idanchuang.cms.server.interfaces.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-31 16:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerRenderVO {

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
    private List<ComponentRenderVO> components;
}
