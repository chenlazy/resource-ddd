package com.idanchuang.cms.server.domain.model.cms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 渲染的组件
 * @author lei.liu
 * @date 2021/9/10
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComponentRender implements Serializable {
    private static final long serialVersionUID = 310982771185305858L;

    /**
     * 组件类型
     */
    private Integer modelType;

    /**
     * 组件内容
     */
    private String modelContent;
}
