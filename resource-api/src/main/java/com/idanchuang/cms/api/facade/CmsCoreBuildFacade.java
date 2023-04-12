package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.request.CmsCorePageReq;
import com.idanchuang.cms.api.request.CmsCoreUpdateReq;
import com.idanchuang.cms.api.request.CmsPageTemplateReq;
import com.idanchuang.cms.api.response.CmsPageDetailDTO;
import com.idanchuang.cms.api.response.CmsPageListDTO;
import com.idanchuang.cms.api.response.CmsPageTemplateDTO;
import com.idanchuang.cms.api.response.PageVersionDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-07 10:20
 * @Desc: CMS搭建服务
 * @Copyright VTN Limited. All rights reserved.
 */
//@Api(value = "CMS搭建服务", tags = {"CMS搭建服务"})
public interface CmsCoreBuildFacade {

    @PostMapping("/pageList")
    @ApiOperation(value = "/pageList", notes = "查询目录列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<PageData<CmsPageListDTO>> catalogueList(@RequestBody CmsCorePageReq cmsCorePageReq);

    @GetMapping("/createPageSchema")
    @ApiOperation(value = "/createPageSchema", notes = "创建目录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Integer> createCatalogue(@RequestParam("operateId") Long operateId, @RequestParam("tagId") Long tagId);

    @PostMapping("/update")
    @ApiOperation(value = "/update", notes = "编辑目录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> upsertCatalogue(@Valid @RequestBody CmsCoreUpdateReq coreUpdateReq);

    @DeleteMapping("/remove")
    @ApiOperation(value = "/remove", notes = "删除目录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> removeCatalogue(@RequestParam("id") Integer id, @RequestParam("operateId") Long operateId);

    @GetMapping("/detail")
    @ApiOperation(value = "/detail", notes = "页面详情", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<CmsPageDetailDTO> getMasterplateDetail(@RequestParam(value = "id", required = false) Integer id,
                                                      @RequestParam(value = "templateId", required = false) Integer templateId,
                                                      @RequestParam(value = "pageTempId", required = false) Integer pageTempId);

    @PostMapping("/listPageTemplate")
    @ApiOperation(value = "/listPageTemplate", notes = "根据别名查询页面实例", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<PageData<CmsPageTemplateDTO>> listPageTemplate(@RequestBody @Valid CmsPageTemplateReq cmsPageTemplateReq);

    @GetMapping("/removePageTemplate")
    @ApiOperation(value = "/removePageTemplate", notes = "删除页面模版", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> removePageTemplate(@RequestParam("templateId") Integer templateId, @RequestParam("operateId") Long operateId);

    @GetMapping("/publishPageTemplate")
    @ApiOperation(value = "/publishPageTemplate", notes = "发布页面模版", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> publishPageTemplate(@RequestParam("templateId") Integer templateId, @RequestParam("operateId") Long operateId);

    @PostMapping("/editPageTemplate")
    @ApiOperation(value = "/editPageTemplate", notes = "编辑页面模版", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> editPageTemplate(@RequestBody @Valid CmsCoreUpdateReq coreUpdateReq);

    @PostMapping("/change")
    @ApiOperation(value = "频道页面、活动页面转换")
    JsonResult<Boolean> change(@RequestParam("catalogueId") Long catalogueId);

    @GetMapping("/historyTemplateList")
    @ApiOperation(value = "/historyTemplateList", notes = "历史模版快照版本列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<List<PageVersionDTO>> historyTemplateList(@RequestParam(value = "templateId") Integer templateId,
                                                         @RequestParam(value = "containCurrent", required = false) Integer containCurrent);

    @GetMapping("/querySnapTemplate")
    @ApiOperation(value = "/querySnapTemplate", notes = "历史模版快照数据", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<String> querySnapTemplate(@RequestParam(value = "templateId") Integer templateId);
}
