package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.server.applicationNew.service.PageRenderService;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRenderRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-05-30 19:11
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class PageRenderControllerTest extends SpringTest {

    @Resource
    private PageRenderService pageRenderService;

    @Resource
    private PageRenderRepository pageRenderRepository;

    @Test
    public void deleteAllPageVersion() {
        Boolean result = pageRenderService.deleteAllPageVersion();
        Assert.assertTrue(result);
    }

    @Test
    public void getCachePageVersion() {
        PageRender render = pageRenderRepository.getPageRenderByCatalogueId(new CatalogueId(4300L));
        Assert.assertNotNull(render);
    }

    @Test
    public void buildAllPageVersion() {
        Boolean pageVersion = pageRenderService.buildAllPageVersion();
        PageRender render = pageRenderService.generateCurrentVersion(new CatalogueId(4300L));
        Assert.assertTrue(pageVersion);
        Assert.assertNotNull(render);
    }
}
