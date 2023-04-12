package com.idanchuang.cms.server.infrastructure.persistence.mapper;

import com.idanchuang.cms.server.domain.model.cms.PageTagCondition;
import com.idanchuang.cms.server.infrastructure.persistence.model.PageTagDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:41
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface PageTagMapper {

    int insert(PageTagDO pageTag);

    int deleteById(@Param("id") Serializable id);

    int updateById(PageTagDO pageTag);

    List<PageTagDO> selectByCondition(PageTagCondition condition);

    int updatePlatform(@Param("id") Serializable id);

    PageTagDO selectById(@Param("id") Serializable id);
}
