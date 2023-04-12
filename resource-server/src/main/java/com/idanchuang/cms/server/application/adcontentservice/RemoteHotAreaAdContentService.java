package com.idanchuang.cms.server.application.adcontentservice;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.ErrorEnum;
import com.idanchuang.cms.api.adcontentservice.request.HotAreaPositionRequest;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/2/23 5:14 下午
 */
@Slf4j
@Service
public class RemoteHotAreaAdContentService {

    @Resource
    private AppHotAreaFacade hotAreaFacade;

    public List<HotAreaAdminDTO> getListByPositionPlatform(String position, Integer platform, LocalDateTime timePoint) {
        JsonResult<List<HotAreaAdminDTO>> result;
        try {
            if (null == timePoint) {
                result = hotAreaFacade.getListByPositionPlatformNoTime(position, platform);
            } else {
                HotAreaPositionRequest request = new HotAreaPositionRequest();
                request.setPlatform(platform);
                request.setPosition(position);
                request.setTimePoint(timePoint);
                result = hotAreaFacade.getListByPositionPlatform(request);
            }
        } catch (Exception e) {
            log.error("getListByPositionPlatform error e:{}", e);
            throw new BusinessException(ErrorEnum.QUERY_AD_CONTENT_ERROR);
        }
        if (result != null && result.checkSuccess()) {
            return result.getData();
        } else {
            return Lists.newArrayList();
        }
    }
}
