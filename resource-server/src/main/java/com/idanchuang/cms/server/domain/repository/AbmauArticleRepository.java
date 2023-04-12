package com.idanchuang.cms.server.domain.repository;

import com.idanchuang.resource.server.infrastructure.persistence.model.ArticleSubjectDO;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-30 09:38
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface AbmauArticleRepository {

    ArticleSubjectDO getArticleById(Long id);
}
