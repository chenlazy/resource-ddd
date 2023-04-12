package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.request.CmsPageSchemaCreateReq;
import com.idanchuang.cms.api.request.CmsPageSchemaUpdateReq;
import com.idanchuang.cms.api.response.CmsPageSchemaDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 17:05
 * @Desc: CMS页面定义服务
 * @Copyright VTN Limited. All rights reserved.
 */
@FeignClient(value = "DC-RESOURCE", path = "/cms/pageSchema")
//@Api(value = "CMS页面定义服务", tags = {"CMS页面定义服务"})
public interface CmsPageSchemaFacade {

    @PostMapping("/create")
    @ApiOperation(value = "/create", notes = "创建页面定义信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Integer> createPageSchema(@Valid @RequestBody CmsPageSchemaCreateReq createReq);

    @PostMapping("/batchCreate")
    @ApiOperation(value = "/batchCreate", notes = "批量创建页面定义信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> batchCreateSchema(@RequestBody List<CmsPageSchemaCreateReq> createReqs);

    @GetMapping("/query")
    @ApiOperation(value = "/query", notes = "创建页面定义信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<CmsPageSchemaDTO> getPageSchema(Integer pageSchemaId);


    @PostMapping("/update")
    @ApiOperation(value = "/update", notes = "更新页面定义信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> updatePageSchema(@Valid @RequestBody CmsPageSchemaUpdateReq updateReq);

}
