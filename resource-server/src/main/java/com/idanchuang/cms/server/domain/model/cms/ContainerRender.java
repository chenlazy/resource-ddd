package com.idanchuang.cms.server.domain.model.cms;

import com.idanchuang.cms.server.domain.model.cms.container.ContainerCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 渲染的容器
 * @author lei.liu
 * @date 2021/9/10
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContainerRender implements Serializable {
    private static final long serialVersionUID = -570102166077513473L;

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
    private List<ComponentRender> components;



}
