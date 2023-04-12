package com.idanchuang.resource.api.facade;

import com.github.pagehelper.PageInfo;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.request.ContentPutReq;
import com.idanchuang.resource.api.request.ResourceUnitCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceUnitSearchReqDTO;
import com.idanchuang.resource.api.request.ResourceUnitUpdateReqDTO;
import com.idanchuang.resource.api.response.PutResourceDTO;
import com.idanchuang.resource.api.response.ResourceUnitRespDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by develop at 2021/2/4.
 *
 * @author wuai
 */
@FeignClient(value = "DC-RESOURCE", path = "/resource/unit")
@Api(value = "获取资源位内容投放", tags = {"获取资源位内容投放"})
public interface ResourceUnitFacade {

    /**
     * 获取资源位投放信息
     *
     * @param contentPutReq
     * @return
     */
    @GetMapping("/getContentPutInfo")
    @ApiOperation(value = "/getContentPutInfo", notes = "获取资源位投放信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<List<PutResourceDTO>> getContentPutInfo(@RequestBody @Valid ContentPutReq contentPutReq);

    /**
     * 根据资源id获取投放内容
     *
     * @param resourceId 资源位id
     * @param pageCode   页面id
     * @param business   业务渠道
     * @param platform   业务平台
     * @param role       用户角色
     * @return
     */
    @GetMapping("/getContentPutInfoByResourceId")
    @ApiOperation(value = "/getContentPutInfoByResourceId", notes = "根据资源id获取投放内容", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<PutResourceDTO> getContentPutInfoByResourceId(@RequestParam("resourceId") Integer resourceId, @RequestParam("pageCode") String pageCode, @RequestParam("business") Integer business, @RequestParam("platform") String platform, @RequestParam("role") Integer role);

    /**
     * 根据页面id获取资源位id列表
     *
     * @param pageCode
     * @return
     */
    @GetMapping("/getResourceListByPageId")
    @ApiOperation(value = "/getResourceListByPageId", notes = "根据页面id获取资源位id列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<List<Integer>> getResourceListByPageId(@RequestParam("pageCode") String pageCode);

    @ApiOperation(value = "新建资源位投放单元")
    @PostMapping
    JsonResult<Boolean> createResourceUnit(@Valid @RequestBody ResourceUnitCreateReqDTO req);

    @ApiOperation(value = "根据id查询资源位投放单元")
    @GetMapping("/{id}")
    JsonResult<ResourceUnitRespDTO> getResourceUnitById(@PathVariable(value = "id") Long id);

    // @SpringQueryMap
    @ApiOperation(value = "查询资源位投放单元")
    @PostMapping("/search")
    JsonResult<PageInfo<ResourceUnitRespDTO>> searchResourceUnit(@Valid @RequestBody ResourceUnitSearchReqDTO req);

    @ApiOperation(value = "更新资源位投放单元")
    @PutMapping
    JsonResult<Boolean> updateResourceUnit(@RequestBody ResourceUnitUpdateReqDTO req);

    /**
     * 根据投放内容名获取一个投放id
     * @param unitName
     * @return
     */
    @GetMapping("/getUnitIdByUnitNameLimitOne")
    @ApiOperation(value = "/getUnitIdByUnitNameLimitOne", notes = "根据投放内容名获取一个投放id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Long> getUnitIdByUnitNameLimitOne(@RequestParam("unitName") String unitName);
}
