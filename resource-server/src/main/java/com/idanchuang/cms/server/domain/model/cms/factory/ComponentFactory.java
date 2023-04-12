package com.idanchuang.cms.server.domain.model.cms.factory;

import com.idanchuang.cms.server.application.constant.PageStyleConstant;
import com.idanchuang.cms.server.application.enums.ComponentTypeEnum;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponent;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-11 11:28
 * @Desc: 组件工厂类
 * @Copyright VTN Limited. All rights reserved.
 */
public class ComponentFactory {


    private ComponentFactory() {

    }

    public static ContainerComponent createComponent(String type, Integer modelType, Object details, String group,
                                                     String modelJson, String bizJson, Long containerId, Integer operatorId) {

        Integer componentType = PageStyleConstant.GROUP_BASE_TYPE.equals(group) ? ComponentTypeEnum.COMPONENT_TYPE_BASE.getType() : ComponentTypeEnum.COMPONENT_TYPE_BUSINESS.getType();

        return new ContainerComponent(0L, containerId, componentType, modelType, bizJson, modelJson, operatorId, type, details, null, null);
    }

}
