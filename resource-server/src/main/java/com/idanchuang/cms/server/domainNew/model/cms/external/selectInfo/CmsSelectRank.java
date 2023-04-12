package com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo;

import lombok.Data;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午1:58
 */
@Data
public class CmsSelectRank {

    /**
     * 页面定义id
     */
    private Long pageSchemaId;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面别名 例如 VTN_123
     */
    private String aliasTitle;

    /**
     * 页面模版id
     */
    private Long pageId;

    /**
     * 模版创建人id
     */
    private Long createId;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 模版最后更新人id
     */
    private Long operatorId;

    /**
     * 更新人姓名
     */
    private String operatorName;

}
