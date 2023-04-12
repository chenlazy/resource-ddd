package com.idanchuang.resource.server.interfaces.controller;

import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.facade.ResourceFacade;
import com.idanchuang.resource.api.request.ContentPutReq;
import com.idanchuang.resource.api.request.PrePutContentBatchReq;
import com.idanchuang.resource.api.request.PreviewPutContentReq;
import com.idanchuang.resource.api.response.PutResourceDTO;
import com.idanchuang.resource.api.response.PutResourcePageListDTO;
import com.idanchuang.resource.server.application.service.ResourceService;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.domain.model.resource.ResourcePutInfo;
import com.idanchuang.resource.server.infrastructure.transfer.ResourceConfigTransfer;
import com.idanchuang.resource.server.infrastructure.transfer.ResourcePutInfoTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fym
 * @description :
 * @date 2021/3/11 下午6:04
 */
@RestController
@RequestMapping("/resource/")
@Slf4j
public class ResourceController implements ResourceFacade {

    @Resource
    private ResourceService resourceService;

    @Override
    public JsonResult<List<PutResourceDTO>> getContentPutInfo(@Valid ContentPutReq req) {
        List<ResourcePutInfo> resourceConfigs = resourceService.getContentPutInfo(req.getResourceIdList(), req.getPageCode(), req.getBusiness(), req.getPlatform(), req.getRole());
        List<PutResourceDTO> putResourceDTOS = resourceConfigs.stream().map(ResourcePutInfoTransfer::model2Dto).collect(Collectors.toList());
        return JsonResult.success(putResourceDTOS);
    }

    @Override
    public JsonResult<PutResourceDTO> getContentPutInfoByResourceId(Long resourceId, String pageCode, Integer business, String platform, Integer role) {
        ResourcePutInfo contentPutInfoByResourceId = resourceService.getContentPutInfoByResourceId(resourceId, pageCode, business, platform, role);
        return JsonResult.success(ResourcePutInfoTransfer.model2Dto(contentPutInfoByResourceId));
    }

    @Override
    public JsonResult<List<PutResourcePageListDTO>> getResourceListByPageId(String pageCode, Integer business) {
        List<ResourceConfig> resourceListByPageId = resourceService.getResourceListByPageId(pageCode, business);
        List<PutResourcePageListDTO> pageListDTOS = resourceListByPageId.stream().map(ResourceConfigTransfer::resourceConfig2PageListDTO).collect(Collectors.toList());
        return JsonResult.success(pageListDTOS);
    }

    @Override
    public JsonResult<PutResourceDTO> getPreviewPutContent(@Valid PreviewPutContentReq req) {
        ResourcePutInfo previewPutContent = resourceService.getPreviewPutContent(ResourcePutInfoTransfer.req2ModelQuery(req));
        return JsonResult.success(ResourcePutInfoTransfer.model2Dto(previewPutContent));
    }

    @Override
    public JsonResult<List<PutResourceDTO>> getPreviewPutContentList(@Valid PrePutContentBatchReq req) {
        List<ResourcePutInfo> previewPutContentList = resourceService.getPreviewPutContentList(ResourcePutInfoTransfer.batchReq2ModelQuery(req));
        List<PutResourceDTO> resourceDTOS = previewPutContentList.stream().map(ResourcePutInfoTransfer::model2Dto).collect(Collectors.toList());
        return JsonResult.success(resourceDTOS);
    }
}
