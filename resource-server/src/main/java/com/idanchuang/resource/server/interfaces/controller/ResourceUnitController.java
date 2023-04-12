package com.idanchuang.resource.server.interfaces.controller;


import com.github.pagehelper.PageInfo;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.facade.ResourceUnitFacade;
import com.idanchuang.resource.api.request.ContentPutReq;
import com.idanchuang.resource.api.request.ResourceUnitCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceUnitSearchReqDTO;
import com.idanchuang.resource.api.request.ResourceUnitUpdateReqDTO;
import com.idanchuang.resource.api.response.PutResourceDTO;
import com.idanchuang.resource.api.response.ResourceUnitRespDTO;
import com.idanchuang.resource.server.application.service.ResourceUnitService;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnitQueryConditions;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.idanchuang.resource.server.interfaces.assember.ResourceUnitDtoAssembler.*;

/**
 * @author wengbinbin
 * @date 2021/3/12
 */

@Api(tags = {"资源位投放单元模块"})
@RestController
@RequestMapping("/resource/unit")
@Slf4j
public class ResourceUnitController implements ResourceUnitFacade {
    @Resource
    private ResourceUnitService resourceUnitService;

    @Override
    public JsonResult<List<PutResourceDTO>> getContentPutInfo(@Valid ContentPutReq contentPutReq) {
        return null;
    }

    @Override
    public JsonResult<PutResourceDTO> getContentPutInfoByResourceId(Integer resourceId, String pageCode, Integer business, String platform, Integer role) {
        return null;
    }

    @Override
    public JsonResult<List<Integer>> getResourceListByPageId(String pageCode) {
        return null;
    }

    @Override
    public JsonResult<Boolean> createResourceUnit(@Valid @RequestBody ResourceUnitCreateReqDTO req) {
        req.checkBeginAndEndTime();
        resourceUnitService.createResourceUnit(toEntity(req));
        return JsonResult.success(Boolean.TRUE);
    }

    @Override
    public JsonResult<ResourceUnitRespDTO> getResourceUnitById(@PathVariable Long id) {
        ResourceUnit resourceUnit = resourceUnitService.getResourceUnitById(id);
        return JsonResult.success(entityToDTO(resourceUnit));
    }

    @Override
    public JsonResult<PageInfo<ResourceUnitRespDTO>> searchResourceUnit(@Valid ResourceUnitSearchReqDTO req) {
        ResourceUnitQueryConditions queryConditions = new ResourceUnitQueryConditions();
        BeanUtils.copyProperties(req, queryConditions);
        PageInfo<ResourceUnit> resourceUnitPageInfo = resourceUnitService.searchResourceUnit(queryConditions);
        PageInfo<ResourceUnitRespDTO> pageInfo = new PageInfo<ResourceUnitRespDTO>();
        pageInfo.setList(entityToDTO(resourceUnitPageInfo.getList()));
        pageInfo.setPageNum(queryConditions.getPageNum());
        pageInfo.setPageSize(queryConditions.getPageSize());
        pageInfo.setTotal(resourceUnitPageInfo.getTotal());
        return JsonResult.success(pageInfo);
    }

    @Override
    public JsonResult<Boolean> updateResourceUnit(@RequestBody ResourceUnitUpdateReqDTO req) {
        ResourceUnit resourceUnit = dtoToEntity(req);
        resourceUnitService.updateResourceUnit(resourceUnit);
        return JsonResult.success(Boolean.TRUE);
    }

    @Override
    public JsonResult<Long> getUnitIdByUnitNameLimitOne(String unitName) {
        return JsonResult.success(resourceUnitService.getUnitIdByUnitNameLimitOne(unitName));
    }


}
