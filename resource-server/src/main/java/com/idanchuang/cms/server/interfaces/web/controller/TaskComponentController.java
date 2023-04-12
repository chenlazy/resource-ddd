package com.idanchuang.cms.server.interfaces.web.controller;

import com.idanchuang.cms.api.response.TaskActivityConfigDTO;
import com.idanchuang.cms.server.application.service.TaskComponentService;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-05 18:04
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/task/component")
@Slf4j
public class TaskComponentController {

    @Resource
    private TaskComponentService taskComponentService;

    @ApiOperation(value = "获取抽奖活动奖品列表")
    @GetMapping("/activityConfig")
    public JsonResult<TaskActivityConfigDTO> getTaskActivityConfig(@RequestParam(value = "activityId") Long activityId) {
        TaskActivityConfigDTO activityConfig = taskComponentService.getTaskActivityConfig(activityId);
        return JsonResult.success(activityConfig);
    }
}
