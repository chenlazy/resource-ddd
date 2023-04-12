package com.idanchuang.cms.server.interfaces.web.controller;

import com.idanchuang.cms.api.response.LuckyComponentActivityDTO;
import com.idanchuang.cms.api.response.LuckyComponentPrizeDTO;
import com.idanchuang.cms.server.application.remote.RemoteLuckyService;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-05 18:01
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/page/lucky")
@Slf4j
public class LuckyComponentController {

    @Resource
    private RemoteLuckyService remoteLuckyService;

    @ApiOperation(value = "获取抽奖活动详情")
    @GetMapping("/activity/detail")
    public JsonResult<LuckyComponentActivityDTO> getActivityDetail(@RequestParam(value = "activityId") Long activityId) {
        return JsonResult.success(remoteLuckyService.getActivityDetail(activityId));
    }

    @ApiOperation(value = "获取抽奖活动奖品列表")
    @GetMapping("/activity/prize/query")
    public JsonResult<List<LuckyComponentPrizeDTO>> getActivityPrize(@RequestParam(value = "activityId") Long activityId) {
        return JsonResult.success(remoteLuckyService.getActivityPrize(activityId));
    }
}
