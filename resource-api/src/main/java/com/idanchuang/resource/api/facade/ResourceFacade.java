package com.idanchuang.resource.api.facade;

import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.request.ContentPutReq;
import com.idanchuang.resource.api.request.PrePutContentBatchReq;
import com.idanchuang.resource.api.request.PreviewPutContentReq;
import com.idanchuang.resource.api.response.PutResourceDTO;
import com.idanchuang.resource.api.response.PutResourcePageListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by develop at 2021/2/4.
 *
 * @author wuai
 */
@FeignClient(value = "DC-RESOURCE", path = "/resource/")
@Api(value = "获取资源位内容投放", tags = {"获取资源位内容投放"})
public interface ResourceFacade {


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
     * @param resourceId   资源位id
     * @param pageCode 页面id
     * @param business     业务渠道
     * @param platform     业务平台
     * @param role         用户角色
     * @return
     */
    @GetMapping("/getContentPutInfoByResourceId")
    @ApiOperation(value = "/getContentPutInfoByResourceId", notes = "根据资源id获取投放内容", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<PutResourceDTO> getContentPutInfoByResourceId(@RequestParam("resourceId") Long resourceId, @RequestParam("pageCode") String pageCode, @RequestParam("business") Integer business, @RequestParam("platform") String platform, @RequestParam("role") Integer role);

    /**
     * 根据页面id获取资源位id列表
     *
     * @param pageCode
     * @param business
     * @return
     */
    @GetMapping("/getResourceListByPageId")
    @ApiOperation(value = "/getResourceListByPageId", notes = "根据页面id获取资源位id列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<List<PutResourcePageListDTO>> getResourceListByPageId(@RequestParam("pageCode") String pageCode, @RequestParam("business") Integer business);

    /**
     * 预览资源位投放信息
     * @param previewPutContentReq
     * @return
     */
    @GetMapping("/getPreviewPutContent")
    @ApiOperation(value = "/getPreviewPutContent", notes = "预览资源位投放信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<PutResourceDTO> getPreviewPutContent(@RequestBody @Valid PreviewPutContentReq previewPutContentReq);

    /**
     * 批量预览资源位投放信息
     * @param prePutContentBatchReq
     * @return
     */
    @GetMapping("/getPreviewPutContentList")
    @ApiOperation(value = "/getPreviewPutContentList", notes = "批量预览资源位投放信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<List<PutResourceDTO>> getPreviewPutContentList(@RequestBody @Valid PrePutContentBatchReq prePutContentBatchReq);
}
