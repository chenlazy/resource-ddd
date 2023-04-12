package com.idanchuang.cms.server.domain.model.cms.factory;

import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.cms.server.domain.model.cms.ContainerExtra;
import com.idanchuang.cms.server.domain.model.cms.container.ContainerCode;
import org.springframework.util.StringUtils;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-11 16:39
 * @Desc: 容器组件类
 * @Copyright VTN Limited. All rights reserved.
 */
public class ContainerFactory {

    private ContainerFactory() {

    }

    public static CmsPageContainer createContainer(Integer pageId, String containerCode, Integer status,
                                                   String pageStyle, String containerName, Integer operatorId,
                                                   ContainerExtra containerExtra) {

        return new CmsPageContainer(null, pageId, StringUtils.isEmpty(containerCode) ? null :new ContainerCode(containerCode), containerName, status, pageStyle, operatorId, null,
                null, 0, containerExtra);
    }
}
