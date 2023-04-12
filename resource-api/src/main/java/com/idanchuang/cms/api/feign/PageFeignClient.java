package com.idanchuang.cms.api.feign;

import com.idanchuang.cms.api.request.PageHistoryDiffReq;
import com.idanchuang.cms.api.response.PageDiffDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-18 17:03
 * @Desc: 页面搭建
 * @Copyright VTN Limited. All rights reserved.
 */
@FeignClient(value = "DC-RESOURCE", path = "/page/feign")
public interface PageFeignClient {

    @PostMapping("/pageDiff")
    @ApiOperation(value = "对比页面历史数据")
    JsonResult<PageDiffDTO> comparePageJsonDiff(@RequestBody PageHistoryDiffReq diffReq);

    @GetMapping("/checkEmptyPage")
    @ApiOperation(value = "校验空模版")
    JsonResult<String> checkEmptyPage(@RequestParam("pageId") Integer pageId);

    @GetMapping("/queryCompAdress")
    @ApiOperation(value = "查询组件说明地址")
    JsonResult<String> queryCompAddress(@RequestParam("keyword") String keyword);
}
