package com.idanchuang.cms.server.domainNew.shard.parse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-17 16:44
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageContainerData {

    /**
     * 容器列表
     */
    private List<ContainerData> compDataList;

    /**
     * 页面配置
     */
    private Object pageConfig;
}
