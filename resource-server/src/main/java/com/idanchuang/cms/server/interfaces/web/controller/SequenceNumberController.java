package com.idanchuang.cms.server.interfaces.web.controller;

import com.idanchuang.cms.api.facade.SequenceNumberFacade;
import com.idanchuang.cms.api.request.CreateNumberReq;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 上午11:30
 */
@RestController
@RequestMapping("/cms/")
@Api(value = "发号器", tags = {"发号器服务"})
@Slf4j
public class SequenceNumberController {

    @Resource
    private SequenceNumberFacade sequenceNumberFacade;

    @PostMapping("/sequenceNumber")
    @ApiOperation(value = "/sequenceNumber", notes = "单个发号", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<Long> sequenceNumber(@RequestBody @Valid CreateNumberReq createNumberReq) {
        return sequenceNumberFacade.sequenceNumber(createNumberReq);
    }

    @PostMapping("/batchSequenceNumber")
    @ApiOperation(value = "/batchSequenceNumber", notes = "批量发号", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<List<Long>> batchSequenceNumber(@RequestBody @Valid CreateNumberReq createNumberReq) {
        if (createNumberReq.getNum() == null || createNumberReq.getNum() <= 0) {
            JsonResult.failure("未申请发号数量");
        }
        return sequenceNumberFacade.batchSequenceNumber(createNumberReq);
    }
}
