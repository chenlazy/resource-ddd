package com.idanchuang.cms.server.domain.model.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//前端多容器页面样式
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageContainerData {


    //容器信息
    private List<CmsCompData> compDataList;

    /**
     * 页面配置
     */
    private Object pageConfig;

}
