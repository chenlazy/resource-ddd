package com.idanchuang.cms.api.adcontentservice.facade;

import com.idanchuang.cms.api.adcontentservice.request.HotAreaPositionRequest;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/24 下午3:49
 */
@Api(value = "首页热区服务", tags = {"首页热区服务"})
@FeignClient(value = "DC-RESOURCE", path = "/hotArea/")
public interface HotAreaFacade {

    /**
     * 制定时间范围查询首页热区
     *
     * @param hotAreaPositionRequest
     * @return
     */
    @PostMapping({"/getListByPositionPlatform"})
    JsonResult<List<HotAreaAdminDTO>> getListByPositionPlatform(@RequestBody HotAreaPositionRequest hotAreaPositionRequest);

    /**
     * 根据平台和位置查询首页热区
     *
     * @param position
     * @param platform
     * @return
     */
    @GetMapping({"/getListByPositionPlatformNoTime"})
    JsonResult<List<HotAreaAdminDTO>> getListByPositionPlatformNoTime(@RequestParam("position") String position, @RequestParam("platform") Integer platform);
}
