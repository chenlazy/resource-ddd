package com.idanchuang.resource.server.interfaces.controller;

import com.github.pagehelper.PageInfo;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.request.ResourceConfigCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceConfigSearchReqDTO;
import com.idanchuang.resource.api.request.ResourceConfigUpdateReqDTO;
import com.idanchuang.resource.api.response.ResourceConfigRespDTO;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wengbinbin
 * @date 2021/3/19
 */

@Slf4j
public class ResourceConfigAdminControllerTest extends SpringTest {
    @Autowired
    private ResourceConfigController resourceConfigController;
    @Test
    public void testCreateResourceConfig() {
        ResourceConfigCreateReqDTO dto = new ResourceConfigCreateReqDTO();
        dto.setOperatorUser("wbb").setOperatorId(111).setBusinessType(1).setPageCode("abm_screen")
                .setResourceName("金刚位")
                .setComponentConfined(1).setComponentType("{}").setResourceNumb(11323)
                .setCreateUser("wbb").setCreateUserId(11);
        resourceConfigController.createResourceConfig(dto);
    }

    @Test
    public void testGetResourceConfigById() {
        JsonResult<ResourceConfigRespDTO> config = resourceConfigController.getResourceConfigById(53L);
        Assert.assertNotNull(config.getData());
    }

    @Test
    public void testGetResourceIdByNameLimitOne() {
        JsonResult<Long> resource = resourceConfigController.getResourceIdByNameLimitOne("resource");
        Assert.assertNotNull(resource.getData());
    }

    @Test
    public void testSearchResourceConfig() {
        ResourceConfigSearchReqDTO req = new ResourceConfigSearchReqDTO().setPageCode("abm_workbench").setPageSize(2).setPageNum(2);
        JsonResult<PageInfo<ResourceConfigRespDTO>> result1 = resourceConfigController.searchResourceConfig(req);
        assert result1.isSuccess();
        log.info(String.valueOf(result1.getData().getList().size()));
        req = new ResourceConfigSearchReqDTO().setResourceId(37L).setPageSize(2);
        result1 = resourceConfigController.searchResourceConfig(req);
        assert result1.isSuccess();
        log.info(String.valueOf(result1.getData().getList().size()));
        req = new ResourceConfigSearchReqDTO().setResourceStatus(1).setPageSize(2);
        result1 = resourceConfigController.searchResourceConfig(req);
        assert result1.isSuccess();
        log.info(String.valueOf(result1.getData().getList().size()));
        req = new ResourceConfigSearchReqDTO().setOperatorUser("马方超").setPageSize(2);
        result1 =  resourceConfigController.searchResourceConfig(req);
        assert result1.isSuccess();
        log.info(String.valueOf(result1.getData().getList().size()));
        req = new ResourceConfigSearchReqDTO().setResourceName("金刚").setPageSize(2);
        result1 =  resourceConfigController.searchResourceConfig(req);
        assert result1.isSuccess();
        log.info(String.valueOf(result1.getData().getList().size()));
    }

    @Test
    public void testUpdateResourceConfig() {
        ResourceConfigUpdateReqDTO req = new ResourceConfigUpdateReqDTO();
        req.setResourceId(37L).setResourceName("首页活动弹窗序号111").setOperatorId(1).setOperatorUser("admin");
        resourceConfigController.updateResourceConfig(req);
        req.setResourceId(37L).setResourceStatus(0).setOperatorId(1).setOperatorUser("admin");
        resourceConfigController.updateResourceConfig(req);
    }
}