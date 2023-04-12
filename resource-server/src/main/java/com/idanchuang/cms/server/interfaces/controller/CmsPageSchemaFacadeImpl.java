package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.api.facade.CmsPageSchemaFacade;
import com.idanchuang.cms.api.request.CmsPageSchemaCreateReq;
import com.idanchuang.cms.api.request.CmsPageSchemaUpdateReq;
import com.idanchuang.cms.api.response.CmsPageSchemaDTO;
import com.idanchuang.cms.server.application.service.CmsPageSchemaService;
import com.idanchuang.cms.server.domain.model.cms.CmsPageSchema;
import com.idanchuang.cms.server.interfaces.assember.CmsPageSchemaDtoAssembler;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 16:49
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/pageSchema")
@Slf4j
public class CmsPageSchemaFacadeImpl implements CmsPageSchemaFacade {

    @Resource
    private CmsPageSchemaService pageSchemaService;

    @Override
    public JsonResult<Integer> createPageSchema(@Valid CmsPageSchemaCreateReq createReq) {
        Integer pageSchema = pageSchemaService.createPageSchema(CmsPageSchemaDtoAssembler.toEntity(createReq));
        return JsonResult.success(pageSchema);
    }

    @Override
    public JsonResult<Boolean> batchCreateSchema(List<CmsPageSchemaCreateReq> createReqs) {
        List<CmsPageSchema> pageSchemas = createReqs.stream().map(CmsPageSchemaDtoAssembler::toEntity).collect(Collectors.toList());
        Boolean pageSchema = pageSchemaService.batchCreatePageSchema(pageSchemas);
        return JsonResult.success(pageSchema);
    }

    @Override
    public JsonResult<CmsPageSchemaDTO> getPageSchema(Integer pageSchemaId) {
        CmsPageSchema pageSchema = pageSchemaService.getPageSchema(pageSchemaId);
        return JsonResult.success(CmsPageSchemaDtoAssembler.entityToDTO(pageSchema));
    }

    @Override
    public JsonResult<Boolean> updatePageSchema(@Valid CmsPageSchemaUpdateReq updateReq) {
        Boolean updatePageSchema = pageSchemaService.updatePageSchema(CmsPageSchemaDtoAssembler.toEntity(updateReq));
        return JsonResult.success(updatePageSchema);
    }
}
