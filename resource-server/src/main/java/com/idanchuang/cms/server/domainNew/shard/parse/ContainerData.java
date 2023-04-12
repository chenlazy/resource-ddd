package com.idanchuang.cms.server.domainNew.shard.parse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-17 16:46
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerData {

    /**
     * 容器内组件json解析数据
     */
    private Object componentJsonData;

    /**
     * 容器code
     */
    private String containerCode;

    /**
     * 容器名称
     */
    private String containerName;
}
