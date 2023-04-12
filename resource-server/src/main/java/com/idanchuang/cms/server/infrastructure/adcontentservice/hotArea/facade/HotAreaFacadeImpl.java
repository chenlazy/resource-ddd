package com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.facade;

import com.idanchuang.cms.api.adcontentservice.facade.HotAreaFacade;
import com.idanchuang.cms.api.adcontentservice.request.HotAreaPositionRequest;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;
import com.idanchuang.cms.server.application.adcontentservice.AppHotAreaFacade;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/24 下午3:52
 */
@Slf4j
@RestController
@RequestMapping("/hotArea/")
public class HotAreaFacadeImpl implements HotAreaFacade {

    @Resource
    private AppHotAreaFacade appHotAreaFacade;

    @Override
    public JsonResult<List<HotAreaAdminDTO>> getListByPositionPlatform(HotAreaPositionRequest hotAreaPositionRequest) {
        return appHotAreaFacade.getListByPositionPlatform(hotAreaPositionRequest);
    }

    @Override
    public JsonResult<List<HotAreaAdminDTO>> getListByPositionPlatformNoTime(String position, Integer platform) {
        return appHotAreaFacade.getListByPositionPlatformNoTime(position, platform);
    }
}
