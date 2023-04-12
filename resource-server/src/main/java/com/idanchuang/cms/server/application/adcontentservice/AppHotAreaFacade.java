package com.idanchuang.cms.server.application.adcontentservice;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.entity.WHotArea;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.service.impl.IWHotAreaService;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.transfer.HotAreaTransfer;
import com.idanchuang.cms.api.adcontentservice.request.HotAreaPositionRequest;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fym
 * @description :
 * @date 2021/2/23 3:38 下午
 */
@Component
@Slf4j
public class AppHotAreaFacade {

    @Resource
    private IWHotAreaService hotAreaService;


    public JsonResult<List<HotAreaAdminDTO>> getListByPositionPlatform(HotAreaPositionRequest request) {
        List<WHotArea> list = hotAreaService.getListByPositionPlatform(request.getPosition(), request.getPlatform(), request.getTimePoint());
        if (CollectionUtils.isEmpty(list)) {
            return JsonResult.success(Lists.newArrayList());
        }
        List<HotAreaAdminDTO> hotAreaAdminDTOList = list.stream().map(HotAreaTransfer::transToDTO).collect(Collectors.toList());
        return JsonResult.success(hotAreaAdminDTOList);
    }

    public JsonResult<List<HotAreaAdminDTO>> getListByPositionPlatformNoTime(String position, Integer platform) {
        List<WHotArea> list = hotAreaService.getListByPositionPlatform(position, platform);
        if (CollectionUtils.isEmpty(list)) {
            return JsonResult.success(Lists.newArrayList());
        }
        List<HotAreaAdminDTO> hotAreaAdminDTOList = list.stream().map(HotAreaTransfer::transToDTO).collect(Collectors.toList());
        return JsonResult.success(hotAreaAdminDTOList);
    }
}
