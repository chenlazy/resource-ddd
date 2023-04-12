package com.idanchuang.cms.server.application.service;

import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.cms.server.domain.repository.CmsPageContainerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 14:13
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class CmsPageContainerService {

    @Resource
    private CmsPageContainerRepository containerRepository;

    public Boolean createPageContainer(CmsPageContainer cmsPageContainer) {
        return containerRepository.createPageContainer(cmsPageContainer);
    }

    public Boolean batchCreatePageContainer(List<CmsPageContainer> cmsPageContainers) {
        return containerRepository.batchCreatePageContainer(cmsPageContainers);
    }

    public List<CmsPageContainer> queryPageContainer(Integer pageId) {
        return containerRepository.queryPageContainer(pageId);
    }

    public Boolean updateContainerPage(Integer pageId, Integer operatorId, List<Long> containerIds) {
        return containerRepository.updateContainerPage(pageId, operatorId, containerIds);
    }

    public CmsPageContainer getById(Long id) {
        return containerRepository.getById(id);
    }
}
