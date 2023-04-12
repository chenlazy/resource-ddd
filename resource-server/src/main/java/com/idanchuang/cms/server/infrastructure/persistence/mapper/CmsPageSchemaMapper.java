package com.idanchuang.cms.server.infrastructure.persistence.mapper;

import com.idanchuang.cms.server.domain.model.cms.CmsCorePageList;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageDetailDO;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageSchemaDO;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageSchemaListDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:40
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface CmsPageSchemaMapper {

    int insert(CmsPageSchemaDO cmsPageSchemaDO);

    int insertBatch(@Param("schemas") List<CmsPageSchemaDO> cmsPageSchemaDOS);

    CmsPageSchemaDO selectById(@Param("pageSchemaId")Integer pageSchemaId);

    //根据code换取pageId
    Long selectSchemaIdByCode(@Param("schemaCode")String schemaCode);

    int updateById(CmsPageSchemaDO cmsPageSchemaDO);

    List<CmsPageSchemaListDO> queryByCondition(@Param("condition")CmsCorePageList condition);

    boolean removePageSchema(@Param("id") Integer id, @Param("operatorId") Long operatorId);

    CmsPageDetailDO getDetailById(@Param("id") Integer id);

}
