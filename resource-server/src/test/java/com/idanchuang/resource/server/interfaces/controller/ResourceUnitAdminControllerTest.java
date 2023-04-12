package com.idanchuang.resource.server.interfaces.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.request.ResourceUnitCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceUnitSearchReqDTO;
import com.idanchuang.resource.api.request.ResourceUnitUpdateReqDTO;
import com.idanchuang.resource.api.response.ResourceUnitRespDTO;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author wengbinbin
 * @date 2021/3/18
 */

@Slf4j
public class ResourceUnitAdminControllerTest extends SpringTest {
    @Resource
    private ResourceUnitController resourceUnitController;

    @Test
    public void testGetUnitIdByUnitNameLimitOne() {
        JsonResult<Long> nameLimitOne = resourceUnitController.getUnitIdByUnitNameLimitOne("单元名称");
        Assert.assertNotNull(nameLimitOne.getData());
    }

    @Test
    public void testCreateResourceUnit() {
        ResourceUnitCreateReqDTO req = new ResourceUnitCreateReqDTO()
                .setResourceId(37L).setContentTitle("首页资源位弹屏组件12").setPlatformFrom("[1,2,3]")
                .setWeight(1).setStartTime(LocalDateTime.now().minusDays(5))
                .setEndTime(LocalDateTime.now()).setVisibleRoleS(Arrays.asList(1, 2)).setComponentJsonData("{}")
                .setOperatorId(42).setOperatorUser("wbb").setCreateUser("wbb").setCreateUserId(42);
        log.info(JSON.toJSONString(resourceUnitController.createResourceUnit(req)));
    }

    @Test
    public void testGetResourceUnitById() {
        JsonResult<ResourceUnitRespDTO> dtoJsonResult = resourceUnitController.getResourceUnitById(35L);
        log.info(JSON.toJSONString(dtoJsonResult.getData()));
        log.info(dtoJsonResult.getData().getComponentConfined().toString());
        log.info(dtoJsonResult.getData().getPageName());
    }

    @Test
    public void testSearchResourceUnit() {
        ResourceUnitSearchReqDTO req = new ResourceUnitSearchReqDTO();
//        req.setResourceName("test");
        req.setContentTitle("首页");
        JsonResult<PageInfo<ResourceUnitRespDTO>> pageInfoJsonResult = resourceUnitController.searchResourceUnit(req);
        log.info(String.valueOf(pageInfoJsonResult.getData().getTotal()));
        req.setPageNum(1).setPageSize(2).setContentTitle("首页");
        log.info(String.valueOf(pageInfoJsonResult.getData().getTotal()));
        req = new ResourceUnitSearchReqDTO().setResourceId(38L).setPageCode("0");
        log.info(JSON.toJSONString(resourceUnitController.searchResourceUnit(req)));
    }

    @Test
    public void testUpdateResourceUnit() {
        ResourceUnitUpdateReqDTO req = new ResourceUnitUpdateReqDTO();
        req.setUnitId(37L).setContentTitle("首页资源位弹屏组件1").setPlatformFrom(Arrays.asList(1,2)).setWeight(2)
                .setStartTime(LocalDateTime.now().minusDays(6)).setVisibleRoleS(Arrays.asList(1,2))
                .setOperatorId(43).setOperatorUser("test");
        resourceUnitController.updateResourceUnit(req);

    }
}