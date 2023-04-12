package com.idanchuang.cms.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 渲染的容器
 * @author lei.liu
 * @date 2021/9/10
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContainerRenderDTO implements Serializable {
    private static final long serialVersionUID = 8594379114254718316L;

    /**
     * 容器ID
     */
    private Long containerId;

    /**
     * 容器样式内容
     */
    private String styleContent;


}
