package com.idanchuang.resource.server.infrastructure.repository;

import com.idanchuang.resource.server.domain.repository.ArticleRepository;
import com.idanchuang.resource.server.infrastructure.persistence.abmau.mapper.DemoMapper;
import com.idanchuang.resource.server.infrastructure.persistence.model.ArticleSubjectDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-29 17:56
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    @Resource
    private DemoMapper demoMapper;

    @Override
    public ArticleSubjectDO getArticleById(Long id) {
        return demoMapper.queryBySubjectId(id);
    }
}
