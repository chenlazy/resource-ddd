package com.idanchuang.resource.server.domain.repository;

import com.idanchuang.resource.server.infrastructure.persistence.model.ArticleSubjectDO;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-29 17:55
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface ArticleRepository {

    ArticleSubjectDO getArticleById(Long id);
}
