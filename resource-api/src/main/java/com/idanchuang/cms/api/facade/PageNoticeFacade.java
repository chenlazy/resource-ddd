package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.request.ActivityNoticeReq;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-15 10:15
 * @Desc: CMS活动通知接口
 * @Copyright VTN Limited. All rights reserved.
 */
@FeignClient(value = "DC-RESOURCE", path = "/cms/page/notice")
@Api(value = "CMS活动通知", tags = {"CMS活动通知"})
public interface PageNoticeFacade {

    @PostMapping("/activityChange")
    @ApiOperation(value = "活动变更通知")
    JsonResult<Boolean> notifyActivityChange(@RequestBody ActivityNoticeReq activityNoticeReq);
}
