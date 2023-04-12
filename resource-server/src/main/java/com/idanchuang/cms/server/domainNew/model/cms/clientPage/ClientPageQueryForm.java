package com.idanchuang.cms.server.domainNew.model.cms.clientPage;

import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-15 10:30
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClientPageQueryForm {

    /**
     * 页码
     */
    private int pageNum = 1;

    /**
     * 每页大小
     */
    private int pageSize = 10;

    /**
     * 平台类型
     */
    private PlatformCode platformCode;

    /**
     * 页面code
     */
    private PageCode pageCode;

    /**
     * 页面id列表
     */
    private List<ClientPageId> ids;
}
