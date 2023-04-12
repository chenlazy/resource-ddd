package com.idanchuang.cms.server.interfaces.controller;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.api.facade.PageRenderFacade;
import com.idanchuang.cms.api.response.PageRenderDTO;
import com.idanchuang.cms.server.interfaces.app.dto.PageVersionDiffForm;
import com.idanchuang.cms.server.interfaces.app.facade.PageMasterplateFacade;
import com.idanchuang.cms.server.interfaces.web.config.GatewayUserDTO;
import com.idanchuang.cms.server.interfaces.web.config.RequestContext;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/14
 */
@Slf4j
public class PageExampleControllerTest extends SpringTest {

    @Resource
    private PageRenderFacade pageRenderFacade;

    @Resource
    private PageMasterplateFacade pageMasterplateFacade;

    @Test
    public void getInfoById() {
        JsonResult<PageRenderDTO> result = pageRenderFacade.getInfoById(2087);
        log.info(JSON.toJSONString(result));
    }


    @Test
    public void getInfoList() {
        JsonResult<List<PageRenderDTO>> result = pageRenderFacade.getInfoList(1762);
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void getCachePageVersion() {
        JsonResult<PageRenderDTO> result = pageRenderFacade.getCachePageVersion(1718);
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void testDiff() {
        GatewayUserDTO userDTO = new GatewayUserDTO();
        userDTO.setIdCode(6931323);
        userDTO.setBrandProviderLevel(1);
        PageVersionDiffForm diffForm = new PageVersionDiffForm();
        diffForm.setAliasTitle("VTN_4285");
        JsonResult<com.idanchuang.cms.server.interfaces.app.dto.PageRenderDTO> diff = pageMasterplateFacade.diff(diffForm, "", "", userDTO);
        Assert.assertNotNull(diff.getData());
    }

    @Test
    public void testDiffNew() {
        GatewayUserDTO userDTO = RequestContext.get();
        PageVersionDiffForm diffForm = new PageVersionDiffForm();
        diffForm.setAliasTitle("VTN_4285");
        JsonResult<com.idanchuang.cms.server.interfaces.app.dto.PageRenderDTO> renderResult = pageMasterplateFacade.diff(diffForm, "", "", userDTO);
        Assert.assertNotNull(renderResult.getData());
    }

    @Test
    public void testShare() {
        JsonResult<com.idanchuang.cms.server.interfaces.app.dto.PageRenderDTO> share = pageMasterplateFacade.share(4285, RequestContext.get());
        Assert.assertNotNull(share.getData());
    }

}