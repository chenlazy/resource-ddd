package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.api.facade.SequenceNumberFacade;
import com.idanchuang.cms.api.request.CreateNumberReq;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-05-31 11:25
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class SequenceNumberControllerTest extends SpringTest {

    @Resource
    private SequenceNumberFacade sequenceNumberFacade;

    @Test
    public void sequenceNumber() {
        CreateNumberReq createNumberReq = new CreateNumberReq();
        createNumberReq.setNum(1);
        createNumberReq.setNumberType(1);
        JsonResult<Long> result = sequenceNumberFacade.sequenceNumber(createNumberReq);
        Assert.assertTrue(result.getData() > 0);
    }


    @Test
    public void batchSequenceNumber() {
        CreateNumberReq createNumberReq = new CreateNumberReq();
        createNumberReq.setNum(5);
        createNumberReq.setNumberType(1);
        JsonResult<List<Long>> result = sequenceNumberFacade.batchSequenceNumber(createNumberReq);
        Assert.assertNotNull(result.getData());
    }

}
