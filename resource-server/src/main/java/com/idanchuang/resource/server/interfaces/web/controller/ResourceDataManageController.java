package com.idanchuang.resource.server.interfaces.web.controller;

import com.idanchuang.resource.api.request.admin.ResourceDataPageReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceDataReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceDataUnitPageReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceDataUnitReqDTO;
import com.idanchuang.resource.api.response.admin.ResourceClickIncidentRespDTO;
import com.idanchuang.resource.api.response.admin.ResourceDataManagePageRespDTO;
import com.idanchuang.resource.api.response.admin.ResourceDataManageRespDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.application.service.ResourceDataManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 14:08
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Api(tags = "资源位内容投放数据管理")
@Slf4j
@RestController
@RequestMapping(value = "/content/put/data")
public class ResourceDataManageController {

    @Resource
    private ResourceDataManageService resourceDataManageService;

    @PostMapping(value = "/getResourceDataManageInfo")
    @ApiOperation(value = "获取内容投放点击图表数据", notes = "getResourceDataManageInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<ResourceDataManageRespDTO> getResourceDataManageInfo(@RequestBody @Valid ResourceDataUnitReqDTO reqDTO) {
        return resourceDataManageService.getResourceDataManageInfo(reqDTO);
    }

    @PostMapping(value = "/getResourceDataManagePageRespList")
    @ApiOperation(value = "获取内容投放点击数据明细", notes = "getResourceDataManagePageRespList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<PageData<ResourceDataManagePageRespDTO>> getResourceDataManagePageRespList(@RequestBody @Valid ResourceDataUnitPageReqDTO reqDTO) {
        return resourceDataManageService.getResourceDataManagePageRespList(reqDTO);
    }

    @PostMapping(value = "/getResourceData")
    @ApiOperation(value = "获取资源位点击图表数据", notes = "getResourceData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<ResourceDataManageRespDTO> getResourceData(@RequestBody @Valid ResourceDataReqDTO reqDTO) {
        return resourceDataManageService.getResourceData(reqDTO);
    }

    @PostMapping(value = "/getResourceDataPageRespList")
    @ApiOperation(value = "获取资源位点击数据明细", notes = "getResourceDataPageRespList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<PageData<ResourceDataManagePageRespDTO>> getResourceDataPageRespList(@RequestBody @Valid ResourceDataPageReqDTO reqDTO) {
        return resourceDataManageService.getResourceDataPageRespList(reqDTO);
    }

    @GetMapping(value = "/getClickIncidentList")
    @ApiOperation(value = "根据内容投放id获取点击事件列表", notes = "getClickIncidentList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<ResourceClickIncidentRespDTO> getClickIncidentList(@RequestParam("unitId") Long unitId) {
        return resourceDataManageService.getClickIncidentList(unitId);
    }
}
