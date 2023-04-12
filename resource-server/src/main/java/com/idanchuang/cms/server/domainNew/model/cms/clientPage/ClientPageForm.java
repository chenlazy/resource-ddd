package com.idanchuang.cms.server.domainNew.model.cms.clientPage;

import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 10:38
 * @Desc: 页面领域类
 * @Copyright VTN Limited. All rights reserved.
 */
@Builder
@Getter
public class ClientPageForm {

    /**
     * 页面id
     */
    private ClientPageId id;

    /**
     * 页面名称
     */
    private String name;


    /**
     * 平台类型，0：VTN；1：ABM
     */
    private PlatformCode platform;

    /**
     * 页面code
     */
    private PageCode pageCode;

    /**
     * 操作人id
     */
    private OperatorId operatorId;
}
