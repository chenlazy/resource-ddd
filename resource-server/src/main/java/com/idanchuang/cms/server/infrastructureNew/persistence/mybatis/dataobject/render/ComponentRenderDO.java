package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.render;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-01-04 16:15
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentRenderDO {

    /**
     * 组件类型
     */
    private Integer modelType;

    /**
     * 组件内容
     */
    private String modelContent;
}
