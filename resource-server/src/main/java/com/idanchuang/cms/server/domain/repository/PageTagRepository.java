package com.idanchuang.cms.server.domain.repository;

import com.idanchuang.cms.server.domain.model.cms.PageTag;
import com.idanchuang.cms.server.domain.model.cms.PageTagCondition;
import com.idanchuang.component.base.page.PageData;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:30
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface PageTagRepository {

    boolean insert(PageTag pageTag);

    boolean deleteById(Integer id);

    boolean updateById(PageTag pageTag);

    PageData<PageTag> pageByCondition(PageTagCondition condition);

    List<PageTag> selectList(PageTagCondition condition);

    boolean updatePlatform(Integer id);

    PageTag selectById(Integer id);
}
