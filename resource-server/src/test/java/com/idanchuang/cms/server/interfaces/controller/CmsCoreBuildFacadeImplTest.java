package com.idanchuang.cms.server.interfaces.controller;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.api.request.CmsCorePageReq;
import com.idanchuang.cms.api.request.CmsCoreUpdateReq;
import com.idanchuang.cms.api.request.CmsPageTemplateReq;
import com.idanchuang.cms.api.response.CmsPageDetailDTO;
import com.idanchuang.cms.api.response.CmsPageListDTO;
import com.idanchuang.cms.api.response.CmsPageTemplateDTO;
import com.idanchuang.cms.api.response.PageVersionDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/10/11
 */
@Slf4j
public class CmsCoreBuildFacadeImplTest extends SpringTest {

    @Resource
    private CmsCoreBuildFacadeImpl cmsCoreBuildFacadeImpl;

    @Test
    public void publishPageTemplate() {
        JsonResult<Boolean> result = cmsCoreBuildFacadeImpl.publishPageTemplate(3746, 1L);
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void pageList() {
        CmsCorePageReq req = new CmsCorePageReq();
        req.setPlatform("vtn");
        //req.setTagId(999L);
        //req.setId(2087L);
        req.setAliasTitle("VTN_1");
        JsonResult<PageData<CmsPageListDTO>> result = cmsCoreBuildFacadeImpl.catalogueList(req);
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void getDetailById() {
        JsonResult<CmsPageDetailDTO> result = cmsCoreBuildFacadeImpl.getMasterplateDetail(2208, 3723, 2208);
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void catalogueList() {
        CmsCorePageReq corePageReq = new CmsCorePageReq();
        corePageReq.setCurrent(1L);
        corePageReq.setSize(30L);
        JsonResult<PageData<CmsPageListDTO>> list = cmsCoreBuildFacadeImpl.catalogueList(corePageReq);
        Assert.assertNotNull(list.getData());
    }

    @Test
    public void createCatalogue() {
        JsonResult<Integer> result = cmsCoreBuildFacadeImpl.createCatalogue(2573L, 1000098L);
        Assert.assertTrue(result.getData() > 0);
    }

    @Test
    public void update() {
        CmsCoreUpdateReq coreUpdateReq = new CmsCoreUpdateReq();
        coreUpdateReq.setId(4300);
        coreUpdateReq.setCompData("{\"compDataList\":[{\"containerCode\":\"\",\"containerId\":10128,\"containerName\":\"容器1\",\"componentJsonData\":[{\"level\":[],\"layoutType\":\"1\",\"details\":[{\"picture\":\"https://img.danchuangglobal.com/202205/1ddbda77c0804984b0cc8d9c1364a7eb.jpeg\",\"linkType\":\"0\",\"jumpConfig\":{\"url\":\"\"},\"pictureName\":\"1c0d3f6b27\",\"image_id\":10142841,\"picture_imgData\":{\"width\":300,\"height\":300}}],\"componentBaseConfig_1\":{\"picture\":{\"radius\":0,\"space\":0},\"component\":{\"padding\":0}},\"version\":\"v2\",\"type\":\"access-picture-v2\",\"group\":\"base\",\"name\":\"图片(新)\",\"needLogin\":false,\"id\":1,\"role\":\"1\",\"containerId\":1653896519753,\"contentId\":\"access-picture-v2165389651966885\",\"componentId\":1640000023847,\"remark\":\"l3sfabjd\",\"apm\":{\"list\":[\"2.1640000023847..1.7.18_10142841.type_0-setType_1\"]}}]}],\"pageConfig\":{\"isLongTime\":true,\"resourceConfig\":{},\"backGroundColor\":\"#fff\",\"hasBackgroundImage\":false,\"backgroundImageType\":\"1\",\"backGroundImageList\":[null],\"shareFlag\":false,\"shareType\":\"min-program\",\"shareScope\":0,\"shareInfoList\":[],\"tagId\":1000103,\"pageTitle\":\"测试页面\",\"startTime\":\"2022-05-30T16:42:23\",\"templateName\":\"测试页面\",\"describe\":\"测试页面\",\"shareTitle\":null,\"shareDesc\":null}}");
        coreUpdateReq.setDescribe("测试");
        coreUpdateReq.setEndTime(null);
        coreUpdateReq.setShareFlag(0);
        coreUpdateReq.setNicheIds(Collections.emptyList());
        coreUpdateReq.setPageTitle("测试");
        coreUpdateReq.setPlatform("vtn");
        coreUpdateReq.setStartTime("2022-05-30T16:00:23");
        coreUpdateReq.setSelectInfoList(Collections.emptyList());
        coreUpdateReq.setShareInfoList(Collections.emptyList());
        JsonResult<Boolean> result = cmsCoreBuildFacadeImpl.upsertCatalogue(coreUpdateReq);
        Assert.assertTrue(result.getData());
    }

    @Test
    public void remove() {
        JsonResult<Boolean> result = cmsCoreBuildFacadeImpl.removeCatalogue(4300, 2573L);
        Assert.assertTrue(result.getData());
    }

    @Test
    public void listPageTemplate() {
        CmsPageTemplateReq templateReq = new CmsPageTemplateReq();
        templateReq.setCurrent(1L);
        templateReq.setSize(10L);
        JsonResult<PageData<CmsPageTemplateDTO>> result = cmsCoreBuildFacadeImpl.listPageTemplate(templateReq);
        Assert.assertNotNull(result.getData());
    }

    @Test
    public void removePageTemplate() {
        JsonResult<Boolean> result = cmsCoreBuildFacadeImpl.removePageTemplate(7216, 2573L);
        Assert.assertTrue(result.getData());
    }

    @Test
    public void editPageTemplate() {
        CmsCoreUpdateReq coreUpdateReq = new CmsCoreUpdateReq();
        coreUpdateReq.setId(4300);
        coreUpdateReq.setTemplateId(7216);
        coreUpdateReq.setTemplateName("测试页面");
        coreUpdateReq.setShareFlag(0);
        coreUpdateReq.setCompData("{\"compDataList\":[{\"containerCode\":\"\",\"containerId\":10128,\"containerName\":\"容器1\",\"componentJsonData\":[{\"level\":[],\"layoutType\":\"1\",\"details\":[{\"picture\":\"https://img.danchuangglobal.com/202205/1ddbda77c0804984b0cc8d9c1364a7eb.jpeg\",\"linkType\":\"0\",\"jumpConfig\":{\"url\":\"\"},\"pictureName\":\"1c0d3f6b27\",\"image_id\":10142841,\"picture_imgData\":{\"width\":300,\"height\":300}}],\"componentBaseConfig_1\":{\"picture\":{\"radius\":0,\"space\":0},\"component\":{\"padding\":0}},\"version\":\"v2\",\"type\":\"access-picture-v2\",\"group\":\"base\",\"name\":\"图片(新)\",\"needLogin\":false,\"id\":1,\"role\":\"1\",\"containerId\":1653896519753,\"contentId\":\"access-picture-v2165389651966885\",\"componentId\":1640000023847,\"remark\":\"l3sfabjd\",\"apm\":{\"list\":[\"2.1640000023847..1.7.18_10142841.type_0-setType_1\"]}}]}],\"pageConfig\":{\"isLongTime\":true,\"resourceConfig\":{},\"backGroundColor\":\"#fff\",\"hasBackgroundImage\":false,\"backgroundImageType\":\"1\",\"backGroundImageList\":[null],\"shareFlag\":false,\"shareType\":\"min-program\",\"shareScope\":0,\"shareInfoList\":[],\"tagId\":1000103,\"pageTitle\":\"测试页面\",\"startTime\":\"2022-05-30T16:42:23\",\"templateName\":\"测试页面\",\"describe\":\"测试页面\",\"shareTitle\":null,\"shareDesc\":null}}");
        coreUpdateReq.setDescribe("测试");
        coreUpdateReq.setEndTime(null);
        coreUpdateReq.setNicheIds(Collections.emptyList());
        coreUpdateReq.setPageTitle("测试");
        coreUpdateReq.setPlatform("vtn");
        coreUpdateReq.setStartTime("2022-05-30T16:00:23");
        coreUpdateReq.setSelectInfoList(Collections.emptyList());
        coreUpdateReq.setShareInfoList(Collections.emptyList());
        JsonResult<Boolean> result = cmsCoreBuildFacadeImpl.editPageTemplate(coreUpdateReq);
        Assert.assertTrue(result.getData());
    }

    @Test
    public void change() {
        JsonResult<Boolean> change = cmsCoreBuildFacadeImpl.change(4300L);
        Assert.assertTrue(change.getData());
    }

    @Test
    public void historyTemplateList() {
        JsonResult<List<PageVersionDTO>> result = cmsCoreBuildFacadeImpl.historyTemplateList(7216, 1);
        Assert.assertNotNull(result.getData());
    }

    @Test
    public void querySnapTemplate() {
        JsonResult<String> result = cmsCoreBuildFacadeImpl.querySnapTemplate(7216);
        Assert.assertNotNull(result.getData());
    }
}