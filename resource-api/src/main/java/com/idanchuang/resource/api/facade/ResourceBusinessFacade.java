package com.idanchuang.resource.api.facade;

import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.response.ResourceBusinessRespDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wengbinbin
 * @date 2021/3/18
 */

@FeignClient(value = "DC-RESOURCE", path = "/resource/business")
@Api(value = "获取资源位业务页面信息", tags = {"获取资源位业务页面信息"})
public interface ResourceBusinessFacade {

    @ApiOperation(value = "查询资源位业务页面信息")
    @GetMapping
    JsonResult<List<ResourceBusinessRespDTO>> getResourceBusiness();

    @ApiOperation(value = "查询文章")
    @GetMapping("/getArticleById")
    JsonResult<Object> getArticleById(@RequestParam("id") Long id);
}
