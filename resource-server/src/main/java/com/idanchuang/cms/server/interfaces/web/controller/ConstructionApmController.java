package com.idanchuang.cms.server.interfaces.web.controller;

import com.google.common.collect.Maps;
import com.idanchuang.cms.api.request.ConstructionApmReq;
import com.idanchuang.cms.server.infrastructureNew.util.ApmUtils;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.link.apmsdk.enums.ApmContentTypeEnum;
import com.idanchuang.link.apmsdk.enums.ApmDomainEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author fym
 * @description :
 * @date 2021/12/24 下午2:34
 */
@RestController
@RequestMapping("/cms/")
@Api(value = "apm构造器", tags = {"apm构造器"})
@Slf4j
public class ConstructionApmController {

    @PostMapping("/batchConstructionApm")
    @ApiOperation(value = "/batchConstructionApm", notes = "批量构造apm参数", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<Map<String, List<String>>> batchConstructionApm(@RequestBody @Valid ConstructionApmReq req) {
        if (req == null || MapUtils.isEmpty(req.getApmReqMap())) {
            return JsonResult.success(Maps.newHashMap());
        }
        Map<String, List<String>> apmMap = Maps.newHashMap();
        for (Map.Entry<String, List<ConstructionApmReq.ApmReq>> entry : req.getApmReqMap().entrySet()) {
            List<String> collect = entry.getValue().stream().map(u -> {
                String apm = ApmUtils.createApm(entry.getKey(), u.getNicheLineId(), u.getLocation(), ApmDomainEnum.CMS, ApmContentTypeEnum.fromVal(u.getContentTypeEnum()), u.getContentId(), u.getCustom());
                return apm;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                apmMap.put(entry.getKey(), collect);
            }
        }
        return JsonResult.success(apmMap);
    }
}
