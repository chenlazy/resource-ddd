package com.idanchuang.resource.server.interfaces.controller;


import com.github.pagehelper.PageInfo;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.facade.ResourceConfigFacade;
import com.idanchuang.resource.api.request.ResourceConfigCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceConfigSearchReqDTO;
import com.idanchuang.resource.api.request.ResourceConfigUpdateReqDTO;
import com.idanchuang.resource.api.response.ResourceConfigRespDTO;
import com.idanchuang.resource.server.application.service.ResourceConfigService;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfigQueryConditions;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.idanchuang.resource.server.interfaces.assember.ResourceConfigDtoAssembler.entityToDTO;
import static com.idanchuang.resource.server.interfaces.assember.ResourceConfigDtoAssembler.toEntity;

/**
 * @author wengbinbin
 * @date 2021/3/12
 */
@Api(tags = {"资源位配置模块"})
@RestController
@RequestMapping("/resource/config")
@Slf4j
public class ResourceConfigController implements ResourceConfigFacade {
    @Resource
    private ResourceConfigService resourceConfigService;

    @Override
    public JsonResult<Boolean> checkResourceNumbUniq(@PathVariable("id") Integer resourceNumb, String pageCode) {
        return JsonResult.success(resourceConfigService.checkResourceNumbUniq(pageCode, resourceNumb, null));
    }

    @Override
    public JsonResult<Boolean> createResourceConfig(@Valid @RequestBody ResourceConfigCreateReqDTO req) {
        resourceConfigService.checkResourceNumbUniq(req.getPageCode(), req.getResourceNumb(), null);
        resourceConfigService.checkResourceNameUniq(req.getPageCode(), req.getResourceName(), null);
        resourceConfigService.createResourceConfig(toEntity(req));
        return JsonResult.success(true);
    }

    @Override
    public JsonResult<ResourceConfigRespDTO> getResourceConfigById(@PathVariable Long id) {
        ResourceConfig resourceConfig = resourceConfigService.getResourceConfigById(id);
        return JsonResult.success(entityToDTO(resourceConfig));
    }

    @Override
    public JsonResult<PageInfo<ResourceConfigRespDTO>> searchResourceConfig(@Valid ResourceConfigSearchReqDTO req) {
        ResourceConfigQueryConditions queryConditions = new ResourceConfigQueryConditions();
        BeanUtils.copyProperties(req, queryConditions);
        PageInfo<ResourceConfig> resourceConfigPageInfo = resourceConfigService.searchResourceConfig(queryConditions);
        PageInfo<ResourceConfigRespDTO> pageInfo = new PageInfo<ResourceConfigRespDTO>();
        pageInfo.setList(entityToDTO(resourceConfigPageInfo.getList()));
        pageInfo.setPageNum(queryConditions.getPageNum());
        pageInfo.setPageSize(queryConditions.getPageSize());
        pageInfo.setTotal(resourceConfigPageInfo.getTotal());
        return JsonResult.success(pageInfo);
    }

    @Override
    public JsonResult<Boolean> updateResourceConfig( @RequestBody ResourceConfigUpdateReqDTO req) {
        resourceConfigService.checkResourceNumbUniq(req.getPageCode(), req.getResourceNumb(),req.getResourceId());
        resourceConfigService.checkResourceNameUniq(req.getPageCode(), req.getResourceName(), req.getResourceId());
        resourceConfigService.updateResourceConfig(toEntity(req));
        return JsonResult.success(Boolean.TRUE);
    }

    @Override
    public JsonResult<Long> getResourceIdByNameLimitOne(String resourceName) {
        return JsonResult.success(resourceConfigService.getResourceIdByNameLimitOne(resourceName));
    }
}
