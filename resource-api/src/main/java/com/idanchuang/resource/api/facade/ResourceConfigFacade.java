package com.idanchuang.resource.api.facade;

import com.github.pagehelper.PageInfo;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.request.ResourceConfigCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceConfigSearchReqDTO;
import com.idanchuang.resource.api.request.ResourceConfigUpdateReqDTO;
import com.idanchuang.resource.api.response.ResourceConfigRespDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by develop at 2021/2/4.
 *
 * @author wuai
 */
@FeignClient(value = "DC-RESOURCE", path = "/resource/config")
@Api(value = "获取资源位内容投放", tags = {"获取资源位内容投放"})
public interface ResourceConfigFacade {

    @ApiOperation(value = "检查resourceNumb是否重复,返回true未重复可添加")
    @GetMapping("/number/{id}")
    JsonResult<Boolean> checkResourceNumbUniq(@PathVariable("id") Integer resourceNumb, String pageCode);

    @ApiOperation(value = "新建资源位配置")
    @PostMapping
    JsonResult<Boolean> createResourceConfig(@Valid @RequestBody ResourceConfigCreateReqDTO req);

    @ApiOperation(value = "根据id查询资源位信息")
    @GetMapping("/{id}")
    JsonResult<ResourceConfigRespDTO> getResourceConfigById(@PathVariable("id") Long id);

    @ApiOperation(value = "查询资源位信息")
    @GetMapping
    JsonResult<PageInfo<ResourceConfigRespDTO>> searchResourceConfig(@Valid @SpringQueryMap ResourceConfigSearchReqDTO req);

    @ApiOperation(value = "更新资源位信息")
    @PutMapping
    JsonResult<Boolean> updateResourceConfig(@RequestBody ResourceConfigUpdateReqDTO req);

    /**
     * 根据资源位名获取一个资源位id
     *
     * @param resourceName
     * @return
     */
    @GetMapping("/getResourceIdByNameLimitOne")
    @ApiOperation(value = "/getResourceIdByNameLimitOne", notes = "根据资源位名获取一个资源位id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Long> getResourceIdByNameLimitOne(@RequestParam("resourceName") String resourceName);
}
