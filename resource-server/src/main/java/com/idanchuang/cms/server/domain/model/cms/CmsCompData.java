package com.idanchuang.cms.server.domain.model.cms;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmsCompData {

    /**
     * 容器内组件json解析数据
     */
    private Object componentJsonData;


    /**
     * 容器code
     */
    private String containerCode;

}
