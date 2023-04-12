package com.idanchuang.cms.server.domainNew.model.cms.catalogue;

import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 10:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CataloguePageQueryForm {

    /**
     * 是否过滤老数据，0否；1是，默认是0
     */
    private int old = 0;

    /**
     * 目录id
     */
    private CatalogueId catalogueId;

    /**
     * 平台类型
     */
    private List<PlatformCode> platformCodes;

    /**
     * 页面id
     */
    private ClientPageId clientPageId;

    /**
     * 页面类型列表
     */
    private List<ClientPageId> clientPageIdList;

    /**
     * 目录名称
     */
    private String catalogueName;

    /**
     * 别名
     */
    private String aliasTitle;

    /**
     * 第几页
     */
    private Long current;

    /**
     * 每页大小
     */
    private Long size;

}
