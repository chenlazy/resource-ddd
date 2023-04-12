package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.api.facade.PageTagFacade;
import com.idanchuang.cms.api.request.PageTagConditionReq;
import com.idanchuang.cms.api.response.PageTagDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-05-31 09:53
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class PageTagControllerTest extends SpringTest {

    @Resource
    private PageTagFacade pageTagFacade;

    @Test
    public void testAddTag() {
        PageTagDTO dto = new PageTagDTO();
        dto.setName("测试标签");
        dto.setPlatform(1);
        dto.setPageCode("ignore");
        JsonResult<Boolean> insert = pageTagFacade.insert(dto);
        Assert.assertTrue(insert.getData());
    }

    @Test
    public void testUpdateTag() {
        PageTagDTO dto = new PageTagDTO();
        dto.setId(1000104);
        dto.setName("修改标签");
        dto.setOperatorId(2573);
        dto.setPageCode("test");
        JsonResult<Boolean> result = pageTagFacade.updateById(dto);
        Assert.assertTrue(result.getData());
    }

    @Test
    public void queryTag() {
        PageTagConditionReq condition = new PageTagConditionReq();
        condition.setPageNum(1);
        condition.setPageSize(20);
        condition.setPlatform(1);
        JsonResult<PageData<PageTagDTO>> result = pageTagFacade.pageByCondition(condition);
        Assert.assertNotNull(result.getData());
    }

}
