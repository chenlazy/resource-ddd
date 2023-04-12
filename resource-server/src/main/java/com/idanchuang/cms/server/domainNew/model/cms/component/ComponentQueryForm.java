package com.idanchuang.cms.server.domainNew.model.cms.component;

import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 14:51
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ComponentQueryForm {

    /**
     * 页码
     */
    private int pageNum = 1;

    /**
     * 每页大小
     */
    private int pageSize = 10;

    /**
     * 容器id列表
     */
    private List<ContainerId> containerIds;

    /**
     * 模型类型
     */
    private ComponentBusinessType businessType;
}
