package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.request.CreateNumberReq;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 上午11:09
 */
public interface SequenceNumberFacade {

    /**
     * 单个发号
     *
     * @param createNumberReq
     * @return
     */
    @PostMapping("/sequenceNumber")
    @ApiOperation(value = "/sequenceNumber", notes = "单个发号", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Long> sequenceNumber(@RequestBody CreateNumberReq createNumberReq);

    /**
     * 批量发号
     *
     * @param createNumberReq
     * @return
     */
    @PostMapping("/batchSequenceNumber")
    @ApiOperation(value = "/batchSequenceNumber", notes = "批量发号", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<List<Long>> batchSequenceNumber(@RequestBody CreateNumberReq createNumberReq);
}
