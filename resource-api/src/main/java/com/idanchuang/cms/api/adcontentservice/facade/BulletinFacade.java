package com.idanchuang.cms.api.adcontentservice.facade;

import com.idanchuang.cms.api.adcontentservice.response.BulletinPortalDetailDTO;
import com.idanchuang.component.base.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/28 上午9:51
 */
@FeignClient(value = "DC-RESOURCE", path = "/api/bulletin")
public interface BulletinFacade {

    /**
     * 根据位置查询公告信息
     * @param platformType
     * @param position
     * @return
     */
    @GetMapping({"/getListByPlatformTypeAndPosition"})
    JsonResult<List<BulletinPortalDetailDTO>> getListByPlatFormPosition(@RequestParam("platformType") String platformType, @RequestParam("position") String position);

}
