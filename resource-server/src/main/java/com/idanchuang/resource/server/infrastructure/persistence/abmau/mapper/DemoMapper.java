package com.idanchuang.resource.server.infrastructure.persistence.abmau.mapper;

import com.idanchuang.resource.server.infrastructure.persistence.model.ArticleSubjectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-29 17:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface DemoMapper {

    ArticleSubjectDO queryBySubjectId(@Param("id") Long id);
}
