package com.idanchuang.cms.server.application.service;

import com.idanchuang.cms.server.domain.model.cms.CmsCorePageList;
import com.idanchuang.cms.server.domain.model.cms.CmsPageDetail;
import com.idanchuang.cms.server.domain.model.cms.CmsPageSchema;
import com.idanchuang.cms.server.domain.model.cms.CmsPageSchemaList;
import com.idanchuang.cms.server.domain.repository.CmsPageSchemaRepository;
import com.idanchuang.component.base.page.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 14:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class CmsPageSchemaService {

    @Resource
    private CmsPageSchemaRepository pageSchemaRepository;

    public Integer createPageSchema(CmsPageSchema cmsPageSchema) {
        return pageSchemaRepository.savePageSchema(cmsPageSchema);
    }

    public Boolean batchCreatePageSchema(List<CmsPageSchema> cmsPageSchemaList) {
        return pageSchemaRepository.batchSavePageSchema(cmsPageSchemaList) > 0;
    }

    public CmsPageSchema getPageSchema(Integer pageSchemaId) {
        return pageSchemaRepository.getPageSchema(pageSchemaId);
    }

    public Boolean updatePageSchema(CmsPageSchema cmsPageSchema) {
        return pageSchemaRepository.updatePageSchema(cmsPageSchema) > 0;
    }

    public PageData<CmsPageSchemaList> queryPageSchemaList(CmsCorePageList cmsCorePageList) {
        return pageSchemaRepository.queryPageSchemaList(cmsCorePageList);
    }

    public Boolean removePageSchema(Integer id, Long operator) {
        return pageSchemaRepository.removePageSchema(id, operator);
    }

    public CmsPageDetail getDetailById(Integer id, Integer templateId) {
        return pageSchemaRepository.getDetailById(id, templateId);
    }




}
