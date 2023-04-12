package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.request.CmsPageContainerCreateReq;
import com.idanchuang.cms.api.response.CmsPageContainerDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 17:04
 * @Desc: 容器实例接口类
 * @Copyright VTN Limited. All rights reserved.
 */
@FeignClient(value = "DC-RESOURCE", path = "/cms/container")
//@Api(value = "CMS容器实例服务", tags = {"CMS容器实例服务"})
public interface CmsPageContainerFacade {

    @PostMapping("/createContainer")
    @ApiOperation(value = "/createContainer", notes = "创建容器实例", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> createPageContainer(@Valid @RequestBody CmsPageContainerCreateReq containerCreateReq);

    @PostMapping("/batchCreate")
    @ApiOperation(value = "/batchCreate", notes = "批量创建容器实例", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> batchCreateContainer(@RequestBody List<CmsPageContainerCreateReq> containerCreateReqs);

    @GetMapping("/batchQuery")
    @ApiOperation(value = "/batchQuery", notes = "查询叶页面容器实例", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<List<CmsPageContainerDTO>> queryPageContainer(@RequestParam("pageId") Integer pageId);

    @GetMapping("/updateContainerPage")
    @ApiOperation(value = "/updateContainerPage", notes = "更新容器页面id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> updateContainerPage(@RequestParam("pageId")Integer pageId, @RequestParam("containerIds") List<Long> containerIds);
}
