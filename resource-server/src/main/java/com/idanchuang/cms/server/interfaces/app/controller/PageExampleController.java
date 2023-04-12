package com.idanchuang.cms.server.interfaces.app.controller;

import com.idanchuang.cms.server.interfaces.app.dto.PageRenderDTO;
import com.idanchuang.cms.server.interfaces.app.dto.PageVersionDiffForm;
import com.idanchuang.cms.server.interfaces.app.facade.PageMasterplateFacade;
import com.idanchuang.cms.server.interfaces.web.config.GatewayUserDTO;
import com.idanchuang.cms.server.interfaces.web.config.RequestContext;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@RequestMapping("/page/version")
@Api(tags = "页面版本详情内容获取")
public class PageExampleController {

    @Resource
    private PageMasterplateFacade pageMasterplateFacade;


    /**
     * 比对页面版本并返回最新版本
     **/
    @PostMapping("/diff")
    public JsonResult<PageRenderDTO> diff(@RequestBody PageVersionDiffForm pageVersionDiffForm, HttpServletRequest request){

        try {
            String device = request.getHeader("device");
            String appVersion = request.getHeader("version");
            GatewayUserDTO userDTO = RequestContext.get();
            //如果设置了等级，获取header里面的等级
            String level = request.getHeader("level");
            if (NumberUtils.isCreatable(level)) {
                userDTO.setBrandProviderLevel(Integer.parseInt(level));
            }
            return pageMasterplateFacade.diff(pageVersionDiffForm, device, appVersion, userDTO);
        } catch (Exception e) {
            log.error("diff error pageVersionDiffForm {},e{}", JsonUtil.toJsonString(pageVersionDiffForm), e);
            return JsonResult.failure("页面查询异常");
        }
    }

    @PostMapping("/diffNew")
    public PageRenderDTO diffNew(@RequestBody PageVersionDiffForm pageVersionDiffForm,
                                       HttpServletRequest request, HttpServletResponse response) {
        try {
            String device = request.getHeader("device");
            String appVersion = request.getHeader("version");
            GatewayUserDTO userDTO = RequestContext.get();
            JsonResult<PageRenderDTO> renderResult = pageMasterplateFacade.diff(pageVersionDiffForm, device, appVersion, userDTO);

            PageRenderDTO renderDTO = null;
            if (renderResult.isSuccess()) {
                renderDTO = renderResult.getData();
            }

            if (null != renderDTO) {
                if (renderDTO.getUpdateVersion() == 1) {
                    response.setHeader("code", "304");
                }
                response.setHeader("serverTimestamp", renderDTO.getServerTimestamp().toString());
            }
            return renderDTO;
        } catch (Exception e) {
            log.error("diff error pageVersionDiffForm {},e{}", JsonUtil.toJsonString(pageVersionDiffForm), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    @GetMapping("/share")
    @ApiOperation(value = "/share", notes = "专题分享信息，根据ID查询专题信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<PageRenderDTO> share(@RequestParam(value = "id", required = false) String id) {

            if (StringUtils.isEmpty(id)) {
                return null;
            }
            if (id.contains("_")) {
                id = id.substring(id.indexOf("_") + 1);
            }
            Integer catalogueId = NumberUtils.isCreatable(id) ? Integer.parseInt(id) : null;
            return pageMasterplateFacade.share(catalogueId, RequestContext.get());
    }
}
