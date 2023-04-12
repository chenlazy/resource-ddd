package com.idanchuang.cms.server.infrastructure.repository;

import com.idanchuang.cms.server.domain.repository.AbmauArticleRepository;
import com.idanchuang.cms.server.infrastructure.persistence.abmau.mapper.AbmauDemoMapper;
import com.idanchuang.resource.server.infrastructure.persistence.model.ArticleSubjectDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-30 09:37
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class AbmauArticleRepositoryImpl implements AbmauArticleRepository {

    @Resource
    private AbmauDemoMapper abmauDemoMapper;

    @Override
    public ArticleSubjectDO getArticleById(Long id) {
        return abmauDemoMapper.queryBySubjectId(id);
    }
}
