package com.idanchuang.resource.server.interfaces.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.dealer.member.api.model.role.response.RoleEnum;
import com.idanchuang.resource.api.common.PlatformTypeEnum;
import com.idanchuang.resource.api.request.ResourceUnitCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceUnitSearchReqDTO;
import com.idanchuang.resource.api.request.ResourceUnitUpdateReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceUnitCreateAdminReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceUnitSearchAdminReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceUnitUpdateAdminReqDTO;
import com.idanchuang.resource.api.response.ResourceUnitRespDTO;
import com.idanchuang.resource.api.response.admin.ResourceUnitResp;
import com.idanchuang.resource.server.application.service.ResourceUnitService;
import com.idanchuang.resource.server.domain.model.resource.OperatorUser;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnitQueryConditions;
import com.idanchuang.resource.server.infrastructure.transfer.ResourceUnitTransfer;
import com.idanchuang.resource.server.infrastructure.utils.DateTimeUtil;
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
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.idanchuang.resource.server.interfaces.assember.ResourceUnitDtoAssembler.dtoToEntity;
import static com.idanchuang.resource.server.interfaces.assember.ResourceUnitDtoAssembler.entityToDTO;
import static com.idanchuang.resource.server.interfaces.assember.ResourceUnitDtoAssembler.toEntity;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 14:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Api(tags = {"资源位投放单元模块"})
@RestController
@RequestMapping("/resource/admin/unit")
@Slf4j
public class ResourceUnitAdminController {

    @Resource
    private ResourceUnitService resourceUnitService;

    @Resource
    private HttpServletRequest request;

    public static ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "新建资源位投放单元")
    @PostMapping
    public JsonResult<Boolean> createResourceUnit(@Valid @RequestBody ResourceUnitCreateAdminReqDTO req) throws JsonProcessingException {
        ResourceUnitCreateReqDTO dto = new ResourceUnitCreateReqDTO();
        BeanUtils.copyProperties(req, dto);
        dto.setPlatformFrom(objectMapper.writeValueAsString(req.getPlatformFrom()));
        dto.setVisibleRoleS(req.getVisibleRoleS());
        dto.setStartTime(dateToLocalDateTime(req.getStartTime()));
        dto.setEndTime(dateToLocalDateTime(req.getEndTime()));
        dto.checkBeginAndEndTime();
        OperatorUser operatorUser = new OperatorUser();
        Assert.isTrue(operatorUser.parseRequest(request),"request解析失败");
        dto.setOperatorUser(operatorUser.getNickName());
        dto.setOperatorId(operatorUser.getId());
        dto.setCreateUser(operatorUser.getNickName());
        dto.setCreateUserId(operatorUser.getId());
        dto.checkBeginAndEndTime();
        resourceUnitService.createResourceUnit(toEntity(dto));
        return JsonResult.success(Boolean.TRUE);
    }

    @ApiOperation(value = "根据id查询资源位投放单元")
    @GetMapping("/{id}")
    public JsonResult<ResourceUnitResp> getResourceUnitById(@PathVariable Long id) throws IOException {
        ResourceUnitResp respDTO = new ResourceUnitResp();
        ResourceUnit resourceUnit = resourceUnitService.getResourceUnitById(id);
        ResourceUnitRespDTO dto = entityToDTO(resourceUnit);
        BeanUtils.copyProperties(dto, respDTO);
        respDTO.setResourceId(dto.getResourceId());
        respDTO.setPlatformFrom(Arrays.asList(objectMapper.readValue(dto.getPlatformFrom(), Integer[].class)));
        respDTO.setVisibleRoleS(dto.getVisibleRoleS());
        return JsonResult.success(respDTO);
    }

    @ApiOperation(value = "查询资源位投放单元")
    @GetMapping
    public JsonResult<PageInfo<ResourceUnitResp>> searchResourceUnit(@Valid ResourceUnitSearchAdminReqDTO req) {
        ResourceUnitSearchReqDTO dto = new ResourceUnitSearchReqDTO();
        BeanUtils.copyProperties(req, dto);
        dto.setStartTime(dateToLocalDateTime(req.getStartTime()));
        dto.setEndTime(dateToLocalDateTime(req.getEndTime()));
        PageInfo<ResourceUnitRespDTO> respDto = searchResourceUnit(dto);
        PageInfo<ResourceUnitResp> resultDto = new PageInfo<>();
        // 封装返回pageInfo
        resultDto.setPageSize(req.getPageSize());
        resultDto.setPageNum(req.getPageNum());
        resultDto.setTotal(respDto.getTotal());
        resultDto.setList(ResourceUnitTransfer.dtoToDto(respDto.getList()));
        return JsonResult.success(resultDto);
    }

    @ApiOperation(value = "更新资源位投放单元")
    @PutMapping
    public JsonResult<Boolean> updateUnit(@RequestBody ResourceUnitUpdateAdminReqDTO req) {
        ResourceUnitUpdateReqDTO dto = new ResourceUnitUpdateReqDTO();
        BeanUtils.copyProperties(req, dto);
        dto.setStartTime(DateTimeUtil.formatLocalDateTime(req.getStartTime()));
        dto.setEndTime(DateTimeUtil.formatLocalDateTime(req.getEndTime()));
        OperatorUser operatorUser = new OperatorUser();
        Assert.isTrue(operatorUser.parseRequest(request),"request解析失败");
        dto.setOperatorUser(operatorUser.getNickName());
        dto.setOperatorId(operatorUser.getId());
        ResourceUnit resourceUnit = dtoToEntity(dto);
        resourceUnitService.updateResourceUnit(resourceUnit);
        return JsonResult.success(Boolean.TRUE);
    }

    @ApiOperation(value = "获取投放角色信息")
    @GetMapping("/role")
    public JsonResult<Map<Integer, String>> getAllRoleInfo(@RequestParam("businessId")Integer businessId) {
        return JsonResult.success(Arrays.stream(RoleEnum.values())
                .collect(Collectors.toMap(RoleEnum::getCode, RoleEnum::getDesc)));
    }

    @ApiOperation(value = "获取投放平台信息")
    @GetMapping("/platform")
    public JsonResult<Map<Integer, String>> getPlatformInfo() {
        return JsonResult.success(Arrays.stream(PlatformTypeEnum.values())
                .collect(Collectors.toMap(PlatformTypeEnum::getType, PlatformTypeEnum::getDesc)));
    }

    private LocalDateTime dateToLocalDateTime(Date queryDateTime) {
        if (queryDateTime == null) {
            return null;
        }
        return queryDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private PageInfo<ResourceUnitRespDTO> searchResourceUnit(ResourceUnitSearchReqDTO req) {
        ResourceUnitQueryConditions queryConditions = new ResourceUnitQueryConditions();
        BeanUtils.copyProperties(req, queryConditions);
        PageInfo<ResourceUnit> resourceUnitPageInfo = resourceUnitService.searchResourceUnit(queryConditions);
        PageInfo<ResourceUnitRespDTO> pageInfo = new PageInfo<ResourceUnitRespDTO>();
        List<ResourceUnitRespDTO> respDTOS = entityToDTO(resourceUnitPageInfo.getList());
        pageInfo.setList(respDTOS);
        pageInfo.setPageNum(queryConditions.getPageNum());
        pageInfo.setPageSize(queryConditions.getPageSize());
        pageInfo.setTotal(resourceUnitPageInfo.getTotal());
        return pageInfo;
    }
}
