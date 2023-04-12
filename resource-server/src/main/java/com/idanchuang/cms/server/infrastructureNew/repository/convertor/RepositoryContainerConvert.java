package com.idanchuang.cms.server.infrastructureNew.repository.convertor;

import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerCode;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerExtra;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerStatus;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.ContainerDO;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-28 16:16
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class RepositoryContainerConvert {

    private RepositoryContainerConvert() {

    }

    public static ContainerDO entityToDO(Container container) {

        if (null == container) {
            return null;
        }

        Long containerId = null != container.getId() ? container.getId().getValue() : null;
        Integer pageId = null != container.getMasterplateId() ? (int) container.getMasterplateId().getValue() : null;
        String containerCode = null != container.getContainerCode() ? container.getContainerCode().getValue() : "";
        String containerName = StringUtils.isNotBlank(container.getContainerName()) ? container.getContainerName() : "";
        Integer status = null != container.getStatus() ? container.getStatus().getVal() : 0;
        String pageStyle = StringUtils.isNotBlank(container.getPageStyle()) ? container.getPageStyle() : "";
        Integer operatorId = null != container.getOperatorId() ? (int)container.getOperatorId().getValue() : null;
        String extra = null != container.getExtra() ? JsonUtil.toJsonString(container.getExtra()) : "";
        Long snapRoot = null != container.getSnapRoot() ? container.getSnapRoot() : 0;

        return ContainerDO.builder().id(containerId).pageId(pageId).containerCode(containerCode).containerName(containerName)
                .status(status).pageStyle(pageStyle).operatorId(operatorId).extra(extra).snapRoot(snapRoot).build();
    }

    public static Container doToDomain(ContainerDO containerDO) {

        if (null == containerDO) {
            return null;
        }

        return new Container(new ContainerId(containerDO.getId()),
                new MasterplateId(containerDO.getPageId()),
                StringUtils.isNotEmpty(containerDO.getContainerCode()) ? new ContainerCode(containerDO.getContainerCode()) : null,
                containerDO.getContainerName(),
                ContainerStatus.fromVal(containerDO.getStatus()),
                new ContainerExtra(),
                containerDO.getPageStyle(),
                containerDO.getOperatorId() != null && containerDO.getOperatorId() > 0 ?
                        new OperatorId(containerDO.getOperatorId()) : null,
                containerDO.getSnapRoot());
    }

}
