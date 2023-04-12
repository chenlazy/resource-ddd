package com.idanchuang.cms.server.interfaces.web.controller;

import com.idanchuang.bizlog.feign.constant.SortOrderEnum;
import com.idanchuang.bizlog.feign.dto.oplog.OpLogRequestPageQueryResponseDTO;
import com.idanchuang.bizlog.feign.dto.oplog.OpV2PageQueryDTO;
import com.idanchuang.bizlog.feign.dto.oplog.OpV2PageQueryParamDTO;
import com.idanchuang.bizlog.feign.dto.param.query.TimeRangeQueryParam;
import com.idanchuang.cms.api.request.OperateLogRequest;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.remote.RemoteBizLogService;
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

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:49
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/operate/log")
@Api(value = "搭建工具操作日志", tags = {"搭建工具操作日志"})
@Slf4j
public class OperateLogController {

    @Resource
    private RemoteBizLogService remoteBizLogService;

    @PostMapping("/pageList")
    @ApiOperation(value = "/pageList", notes = "查询搭建工具操作日志", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<OpLogRequestPageQueryResponseDTO> pageList(@RequestBody @Valid OperateLogRequest req) {
        OpV2PageQueryDTO opV2PageQueryDTO = new OpV2PageQueryDTO();
        OpV2PageQueryParamDTO paramDTO = new OpV2PageQueryParamDTO();
        paramDTO.setSysName(SystemConfig.getInstance().getApplicationName());
        paramDTO.setCustomParameters(req.getCustomParameters());
        paramDTO.setModule(req.getModule());
        paramDTO.setLogType(req.getLogType());
        TimeRangeQueryParam timeRangeQueryParam = new TimeRangeQueryParam();
        timeRangeQueryParam.setSortByDate(true);
        timeRangeQueryParam.setSortOrder(SortOrderEnum.DESC);
        paramDTO.setTimeRangeQueryParam(timeRangeQueryParam);
        opV2PageQueryDTO.setQueryParam(paramDTO);
        opV2PageQueryDTO.setPageNo(req.getCurrent().intValue());
        opV2PageQueryDTO.setPageSize(req.getSize().intValue());
        OpLogRequestPageQueryResponseDTO result = remoteBizLogService.logPageQuery(opV2PageQueryDTO);
        return JsonResult.success(result);
    }
}
