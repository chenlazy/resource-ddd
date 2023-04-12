package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.response.PageRenderDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 页面渲染
 * @author lei.liu
 * @date 2021/9/13
 */
@FeignClient(value = "DC-RESOURCE", path = "/cms/page/render")
@Api(value = "页面渲染服务", tags = {"页面渲染服务"})
public interface PageRenderFacade {

    @GetMapping("/getInfoById")
    JsonResult<PageRenderDTO> getInfoById(@RequestParam("id") Integer id);

    @GetMapping("/getInfoByIdAliasTitle")
    JsonResult<PageRenderDTO> getInfoByIdAliasTitle(@RequestParam("id") String id);

    /**
     * 根据页面定义ID，获取所有页面实例
     * @param id    页面定义ID
     * @return  页面实例列表
     */
    @GetMapping("/getInfoList")
    JsonResult<List<PageRenderDTO>> getInfoList(@RequestParam("id") Integer id);

    @GetMapping("/deleteAllPageVersion")
    JsonResult<Void> deleteAllPageVersion();

    @GetMapping("/getCachePageVersion")
    JsonResult<PageRenderDTO> getCachePageVersion(@RequestParam("pageId") Integer pageId);

    @GetMapping("/buildAllPageVersion")
    JsonResult<Void> buildAllPageVersion();

    @GetMapping("/buildPageVersion")
    JsonResult<Void> buildPageVersion(@RequestParam("id") Integer id);

}
