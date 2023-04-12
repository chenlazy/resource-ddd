package com.idanchuang.cms.server.domainNew.model.cms.container;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.application.constant.PageStyleConstant;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 10:41
 * @Desc: 容器领域类
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class Container {

    /**
     * 容器id
     */
    @NonFinal
    private ContainerId id;

    /**
     * 模版id（page_id）
     */
    private MasterplateId masterplateId;

    /**
     * 容器编码（container_code）
     */
    private ContainerCode containerCode;

    /**
     * 容器名称（container_name)
     */
    private String containerName;

    /**
     * 容器状态（status）
     */
    private ContainerStatus status;

    /**
     * 容器额外信息（extra）
     */
    private ContainerExtra extra;

    /**
     * 容器样式（page_style）
     */
    private String pageStyle;

    /**
     * 操作人(operator_id)
     */
    private OperatorId operatorId;

    /**
     * 快照标识
     */
    @NonFinal
    private Long snapRoot;

    /**
     * 设置容器id
     * @param containerId
     */
    public void setContainerId(ContainerId containerId) {
        this.id = containerId;
    }

    /**
     * 转化为快照
     */
    public void transferSnapshot() {
        this.snapRoot = -1L;
    }

    /**
     * 获取包含的组件列表
     * @return
     */
    public List<Object> getComponents() {

        String pageStyle = this.getPageStyle();

        if (StringUtils.isEmpty(pageStyle)) {
            return Lists.newArrayList();
        }

        Map<String, Object> containerMap = JsonUtil.toMap(pageStyle, Map.class);
        Object components = containerMap.get(PageStyleConstant.COMPONENT_JSON_DATA);
        return JsonUtil.toList(JsonUtil.toJsonString(components), Object.class);
    }
}
