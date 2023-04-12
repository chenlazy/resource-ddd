package com.idanchuang.cms.server.domain.repository;

import com.idanchuang.cms.server.domain.model.cms.CmsCorePageList;
import com.idanchuang.cms.server.domain.model.cms.CmsPageDetail;
import com.idanchuang.cms.server.domain.model.cms.CmsPageSchema;
import com.idanchuang.cms.server.domain.model.cms.CmsPageSchemaList;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaCode;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaId;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageSchemaListDO;
import com.idanchuang.component.base.page.PageData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:29
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface CmsPageSchemaRepository {

    int savePageSchema(CmsPageSchema cmsPageSchema);

    int batchSavePageSchema(List<CmsPageSchema> cmsPageSchemaList);

    CmsPageSchema getPageSchema(Integer pageSchemaId);


    /**
     * 根据定义code换取定义id
     * @param schemaCode
     * @return
     */
    SchemaId getPageSchemaIdByCode(SchemaCode schemaCode);


    int updatePageSchema(CmsPageSchema cmsPageSchema);

    PageData<CmsPageSchemaList> queryPageSchemaList(CmsCorePageList cmsCorePageList);

    Boolean removePageSchema(Integer id, Long operator);

    CmsPageDetail getDetailById(Integer id, Integer templateId);

    List<CmsPageSchemaListDO> queryByCondition(CmsCorePageList condition);
}
