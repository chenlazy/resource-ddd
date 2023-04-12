package com.idanchuang.cms.server.interfaces.web.controller;

import com.idanchuang.cms.server.applicationNew.service.PageRenderService;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRenderRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.interfaces.assember.InterfacePageRenderAssembler;
import com.idanchuang.cms.server.interfaces.vo.PageRenderVO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-05 18:01
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/page/display")
@Slf4j
public class PageRenderController {

    @Resource
    private PageRenderService pageRenderService;

    @Resource
    private PageRenderRepository pageRenderRepository;

    @GetMapping("/deleteAllPageVersion")
    @ApiOperation(value = "/deleteAllPageVersion", notes = "清除页面缓存", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<Void> deleteAllPageVersion() {
        pageRenderService.deleteAllPageVersion();
        return JsonResult.success();
    }

    @GetMapping("/getCachePageVersion")
    @ApiOperation(value = "/getCachePageVersion", notes = "获取页面缓存数据", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<PageRenderVO> getCachePageVersion(@RequestParam("id") Integer id) {
        PageRender pageRender = pageRenderRepository.getPageRenderByCatalogueId(new CatalogueId(id));
        return JsonResult.success(InterfacePageRenderAssembler.domainToVo(pageRender));
    }

    @GetMapping("/buildPageVersion")
    @ApiOperation(value = "/buildPageVersion", notes = "构建页面缓存", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<Void> buildAllPageVersion(@RequestParam(value = "id", required = false) Integer id) {
        if (id == null) {
            pageRenderService.buildAllPageVersion();
        } else {
            pageRenderService.generateCurrentVersion(new CatalogueId(id));
        }
        return JsonResult.success();
    }
}
