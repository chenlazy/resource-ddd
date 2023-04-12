package com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.facade;

import com.idanchuang.cms.api.adcontentservice.facade.BulletinFacade;
import com.idanchuang.cms.api.adcontentservice.response.BulletinPortalDetailDTO;
import com.idanchuang.cms.server.interfaces.adcontentservice.facade.AdminBulletinFacade;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/28 上午9:48
 */
@Slf4j
@RestController
@RequestMapping("/api/bulletin")
public class BulletinFacadeImpl implements BulletinFacade {

    @Resource
    private AdminBulletinFacade adminBulletinFacade;

    @Override
    public JsonResult<List<BulletinPortalDetailDTO>> getListByPlatFormPosition(String platformType, String position) {
        return adminBulletinFacade.getListByPlatFormPosition(platformType, position);
    }
}
