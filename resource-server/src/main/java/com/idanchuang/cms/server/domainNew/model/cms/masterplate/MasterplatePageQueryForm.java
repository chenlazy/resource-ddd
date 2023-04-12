package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 13:45
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MasterplatePageQueryForm {

    /**
     * 目录id
     */
    private CatalogueId catalogueId;

    /**
     * 模版id
     */
    private MasterplateId masterplateId;

    /**
     * 模版名称
     */
    private String masterplateName;

    /**
     * 模版状态
     */
    private MasterplateStatus status;

    /**
     * 第几页
     */
    private Long current;

    /**
     * 每页大小
     */
    private Long size;

}
