package com.idanchuang.cms.server.interfaces.adcontentservice.facade;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.service.impl.WBulletinService;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinQueryRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.BulletinDetailDTO;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.BulletinPageDTO;
import com.idanchuang.cms.api.adcontentservice.response.BulletinPortalDetailDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: xf
 * @time: 2020/12/3
 */
@Component
public class AdminBulletinFacade {

    @Resource
    private WBulletinService bulletinService;


    public JsonResult<PageData<BulletinPageDTO>> selectListBulletin(BulletinQueryRequest bulletinQueryRequest) {
        IPage<BulletinPageDTO> page = bulletinService.selectListBulletin(bulletinQueryRequest);
        PageData<BulletinPageDTO> data = new PageData<>();
        if (page.getTotal() <= 0) {
            return JsonResult.success(data);
        }
        data.setCurrent(page.getCurrent())
                .setSize(page.getSize())
                .setTotal(page.getTotal())
                .setRecords(page.getRecords());
        return JsonResult.success(data);

    }

    public JsonResult<Object> createBulletin(BulletinRequest bulletinRequest) {
        return bulletinService.createBulletin(bulletinRequest);
    }

    public JsonResult<BulletinDetailDTO> getDetailBulletinById(Integer id) {
        return JsonResult.success(bulletinService.getDetailBulletinById(id));
    }

    public JsonResult<Object> updateBulletin(BulletinRequest bulletinRequest) {
        return bulletinService.updateBulletin(bulletinRequest);
    }


    public JsonResult<Boolean> deleteBulletinById(Integer id) {
        return bulletinService.deleteBulletinById(id) ? JsonResult.success() : JsonResult.failure("删除公告失败");
    }


    public JsonResult<List<BulletinPortalDetailDTO>> getListByPlatFormPosition(String platformType, String position) {
        return JsonResult.success(bulletinService.getListByPlatFormPosition(platformType, position));
    }
}
