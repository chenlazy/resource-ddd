package com.idanchuang.cms.server.interfaces.controller;

import com.google.common.collect.Lists;
import com.idanchuang.cms.api.facade.CmsPageContainerFacade;
import com.idanchuang.cms.api.request.CmsPageContainerCreateReq;
import com.idanchuang.cms.api.response.CmsPageContainerDTO;
import com.idanchuang.cms.server.application.service.CmsPageContainerService;
import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.cms.server.interfaces.assember.CmsContainerDtoAssembler;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
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
@RequestMapping("/cms/container")
@Slf4j
public class CmsPageContainerFacadeImpl implements CmsPageContainerFacade {

    @Resource
    private CmsPageContainerService cmsPageContainerService;

    @Override
    public JsonResult<Boolean> createPageContainer(@Valid CmsPageContainerCreateReq containerCreateReq) {
        Boolean pageContainer = cmsPageContainerService.createPageContainer(CmsContainerDtoAssembler.toEntity(containerCreateReq));
        return JsonResult.success(pageContainer);
    }

    @Override
    public JsonResult<Boolean> batchCreateContainer(List<CmsPageContainerCreateReq> containerCreateReqs) {
        List<CmsPageContainer> pageContainers = containerCreateReqs.stream().map(CmsContainerDtoAssembler::toEntity).collect(Collectors.toList());
        Boolean pageContainer = cmsPageContainerService.batchCreatePageContainer(pageContainers);
        return JsonResult.success(pageContainer);
    }

    @Override
    public JsonResult<List<CmsPageContainerDTO>> queryPageContainer(Integer pageId) {
        if (null == pageId) {
            return JsonResult.success(Lists.newArrayList());
        }
        List<CmsPageContainer> cmsPageContainers = cmsPageContainerService.queryPageContainer(pageId);

        if (CollectionUtils.isEmpty(cmsPageContainers)) {
            return JsonResult.success(Lists.newArrayList());
        }
        List<CmsPageContainerDTO> containerDTOS = cmsPageContainers.stream().map(CmsContainerDtoAssembler::entityToDTO).collect(Collectors.toList());
        return JsonResult.success(containerDTOS);
    }

    @Override
    public JsonResult<Boolean> updateContainerPage(Integer pageId, List<Long> containerIds) {
        Boolean containerPage = cmsPageContainerService.updateContainerPage(pageId, 0, containerIds);
        return JsonResult.success(containerPage);
    }
}
