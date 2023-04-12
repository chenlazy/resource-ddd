package com.idanchuang.resource.server.interfaces.controller;

import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.api.response.ResourceBusinessRespDTO;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author wengbinbin
 * @date 2021/3/18
 */
public class ResourceBusinessAdminControllerTest extends SpringTest {
    @Resource
    ResourceBusinessController resourceBusinessController;

    @Test
    public void getResourceBusiness() {
        JsonResult<List<ResourceBusinessRespDTO>> result = resourceBusinessController.getResourceBusiness();
        System.out.println(result.toString());
    }

    @Test
    public void testGetArticleById() {
        JsonResult<Object> article = resourceBusinessController.getArticleById(34L);
        Assert.assertNotNull(article.getData());
    }
}