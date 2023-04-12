package com.idanchuang.cms.server.infrastructureNew.repository.convertor;

import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.model.cms.component.Component;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentBusinessType;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentId;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentType;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.ComponentDO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-28 16:17
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class RepositoryComponentConvert {

    private RepositoryComponentConvert() {

    }

    public static ComponentDO entityToDO(Component component) {

        if (null == component) {
            return null;
        }
        ComponentDO componentDO = new ComponentDO();
        componentDO.setId(component.getId().getValue());
        componentDO.setPageCode(null != component.getPageCode() ? component.getPageCode().getValue() : "");
        componentDO.setPageId(null != component.getMasterplateId() ? (int) component.getMasterplateId().getValue() : 0);
        componentDO.setContainerId(component.getContainerId().getValue());
        componentDO.setComponentType(component.getComponentType().getVal());
        componentDO.setModelType(null != component.getComponentBusinessType() ?
                component.getComponentBusinessType().getModelType() : 0);
        componentDO.setBizJson(component.getBizJson());
        componentDO.setOperatorId((int) component.getOperatorId().getValue());
        componentDO.setSnapRoot(component.getSnapRoot());
        return componentDO;
    }

    public static Component doToDomain(ComponentDO componentDO) {

        if (null == componentDO) {
            return null;
        }

        return new Component(new ComponentId(componentDO.getId()),
                new ContainerId(componentDO.getContainerId()),
                StringUtils.isNotEmpty(componentDO.getPageCode()) ? new PageCode(componentDO.getPageCode()) : null,
                componentDO.getPageId() != 0 ? new MasterplateId(componentDO.getPageId()) : null,
                componentDO.getComponentType() != null ? ComponentType.fromVal(componentDO.getComponentType()) : null,
                componentDO.getModelType() != null ? ComponentBusinessType.fromType(componentDO.getModelType()) : null,
                componentDO.getBizJson(),
                componentDO.getOperatorId() != null ? new OperatorId(componentDO.getOperatorId()) : null,
                componentDO.getSnapRoot());
    }
}
