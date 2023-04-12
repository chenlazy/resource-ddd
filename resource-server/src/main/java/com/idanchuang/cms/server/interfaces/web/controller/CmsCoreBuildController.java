package com.idanchuang.cms.server.interfaces.web.controller;

import com.idanchuang.cms.api.facade.CmsCoreBuildFacade;
import com.idanchuang.cms.api.request.CmsCorePageReq;
import com.idanchuang.cms.api.request.CmsCoreUpdateReq;
import com.idanchuang.cms.api.request.CmsPageTemplateReq;
import com.idanchuang.cms.api.response.CmsPageDetailDTO;
import com.idanchuang.cms.api.response.CmsPageListDTO;
import com.idanchuang.cms.api.response.CmsPageTemplateDTO;
import com.idanchuang.cms.api.response.PageVersionDTO;
import com.idanchuang.cms.server.application.service.log.PageDeleteOperateLogService;
import com.idanchuang.cms.server.application.service.log.PageUpdateOperateLogService;
import com.idanchuang.cms.server.infrastructure.annotation.OperateLog;
import com.idanchuang.cms.server.infrastructure.shard.OperateLogConstant;
import com.idanchuang.cms.server.interfaces.web.config.RequestContext;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-05 17:55
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/")
@Api(value = "搭建工具", tags = {"搭建工具服务"})
@Slf4j
public class CmsCoreBuildController {

    @Resource
    private CmsCoreBuildFacade cmsCoreBuildFacade;

    @PostMapping("/pageList")
    @ApiOperation(value = "/pageList", notes = "查询目录列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<PageData<CmsPageListDTO>> catalogueList(@RequestBody @Valid CmsCorePageReq req) {
        return cmsCoreBuildFacade.catalogueList(req);
    }

    @OperateLog(desc = "创建页面", field = OperateLogConstant.OPERATE_LOG_DATA, module = OperateLogConstant.MODULE_CMS, logType = OperateLogConstant.LOG_TYPE_PAGE_SCHEMA)
    @GetMapping("/createPageSchema")
    @ApiOperation(value = "/createPageSchema", notes = "创建目录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<Integer> createCatalogue(@RequestParam(value = "tagId") Long tagId) {
        return cmsCoreBuildFacade.createCatalogue(RequestContext.get().getId().longValue(), tagId);
    }

    @OperateLog(desc = "修改专题工具", field = "id", module = OperateLogConstant.MODULE_CMS, logType = OperateLogConstant.LOG_TYPE_PAGE_SCHEMA)
    @PostMapping("/update")
    @ApiOperation(value = "/update", notes = "编辑目录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<Boolean> update(@RequestBody @Valid CmsCoreUpdateReq req) {
        long operatorId = RequestContext.get().getId().longValue();
        req.setOperatorId(operatorId);
        return cmsCoreBuildFacade.upsertCatalogue(req);

    }

    @GetMapping("/detail")
    @ApiOperation(value = "/detail", notes = "获取专题工具信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<CmsPageDetailDTO> get(@RequestParam(value = "id", required = false) Integer id,
                                            @RequestParam(value = "templateId", required = false) Integer templateId,
                                            @RequestParam(value = "pageTempId", required = false) Integer pageTempId) {
        return cmsCoreBuildFacade.getMasterplateDetail(id, templateId, pageTempId);
    }

    @OperateLog(desc = "删除页面", field = "id", module = OperateLogConstant.MODULE_CMS, logType = OperateLogConstant.LOG_TYPE_PAGE_SCHEMA)
    @DeleteMapping("/remove/{id}")
    @ApiOperation(value = "/remove/{id}", notes = "删除专题工具", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<Boolean> remove(@PathVariable("id") Long id) {
        return cmsCoreBuildFacade.removeCatalogue(id.intValue(), RequestContext.get().getId().longValue());
    }

    @PostMapping("/listPageTemplate")
    @ApiOperation(value = "/listPageTemplate", notes = "查询页面实例", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<PageData<CmsPageTemplateDTO>> listPageTemplate(@RequestBody @Valid CmsPageTemplateReq cmsPageTemplateReq) {
        return cmsCoreBuildFacade.listPageTemplate(cmsPageTemplateReq);
    }

    @OperateLog(desc = "删除页面模版", field = "templateId", module = OperateLogConstant.MODULE_CMS, logType = OperateLogConstant.LOG_TYPE_PAGE_SCHEMA, businessType = PageDeleteOperateLogService.class)
    @GetMapping("/removePageTemplate")
    @ApiOperation(value = "/removePageTemplate", notes = "删除页面模版", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> removePageTemplate(@RequestParam("templateId") Integer templateId) {
        return cmsCoreBuildFacade.removePageTemplate(templateId, RequestContext.get().getId().longValue());
    }

    @OperateLog(desc = "发布页面模版", field = "templateId", module = OperateLogConstant.MODULE_CMS, logType = OperateLogConstant.LOG_TYPE_PAGE_SCHEMA, businessType = PageDeleteOperateLogService.class)
    @GetMapping("/publishPageTemplate")
    @ApiOperation(value = "/publishPageTemplate", notes = "发布页面模版", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> publishPageTemplate(@RequestParam("templateId") Integer templateId) {
        return cmsCoreBuildFacade.publishPageTemplate(templateId, RequestContext.get().getId().longValue());
    }

    @OperateLog(desc = "编辑页面模版", field = "id", module = OperateLogConstant.MODULE_CMS, logType = OperateLogConstant.LOG_TYPE_PAGE_SCHEMA, businessType = PageUpdateOperateLogService.class)
    @PostMapping("/editPageTemplate")
    @ApiOperation(value = "/editPageTemplate", notes = "编辑页面模版", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Boolean> editPageTemplate(@RequestBody @Valid CmsCoreUpdateReq coreUpdateReq) {
        long operatorId = RequestContext.get().getId().longValue();
        coreUpdateReq.setOperatorId(operatorId);
        return cmsCoreBuildFacade.editPageTemplate(coreUpdateReq);
    }

    @GetMapping("/change")
    @ApiOperation(value = "频道页面、活动页面转换")
    JsonResult<Boolean> change(@RequestParam("catalogueId") Long catalogueId) {
        return cmsCoreBuildFacade.change(catalogueId);
    }

    @GetMapping("/historyTemplateList")
    @ApiOperation(value = "/historyTemplateList", notes = "历史模版快照版本列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<List<PageVersionDTO>> historyTemplateList(@RequestParam(value = "templateId") Integer templateId,
                                                         @RequestParam(value = "containCurrent", required = false) Integer containCurrent) {
        return cmsCoreBuildFacade.historyTemplateList(templateId, containCurrent);
    }

    @GetMapping("/querySnapTemplate")
    @ApiOperation(value = "/querySnapTemplate", notes = "历史模版快照数据", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<String> querySnapTemplate(@RequestParam(value = "templateId") Integer templateId) {
        return cmsCoreBuildFacade.querySnapTemplate(templateId);
    }
}
