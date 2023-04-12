package com.idanchuang.resource.server.interfaces.web.controller;

import com.github.pagehelper.PageInfo;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.request.ResourceConfigCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceConfigSearchReqDTO;
import com.idanchuang.resource.api.request.ResourceConfigUpdateReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceConfigCreateAdminReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceConfigSearchAdminReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceConfigUpdateAdminReqDTO;
import com.idanchuang.resource.api.response.ResourceConfigRespDTO;
import com.idanchuang.resource.api.response.admin.ResourceConfigResp;
import com.idanchuang.resource.server.application.service.ResourceConfigService;
import com.idanchuang.resource.server.domain.model.resource.OperatorUser;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfigQueryConditions;
import com.idanchuang.resource.server.infrastructure.transfer.ResourceConfigTransfer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.idanchuang.resource.server.interfaces.assember.ResourceConfigDtoAssembler.entityToDTO;
import static com.idanchuang.resource.server.interfaces.assember.ResourceConfigDtoAssembler.toEntity;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-15 18:10
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Api(tags = {"资源位配置模块"})
@RestController
@RequestMapping("/resource/admin/config")
@Slf4j
public class ResourceConfigAdminController {

    @Resource
    private ResourceConfigService resourceConfigService;
    @Resource
    private HttpServletRequest request;

    @ApiOperation("检查resourceNumb是否重复,返回true未重复可添加")
    @GetMapping({"/number/{id}"})
    public JsonResult<Boolean> checkResourceNumbUniq(@PathVariable("id") Integer resourceNumb, @RequestParam String pageCode) {
        return JsonResult.success(resourceConfigService.checkResourceNumbUniq(pageCode, resourceNumb, null));
    }

    @ApiOperation(value = "新建资源位配置")
    @PostMapping
    public JsonResult<Boolean> createResourceConfig(@Valid @RequestBody ResourceConfigCreateAdminReqDTO req) {
        ResourceConfigCreateReqDTO dto = new ResourceConfigCreateReqDTO();
        BeanUtils.copyProperties(req, dto);
        OperatorUser operatorUser = new OperatorUser();
        Assert.isTrue(operatorUser.parseRequest(request),"request解析失败");
        dto.setOperatorUser(operatorUser.getNickName());
        dto.setOperatorId(operatorUser.getId());
        dto.setCreateUser(operatorUser.getNickName());
        dto.setCreateUserId(operatorUser.getId());
        resourceConfigService.checkResourceNumbUniq(dto.getPageCode(), dto.getResourceNumb(), null);
        resourceConfigService.checkResourceNameUniq(dto.getPageCode(), dto.getResourceName(), null);
        resourceConfigService.createResourceConfig(toEntity(dto));
        return JsonResult.success(true);
    }

    @ApiOperation(value = "根据id查询资源位信息")
    @GetMapping("/{id}")
    public JsonResult<ResourceConfigResp> getResourceConfigById(@PathVariable Long id) {
        ResourceConfig resourceConfig = resourceConfigService.getResourceConfigById(id);
        return JsonResult.success(ResourceConfigTransfer.dto2facadeResp(entityToDTO(resourceConfig)));
    }

    @ApiOperation(value = "查询资源位信息")
    @GetMapping
    public JsonResult<PageInfo<ResourceConfigResp>> searchResourceConfig(@Valid ResourceConfigSearchAdminReqDTO req) {
        ResourceConfigSearchReqDTO dto = new ResourceConfigSearchReqDTO();
        BeanUtils.copyProperties(req, dto);
        PageInfo<ResourceConfigRespDTO> respDto = searchResourceConfig(dto);
        PageInfo<ResourceConfigResp> resultDto = new PageInfo<>();
        // 封装返回pageInfo
        resultDto.setPageSize(req.getPageSize());
        resultDto.setPageNum(req.getPageNum());
        resultDto.setTotal(respDto.getTotal());
        resultDto.setList(ResourceConfigTransfer.dtoToDto(respDto.getList()));
        return JsonResult.success(resultDto);
    }

    @ApiOperation(value = "更新资源位信息")
    @PutMapping
    public JsonResult<Boolean> updateResourceConfig( @RequestBody ResourceConfigUpdateAdminReqDTO req) {
        ResourceConfigUpdateReqDTO dto = new ResourceConfigUpdateReqDTO();
        BeanUtils.copyProperties(req, dto);
        OperatorUser operatorUser = new OperatorUser();
        Assert.isTrue(operatorUser.parseRequest(request),"request解析失败");
        dto.setOperatorUser(operatorUser.getNickName());
        dto.setOperatorId(operatorUser.getId());
        resourceConfigService.checkResourceNumbUniq(dto.getPageCode(), dto.getResourceNumb(),dto.getResourceId());
        resourceConfigService.checkResourceNameUniq(dto.getPageCode(), dto.getResourceName(), dto.getResourceId());
        resourceConfigService.updateResourceConfig(toEntity(dto));
        return JsonResult.success(Boolean.TRUE);
    }

    private PageInfo<ResourceConfigRespDTO> searchResourceConfig(ResourceConfigSearchReqDTO req) {
        ResourceConfigQueryConditions queryConditions = new ResourceConfigQueryConditions();
        BeanUtils.copyProperties(req, queryConditions);
        PageInfo<ResourceConfig> resourceConfigPageInfo = resourceConfigService.searchResourceConfig(queryConditions);
        PageInfo<ResourceConfigRespDTO> pageInfo = new PageInfo<ResourceConfigRespDTO>();
        List<ResourceConfigRespDTO> respDTOS = entityToDTO(resourceConfigPageInfo.getList());
        pageInfo.setPageSize(queryConditions.getPageSize());
        pageInfo.setPageNum(queryConditions.getPageNum());
        pageInfo.setTotal(resourceConfigPageInfo.getTotal());
        pageInfo.setList(respDTOS);
        return pageInfo;
    }
}
