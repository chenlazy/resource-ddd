package com.idanchuang.cms.server.domain.model.cms.factory;

import com.idanchuang.cms.server.application.enums.PageTypeEnum;
import com.idanchuang.cms.server.domain.model.cms.CmsPageSchema;
import com.idanchuang.cms.server.domain.model.cms.SchemaExtra;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaCode;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-11 19:05
 * @Desc: 页面定义工厂类
 * @Copyright VTN Limited. All rights reserved.
 */
public class PageSchemaFactory {

    private PageSchemaFactory() {

    }

    public static CmsPageSchema createPageSchema(Integer id, SchemaCode schemaCode, Integer operatorId, Long tagId,
                                                 Integer status, String putVersions, String pageName, LocalDateTime createTime,
                                                 LocalDateTime updateTime, SchemaExtra schemaExtra) {

        return new CmsPageSchema(id, schemaCode, pageName, PageTypeEnum.PAGE_TYPE_SITUATION.getType(), status,
                putVersions, operatorId, createTime, updateTime, 0, tagId, schemaExtra);
    }


}
