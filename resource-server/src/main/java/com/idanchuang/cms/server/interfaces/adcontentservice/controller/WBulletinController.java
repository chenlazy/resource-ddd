package com.idanchuang.cms.server.interfaces.adcontentservice.controller;


import com.idanchuang.cms.server.application.adcontentservice.WBulletinAdminService;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 公告(WBulletin)表控制层
 *
 * @author xf
 * @since 2021-02-22 11:07:45
 */
@Api(tags = "站内公告接口服务")
@RestController
@RequestMapping("/bulletin")
public class WBulletinController {

    @Resource
    private WBulletinAdminService wBulletinService;

    @GetMapping("/getByPlatFormPosition")
    public JsonResult<BulletinDTO> getByPlatFormPosition(@RequestParam("platformType")String platformType,
                                                         @RequestParam("position") String position) {
        if(SystemConfig.getInstance().bulletinDowngradeEnable){
            return JsonResult.success();
        }
        return JsonResult.success(wBulletinService.getByPlatFormPosition(platformType,position));
    }

}

