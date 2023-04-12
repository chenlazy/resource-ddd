package com.idanchuang.resource.server.interfaces.web.controller;

import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.response.ResourceBusinessRespDTO;
import com.idanchuang.resource.server.application.service.ResourceBusinessService;
import com.idanchuang.resource.server.application.service.ResourcePageService;
import com.idanchuang.resource.server.domain.model.resource.ResourceBusiness;
import com.idanchuang.resource.server.domain.model.resource.ResourcePage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

import static com.idanchuang.resource.server.interfaces.assember.ResourceBusinessDtoAssembler.entityToDTO;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-15 18:09
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Api(tags = {"资源位业务页面模块"})
@RestController
@RequestMapping("/resource/admin/business")
@Slf4j
public class ResourceBusinessAdminController {

    @Resource
    private ResourceBusinessService resourceBusinessService;
    @Resource
    private ResourcePageService resourcePageService;

    @ApiOperation(value = "查询资源位业务页面信息")
    @GetMapping
    public JsonResult<List<ResourceBusinessRespDTO>> getResourceBusiness() {
        List<ResourceBusiness> resourceBusinessList = resourceBusinessService.getResourceBusiness();
        List<ResourceBusinessRespDTO> respDTOS = new LinkedList<>();
        resourceBusinessList.forEach(resourceBusiness -> {
            List<ResourcePage> pages = resourcePageService.getResourcePageByBusinessId(resourceBusiness.getId());
            ResourceBusinessRespDTO dto = entityToDTO(resourceBusiness, pages);
            respDTOS.add(dto);
        });
        return JsonResult.success(respDTOS);
    }
}
