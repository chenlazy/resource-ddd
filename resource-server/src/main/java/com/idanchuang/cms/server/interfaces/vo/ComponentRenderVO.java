package com.idanchuang.cms.server.interfaces.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ComponentRenderVO {

    /**
     * 组件类型
     */
    private Integer modelType;

    /**
     * 组件内容
     */
    private String modelContent;
}
