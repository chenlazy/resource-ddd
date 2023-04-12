package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.api.feign.PageFeignClient;
import com.idanchuang.cms.api.request.PageHistoryDiffReq;
import com.idanchuang.cms.api.response.PageDiffDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-05-31 15:26
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class PageFeignClientTest extends SpringTest {

    @Resource
    private PageFeignClient pageFeignClient;

    @Test
    public void testComparePageDiff() {
        PageHistoryDiffReq historyDiffReq = new PageHistoryDiffReq();
        historyDiffReq.setPageId(4285);
        historyDiffReq.setMasterplateId(7154);
        JsonResult<PageDiffDTO> result = pageFeignClient.comparePageJsonDiff(historyDiffReq);
        Assert.assertNotNull(result.getData());
    }

    @Test
    public void testCheckEmptyPage() {
        JsonResult<String> result = pageFeignClient.checkEmptyPage(4285);
        Assert.assertNotNull(result.getData());
    }

    @Test
    public void testQueryCompAddress() {
        JsonResult<String> result = pageFeignClient.queryCompAddress("商品");
        Assert.assertNotNull(result.getData());
    }

}
