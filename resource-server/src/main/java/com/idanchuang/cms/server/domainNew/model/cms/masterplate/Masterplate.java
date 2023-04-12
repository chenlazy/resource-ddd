package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.shard.CreateId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 10:41
 * @Desc: 模版领域类
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class Masterplate {

    /**
     * 模版id
     */
    @NonFinal
    private MasterplateId id;

    /**
     * 目录id (page_schema_id)
     */
    private CatalogueId catalogueId;

    /**
     * app端标题(page_name)
     */
    private String appTitle;

    /**
     * 平台类型
     */
    private PlatformCode platform;

    /**
     * 页面别名
     */
    private String aliasTitle;

    /**
     * 模版名称（back_end_title）
     */
    private String masterplateName;

    /**
     * 目录类型（page_type）
     */
    private CatalogueType catalogueType;

    /**
     * 页面id(tag_id)
     */
    private ClientPageId pageId;

    /**
     * 模版状态（status）
     */
    private MasterplateStatus status;

    /**
     * 是否分享:1-是 0-否（share_flag）
     */
    private ShareFlag shareFlag;

    /**
     * 版本号
     */
    @NonFinal
    private Integer version;

    /**
     * 分享内容(share_json)
     */
    private List<MasterplateShareForm> shareForms;

    /**
     * 模版开始时间(start_time)
     */
    private LocalDateTime startTime;

    /**
     * 模版结束时间(end_time)
     */
    private LocalDateTime endTime;

    /**
     * 模版页面信息(page_style)
     */
    private String pageStyle;

    /**
     * 额外信息
     */
    private MasterplateExtra extra;

    /**
     * 操作人id（operator_id）
     */
    private OperatorId operatorId;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 快照标识
     */
    @NonFinal
    private Long snapRoot;

    /**
     * 创建人id
     */
    private CreateId createId;

    public void setId(MasterplateId id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 转化为快照
     */
    public void transferSnapshot() {
        this.snapRoot = this.id.getValue();
    }
}
