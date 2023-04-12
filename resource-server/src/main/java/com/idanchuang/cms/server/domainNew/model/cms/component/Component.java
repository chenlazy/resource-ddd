package com.idanchuang.cms.server.domainNew.model.cms.component;

import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import lombok.Value;
import lombok.experimental.NonFinal;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 10:41
 * @Desc: 组件领域类
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class Component {

    /**
     * 组件id
     */
    @NonFinal
    private ComponentId id;

    /**
     * 容器id（component_id）
     */
    private ContainerId containerId;

    /**
     * 页面code(page_code)
     */
    private PageCode pageCode;

    /**
     * 模版id（page_id）
     */
    private MasterplateId masterplateId;

    /**
     * 组件类型（component_type）
     */
    private ComponentType componentType;

    /**
     * 业务类型（model_type）
     */
    private ComponentBusinessType componentBusinessType;

    /**
     * 业务数据(biz_json)
     */
    private String bizJson;

    /**
     * 操作人id（operator_id）
     */
    private OperatorId operatorId;

    /**
     * 快照标识
     */
    @NonFinal
    private Long snapRoot;

    public void setId(ComponentId id) {
        this.id = id;
    }

    /**
     * 转为快照版本
     */
    public void transferSnapshot() {
        this.snapRoot = -1L;
    }
}
